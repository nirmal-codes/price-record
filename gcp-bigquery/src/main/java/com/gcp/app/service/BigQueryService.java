package com.gcp.app.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.gcp.app.Exception.BigQuerySearchException;
import com.gcp.app.config.BigQueryAppProperties;
import com.gcp.app.config.GoogleCredentialsUtility;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Field;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;

@Service
@EnableConfigurationProperties({ BigQueryAppProperties.class })
public class BigQueryService {

	private final BigQueryAppProperties properties;

	public BigQueryService(BigQueryAppProperties properties) {
		this.properties = properties;
	}

	public List<String> execute(QueryJobConfiguration queryConfig) throws BigQuerySearchException {
		BigQuery bigquery;

		try {
			bigquery = BigQueryOptions
					.newBuilder().setProjectId(properties.getProjectId()).setCredentials(GoogleCredentialsUtility
							.getCreds(properties.getCredentialsPath(), properties.getCredentialsName()))
					.build().getService();
		} catch (IOException ex) {
			throw new BigQuerySearchException("IO Exception thrown - can't find google credentials file", ex);
		}

		// Create a job ID so that we can safely retry.
		Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).build());

		// Wait for the query to complete.
		try {
			queryJob = queryJob.waitFor();

			// Check for errors
			if (queryJob == null) {
				throw new BigQuerySearchException("Job no longer exists");
			} else if (queryJob.getStatus().getError() != null) {
				// You can also look at queryJob.getStatus().getExecutionErrors() for all
				// errors, not just the latest one.
				throw new BigQuerySearchException(queryJob.getStatus().getError().toString());
			}

			final TableResult result = queryJob.getQueryResults();

			// build list of fields in table results expected from query schema
			final List<String> fieldList = result.getSchema().getFields().stream().map(Field::getName)
					.collect(Collectors.toList());

			return tableResultToJSONStringListConverter(result, fieldList);
		} catch (InterruptedException ex) {
			throw new BigQuerySearchException("InterruptedException - query job failed", ex);
		}
	}

	private List<String> tableResultToJSONStringListConverter(TableResult result, List<String> fields) {
		final List<String> queryList = new ArrayList<>();

		// loop throw table results and convert to/add JSONObjects to string list
		// this is slow and could be fixed with intermediate object
		for (FieldValueList row : result.iterateAll()) {
			final JSONObject jsonObject = new JSONObject();

			fields.forEach(field -> jsonObject.put(field, row.get(field).getStringValue()));

			queryList.add(jsonObject.toString());
		}

		return queryList;
	}

}
