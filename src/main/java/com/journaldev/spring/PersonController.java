package com.journaldev.spring;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.*;
import java.io.*;
import java.util.*;
import javax.net.ssl.HttpsURLConnection;

@RestController
public class PersonController {
	
	@RequestMapping("/")
	public String welcome() throws Exception {
		checkHTTPResponseCode();
		downloadWebPageHtmlCode();
		getServerHeaders();
		getCommonServerHeaders();
		sendHttpGetPost();
		return "Welcome to Spring Boot ...";
	}
	
	public void checkHTTPResponseCode() throws Exception {
		URL urlObj = new URL("https://www.facebook.com");
		HttpURLConnection httpCon = (HttpURLConnection) urlObj.openConnection();
		 
		int responseCode = httpCon.getResponseCode();
		System.out.println("Server returned response code : " + responseCode);		 
    }
	
	public void downloadWebPageHtmlCode(){
	    String url = "https://google.com";
        String filePath = "Google.html";
 
        try {
 
            URL urlObj = new URL(url);
            URLConnection urlCon = urlObj.openConnection();
 
            InputStream inputStream = urlCon.getInputStream();
            BufferedInputStream reader = new BufferedInputStream(inputStream);
 
            BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(filePath));
 
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
 
            while ((bytesRead = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, bytesRead);
            }
 
            writer.close();
            reader.close();
 
            System.out.println("Web page saved");
 
        } catch (MalformedURLException e) {
            System.out.println("The specified URL is malformed: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("An I/O error occurs: " + e.getMessage());
        }	
	}
	
	public void getServerHeaders() throws Exception {
		String url = "https://google.com";
		URL urlObj = new URL(url);
		URLConnection urlCon = urlObj.openConnection();
		 
		Map<String, List<String>> map = urlCon.getHeaderFields();
		 
		 
		for (String key : map.keySet()) {
			System.out.println(key + ":");
		 
			List<String> values = map.get(key);
		 
			for (String aValue : values) {
				System.out.println("\t" + aValue);
			}
		}	
	}
	
	public void getCommonServerHeaders() throws Exception {
		String url = "https://facebook.com";
		 
		URL urlObj = new URL(url);
		HttpURLConnection httpCon = (HttpURLConnection) urlObj.openConnection();
		 
		int responseCode = httpCon.getResponseCode();
		String responseMessage = httpCon.getResponseMessage();
		String contentType = httpCon.getContentType();
		String contentEncoding = httpCon.getContentEncoding();
		int contentLength = httpCon.getContentLength();
		 
		long date = httpCon.getDate();
		long expiration = httpCon.getExpiration();
		long lastModified = httpCon.getLastModified();
		 
		System.out.println("Response Code: " + responseCode);
		System.out.println("Response Message: " + responseMessage);
		System.out.println("Content Type: " + contentType);
		System.out.println("Content Encoding: " + contentEncoding);
		System.out.println("Content Length: " + contentLength);
		System.out.println("Date: " + new Date(date));
		System.out.println("Expiration: " + new Date(expiration));
		System.out.println("Last Modified: " + new Date(lastModified));	
	}
	
	public void sendHttpGetPost() throws Exception {
		
		System.out.println("Testing 1 - Send Http GET request");
		sendGet();
		
		System.out.println("\nTesting 2 - Send Http POST request");
		sendPost();	
	}
	
	// HTTP GET request
	public void sendGet() throws Exception {

		String url = "http://www.google.com/search?q=mkyong";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}
	
	// HTTP POST request
	public void sendPost() throws Exception {

		String url = "https://selfsolve.apple.com/wcResults.do";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		System.out.println(response.toString());

	}
	
}
