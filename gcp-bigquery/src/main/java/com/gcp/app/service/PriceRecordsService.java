package com.gcp.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcp.app.entity.PriceRecords;
import com.google.cloud.bigquery.QueryJobConfiguration;

@Service
public class PriceRecordsService {

	@Autowired
	BigQueryService bigQueryService;

	public String getPriceRecord(Integer id) {

		final QueryJobConfiguration queryConfig = QueryJobConfiguration
				.newBuilder("SELECT * FROM product_data.Price_Records pr " + "WHERE pr.record_id = " + id)
				.setUseLegacySql(true).build();

		List<String> result = bigQueryService.execute(queryConfig);

		PriceRecords priceRecords = null;
		String status = "";
		if (!result.isEmpty()) {
			String json = result.get(0);
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				priceRecords = objectMapper.readValue(json, PriceRecords.class);
				if (priceRecords.getProd_price_latest() > priceRecords.getProd_price_old()) {
					status = "For " + priceRecords.getRecord_id() + " latest price is greater than old price";
				} else if (priceRecords.getProd_price_latest() < priceRecords.getProd_price_old()) {
					status = "For " + priceRecords.getRecord_id() + " latest price is less than old price";
				} else {
					status = "For " + priceRecords.getRecord_id() + " latest price is equal old price";
				}
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}

		return status;
	}

}
