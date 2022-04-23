package bookworm;

import bookworm.Client;

/*
 Use this class as a test to make sure that your Java code can communicate
 with your Node Express server.

 This code assumes that your Node Express server is running on the same computer
 as this program, is using port 3000 (the default port), and has a "/test" endpoint
 that sends back a JSON object with a "message" field, as in the starter code
 we distributed for this assignment.
*/

public class ClientExample {

	public static void main(String[] args) {
		Client client = new Client();
		System.out.println(client.getPosts());
	}

}