package com.atlassian.jira.rest.tutorial.issuetest;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;

/**
 * Hello world!
 *
 */
public class IssueCreateExample {
	public static void main(String[] args) throws IOException {
		URI serverUri = URI.create("http://vmwarepc:2990/jira");
		final AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
		final JiraRestClient restClient = factory
			.createWithBasicHttpAuthentication(serverUri, "admin", "admin");
		try {
			IssueInputBuilder issueBuilder = new IssueInputBuilder("TUTORIAL", 1L, "Issue created by REST Client");
			issueBuilder.setSummary("This is cearted for test reasons");
			IssueInput issueInput = issueBuilder.build();
			Promise<BasicIssue> promise = restClient.getIssueClient().createIssue(issueInput);
			String key;
			try {
				key = promise.get().getKey();
				System.out.println("Generated Issue with key = " + key);
				final Issue issue = restClient.getIssueClient()
						.getIssue(key).claim();
				System.out.println(issue);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		} finally {
			restClient.close();
		}
	}
}
