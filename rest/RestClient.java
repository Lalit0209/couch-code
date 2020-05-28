package rest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RestClient implements Runnable{

	private String url_string;
	private String urlParameters;
	private String requestType;


	public RestClient(String url_string, String urlParameters, String requestType) {
		this.url_string = url_string;
		this.urlParameters = urlParameters;
		this.requestType = requestType;
	}
	
	
	public void run() {
		

	String output_string="";
	  try {
		  
		if(requestType=="GET") {
			url_string+="?";
			url_string+=urlParameters;
		}
			
		URL url = new URL(url_string);
		
		byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
		int postDataLength = postData.length;
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(requestType);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("charset", "utf-8");
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		if(requestType=="POST") {
		try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
			   wr.write( postData );
			}
		}
		String temp="";
		if (conn.getResponseCode() < 400) {
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((temp = br.readLine()) != null) {
				output_string+=temp;
			}
			} 
		else {			  
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			while ((temp = br.readLine()) != null) {
				output_string+=temp;
			}
			}

	  } catch (MalformedURLException e) {

		//e.printStackTrace();

	  } catch (IOException e) {

		//e.printStackTrace();

	  }
	  System.out.println(output_string);

	}

}
