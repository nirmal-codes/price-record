package com.gcp.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class BigQueryAppProperties {
	private String credentialsPath = "/resources/";
	private String credentialsName = "key.json";
	private String projectId = "omega-post-325903";

	public String getCredentialsPath() {
		return credentialsPath;
	}

	public String getCredentialsName() {
		return credentialsName;
	}

	public void setCredentialsName(String credentialsName) {
		this.credentialsName = credentialsName;
	}

	public void setCredentialsPath(String credentialsPath) {
		this.credentialsPath = credentialsPath;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}
