package rest;

public class Checker {
	public static void main(String[] args) {
		RestClient client = new RestClient("http://127.0.0.1:5000/codecouch/login/", "Usn=s1&Password=poison", "POST");
//		Thread t = new Thread(client);
//		t.start();
		client.run();
	}
}
