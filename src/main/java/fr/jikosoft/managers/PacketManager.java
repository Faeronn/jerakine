package fr.jikosoft.managers;

import java.io.PrintWriter;

import fr.jikosoft.kernel.Constants;
import fr.jikosoft.kernel.Conversions;

public class PacketManager {
	public static void send(PrintWriter writer, String packet) {
		if(writer != null && !packet.equals("") && !packet.equals(""+(char)0x00)) {
			packet = Conversions.toUtf(packet);
			writer.print(packet + (char)0x00);
			writer.flush();
		}
	}

	public static void LOGIN_SEND_VERSION_PACKET(PrintWriter writer) {
		String packet = Constants.CLIENT_VERSION;
		send(writer, packet);
		
		System.out.println("Login: Send >> " + packet);
	}

	public static void LOGIN_SEND_USERNAME_PACKET(PrintWriter writer, String username) {
		send(writer, username);
		System.out.println("Login: Send >> " + username);
	}

	public static void LOGIN_SEND_PASSWORD_PACKET(PrintWriter writer, String password) {
		send(writer, password);
		System.out.println("Login: Send >> " + password);
	}

	public static void LOGIN_SEND_CONNEXION_PACKET(PrintWriter writer) {
		String packet = "Af";
		send(writer, packet);
		System.out.println("Login: Send >> " + packet);
	}
}
