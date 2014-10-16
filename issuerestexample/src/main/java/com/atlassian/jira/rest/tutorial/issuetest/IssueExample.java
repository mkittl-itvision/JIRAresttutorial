package com.atlassian.jira.rest.tutorial.issuetest;

import java.io.IOException;
import java.net.URI;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;



/**
 * Hello world!
 *
 */
public class IssueExample {
	public static void main(String[] args) throws IOException {
		URI serverUri = URI.create("http://vmwarepc:2990/jira");
		final AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
		final JiraRestClient restClient = factory.createWithBasicHttpAuthentication(serverUri, "admin", "admin");
		try {
			final Issue issue = restClient.getIssueClient().getIssue("TUTORIAL-2").claim();
			System.out.println(issue.getKey());
			System.out.println(issue.getDescription());
			System.out.println(issue.getSummary());
			System.out.println(issue.getAssignee().getDisplayName());
			System.out.println();
			System.out.println(issue);
		} finally {
			restClient.close();
		}
	}
}
