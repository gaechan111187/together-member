package client;

import global.Command;

public class ClientMain {
	public static void main(String[] args) {
		ClientService client = new ClientService();
		Thread user = new Thread(client);
		client.setThisThread(user);
		user.start();
	}
}
