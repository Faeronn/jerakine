package fr.jikosoft.managers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

import fr.jikosoft.kernel.Conversions;

public class LoginManager implements Runnable {
	private static final String SERVER_URL = "localhost";
	private static final int PORT = 4444;
	private BufferedReader reader;
	private PrintWriter writer;
	private Thread thread;
	private Socket socket;
	private Status status;

	private Consumer<String> responseHandler;
	private String username;
	private String password;
	private String hash;


	public LoginManager(String username, String password, Consumer<String> responseHandler) {
		try {
			this.socket = new Socket(SERVER_URL, PORT);
			this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			this.writer = new PrintWriter(this.socket.getOutputStream());

			this.username = username;
			this.password = password;
			this.responseHandler = responseHandler;
			
			this.thread = new Thread(this);
			this.thread.start();
		} catch(IOException e) {
			System.out.println("Unable to connect to the server: " + e.getMessage());
			responseHandler.accept("Unable to connect to the server: " + e.getMessage());
		}
	}

	@Override
	public void run() {
		try {
			String packet = "";
			char[] charCur = new char[1];
			this.status = Status.wait_hash;
			
			while(this.reader.read(charCur, 0, 1) != -1) {
				if (charCur[0] != '\u0000' && charCur[0] != '\n' && charCur[0] != '\r') packet += charCur[0];
				else if(!packet.isEmpty()) {
					System.out.println("Game: Recv << " + packet);
					
					this.parsePacket(packet);
					packet = "";
				}
			}
		} catch(IOException e) {
			System.err.println("Connection closed or error occurred: " + e.getMessage());
		} finally {
			try {
				if (reader != null) reader.close();
				if (writer != null) writer.close();
				if (socket != null && !socket.isClosed()) socket.close();
				
				this.thread.interrupt();
			}
			catch(IOException e1) {
				System.err.println("Error closing resources: " + e1.getMessage());
			}
		}
	}

	public void parsePacket(String packet) {
		switch (this.status) {
			case Status.wait_hash:
				this.hash = packet.substring(2);
				String hashedPassword = Conversions.encryptPassword(this.password, this.hash);
				PacketManager.LOGIN_SEND_VERSION_PACKET(this.writer);
				PacketManager.LOGIN_SEND_USERNAME_PACKET(this.writer, username);
				PacketManager.LOGIN_SEND_PASSWORD_PACKET(this.writer, hashedPassword);
				PacketManager.LOGIN_SEND_CONNEXION_PACKET(this.writer);
				this.status = Status.wait_verification;
				break;
			case Status.wait_verification:
				String serverResponse = "";
				switch (packet.substring(2, 4)) {
					case "Ev":
						serverResponse = "Invalid game version.";
						break;
					case "Ef":
						serverResponse = "Incorrect username or password.";
						break;
					case "Er":
						serverResponse = "Please choose a nickname.";
						break;
					case "Eb":
						serverResponse = "This account is banned.";
						break;
					default:
						serverResponse = "Unknown response: " + packet;
				}
				responseHandler.accept(serverResponse);
			case Status.error:
				break;
		}
	}
	
	private enum Status {
		wait_hash("wait_hash", 0),
		wait_verification("wait_verification", 1),
		error("error", 2);
		
		private Status(final String s, final int n) {}
	}
}
