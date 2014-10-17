package com.atlassian.jira.rest.tutorial.issuetest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.atlassian.jira.rest.client.api.domain.Project;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;

/**
 * Hello world!
 *
 */
public class AdvanceIssueCreateExample 
{
	private static String actualProjectName = "TUTORIAL";
	
	public static void main(String[] args) throws IOException, NumberFormatException {
		URI serverUri = URI.create("http://vmwarepc:2990/jira");
		final AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
		final JiraRestClient restClient = factory
			.createWithBasicHttpAuthentication(serverUri, "admin", "admin");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			Project actualProjekct= restClient.getProjectClient().getProject(actualProjectName).claim();
			System.out.println("Create Issue for the Project " + actualProjekct.getName());
			Iterator<IssueType> issueTypeIterator = actualProjekct.getIssueTypes().iterator();
			ArrayList<IssueType> issueTypeList = new ArrayList<IssueType>(); 
			int counter = 0;
			while (issueTypeIterator.hasNext()) {
				IssueType issueType = (IssueType) issueTypeIterator.next();
				counter++;
				System.out.println(counter + " : " + issueType.getName() + " " + issueType.getId());
				issueTypeList.add(issueType);
			}
			System.out.println();
			System.out.print("Choose issue type number : ");
			String input = reader.readLine();
			int userInputNum = Integer.parseInt(input);
			IssueType userChosenIssueType = issueTypeList.get(userInputNum - 1); 
			System.out.println("Issue is a " +userChosenIssueType.getName() + " - " + 
				userChosenIssueType.getDescription());
			System.out.println();
			System.out.println("Type in the summary for the issue");
			String userEnteredSummary = reader.readLine();
			System.out.println();
			IssueInputBuilder issueBuilder = 
				new IssueInputBuilder(actualProjectName, userChosenIssueType.getId(), userEnteredSummary);
			IssueInput issueInput = issueBuilder.build();
			Promise<BasicIssue> promise = restClient.getIssueClient().createIssue(issueInput);
			String key;
			try {
				key = promise.get().getKey();
				System.out.println("Generated Issue with key = " + key);
				final Issue issue = restClient.getIssueClient()
						.getIssue(key).claim();
				System.out.println(issue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			reader.close();
			restClient.close();
		}
	}
}
