package edu.brynmawr.myapplication_worm;

/*
 * This class represents the Client that communicates with the RESTful API.
 * 
 * Implement the get() method according to the specification. You may add other methods
 * and instance variables as needed, though it should be possible to implement get()
 * without adding anything else.
 */

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Client {

	private String host;
	private int port;
	private String backEndUrl;

	public Client() {
		// use Node Express defaults
		host = "10.0.2.2";
		port = 3000;
		backEndUrl = "http://" + host + ":" + port;
	}

	public Client(String host, int port) {
		this.host = host;
		this.port = port;
		this.backEndUrl = "http://" + host + ":" + port;
	}

//gets post from backend
	public List<Post> getPosts() {
		List<Post> posts = new ArrayList<Post>();
		String rest_url = backEndUrl + "/posts";
		URL url = null;

		try {
			url = new URL(rest_url);
		} catch (MalformedURLException e) {
			System.out.println("Invalid Url: " + rest_url);
			return posts;
		}

		HttpURLConnection conn = null;

		try {
			conn = (HttpURLConnection) url.openConnection();
		} catch (IOException e1) {
			System.out.println("Fail to estabilish connection to the backend");
			return posts;
		}

		try {
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
		} catch (ProtocolException e) {
			System.out.println("Fail setting request method to GET");
			return posts;
		}

		// open connection and send HTTP request
		try {
			conn.connect();
		} catch (IOException e) {
			System.out.println("Failed estabilishing HTTP connection and failed request");
			e.printStackTrace();
			return posts;
		}

		// now the response comes back
		int responsecode = 0;
		try {

			responsecode = conn.getResponseCode();
		} catch (IOException e) {
			System.out.println("Failed getting  response code from server");
			return posts;
		}

		// make sure the response has "200 OK" as the status
		if (responsecode != 200) {
			System.out.println("Server responded with error code: " + responsecode);
			return posts;
		}

		Scanner in = null;
		try {
			in = new Scanner(conn.getInputStream());
		} catch (IOException e) {
			System.out.println("Failed reading response from server");
			return posts;
		}

		while (in.hasNext()) {

			// read the next line of the body of the response
			String line = in.nextLine();
			System.out.println(line);
			JSONParser parser = new JSONParser();
			JSONArray posts_json = null;
//		     then, parse the data and create a JSON object for it
			try {
				posts_json = (JSONArray) parser.parse(line);
				System.out.println(posts_json);
			} catch (ParseException e) {
				System.out.println("Failed parsing response as JSON array");
				return posts;
			}

			for (Object obj : posts_json) {

				JSONObject post_json = (JSONObject) obj;
				Long id = (Long) post_json.get("_id");
				String title = (String) post_json.get("title");
				String name = (String) post_json.get("name");
				String content = (String) post_json.get("content");
				String created = (String) post_json.get("created");

				Post post = new Post(id, title, name, content, created);
//		    	System.out.println(post);
				posts.add(post);

			}

		}

		in.close();
		return posts;

	}

}
