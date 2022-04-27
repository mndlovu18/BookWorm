package edu.brynmawr.myapplication_worm;

/*
 * This class represents the Client that communicates with the RESTful API.
 * 
 * Implement the get() method according to the specification. You may add other methods
 * and instance variables as needed, though it should be possible to implement get()
 * without adding anything else.
 */

import android.util.Log;

import androidx.appcompat.app.AlertDialog;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

	public class ServerConnection {
		String host;
		JSONObject result;

		public ServerConnection(String host) {
			this.host = host;
			this.result = null;
		}

		public JSONObject get(String request){
			Log.v("URL", request);

			result = null;
			ExecutorService executor = Executors.newSingleThreadExecutor();
			executor.execute(() -> {
				try {
					URL url = new URL(host + "/" + request);
					Log.v("URL", url.toString());
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.connect();
					
					int responseCode = connection.getResponseCode();
					if (responseCode != 200) {
						throw new IllegalStateException();
					}

					Scanner in = new Scanner(url.openStream());
					String msg = in.nextLine();

					//use android JSON library to parse the JSON string
					JSONParser parser = new JSONParser();
					result = (JSONObject) parser.parse(msg);
				} 
				catch (Exception e){
					Log.v("URL", e.toString());
					e.printStackTrace();
				}
			});
			return result;
		}	
	}

	public void savePost(String title, String name, String content) {
		ServerConnection server = new ServerConnection(backEndUrl);
		//the create end point creates a new post
		JSONObject results = server.get("createpost?title=" + title + "&name=" + name + "&content=" + content); //get the result of the create request
	}

	public void deletePost(Long id) {
		ServerConnection server = new ServerConnection(backEndUrl);
		//the delete end point deletes a post
		JSONObject results = server.get("delete?_id=" + id); //get the result of the delete request
	}
	
//gets post from backend
	public List<Post> getPosts() {
		List<Post> posts = new ArrayList<Post>(); // create a list of posts
		String rest_url = backEndUrl + "/posts"; // create the url to get posts
		URL url = null; // create a url object

		try {
			url = new URL(rest_url); // create a new url object
		} catch (MalformedURLException e) {
			System.out.println("Invalid Url: " + rest_url);
			return posts;
		}

		HttpURLConnection conn = null; // create a connection object to the url so we can get the data

		try {
			conn = (HttpURLConnection) url.openConnection(); // open a connection to the url object and cast it to a HttpURLConnection to get the data
		} catch (IOException e1) {
			System.out.println("Fail to estabilish connection to the backend");
			return posts;
		}

		try {
			conn.setRequestMethod("GET"); // set the request method to GET
			conn.setRequestProperty("Content-Type", "application/json"); // set the content type to json so we can get the data in json format
			conn.setRequestProperty("Accept", "application/json"); // set the accept type to json so we can get the data in json format
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

			responsecode = conn.getResponseCode(); // get the response code from the backend
		} catch (IOException e) {
			System.out.println("Failed getting  response code from server");
			return posts;
		}

		// make sure the response has "200 OK" as the status
		if (responsecode != 200) {
			System.out.println("Server responded with error code: " + responsecode);
			return posts;
		}

		Scanner in = null; // create a scanner object to read the data from the url
		try {
			in = new Scanner(conn.getInputStream()); // create a new scanner object to read the data from the url
		} catch (IOException e) {
			System.out.println("Failed reading response from server");
			return posts;
		}

		while (in.hasNext()) { // while there is still data to read

			// read the next line of the body of the response
			String line = in.nextLine();
			System.out.println(line);
			JSONParser parser = new JSONParser(); // create a new JSON parser object to parse the data from the url
			JSONArray posts_json = null; // create a JSON array to hold the posts
//		     then, parse the data and create a JSON object for it
			try {
				posts_json = (JSONArray) parser.parse(line);
				System.out.println(posts_json);
			} catch (ParseException e) {
				System.out.println("Failed parsing response as JSON array");
				return posts;
			}

			for (Object obj : posts_json) { // iterate through the JSON array to get each post

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
		//lists returned in the format: [{_id: 1, title: "title", name: "name", content: "content", created: "created"}, {_id: 2, title: "title", name: "name", content: "content", created: "created"}]
	}
}
