package com.atlassian.jira.rest.tutorial.issuetest;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.MetadataRestClient;
import com.atlassian.jira.rest.client.api.domain.Status;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;


/**
 * MetaDataExample
 *
 */
public class MetaDataExample 
{
    public static void main( String[] args ) throws IOException
    {
        URI jira_Uri = URI.create("http://vmwarepc:2990/jira");
        AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
        JiraRestClient client = factory.createWithBasicHttpAuthentication(jira_Uri, "admin", "admin");
        MetadataRestClient metadataclient = client.getMetadataClient();
        ArrayList<Status> statusList = getArrayList(metadataclient.getStatuses().claim().iterator()) ;
        for (Status currentStatus : statusList) {
        	System.out.println(String.format("Name : %s --- Description : %s", 
        		currentStatus.getName(), currentStatus.getDescription()) );
        }        

        client.close();
    }
    
    private static <T> ArrayList<T> getArrayList(Iterator<T> iterator) {
    	ArrayList<T> arrayList = new ArrayList<T>();
    	while (iterator.hasNext()) {
			T value = (T) iterator.next();
			arrayList.add(value);
		}
    	
    	return arrayList;
    }
}
