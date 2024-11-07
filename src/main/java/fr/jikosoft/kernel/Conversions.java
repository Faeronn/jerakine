package fr.jikosoft.kernel;

public class Conversions {
	public static String toUtf(String in) { //ANSI(Unicode) -> UTF-8
		String out = "";
		try {
			out = new String(in.getBytes("UTF8"));
		}
		catch(Exception e) {
			System.out.println (" ! ERROR : Conversion en UTF-8 echouée ! : " + e.getMessage());
		}
		
		return out;
	}

	public static String encryptPassword(String pass, String key) {
		char[] charList = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
						   't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
						   'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U','V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4',
						   '5', '6', '7', '8', '9', '-', '_'};

	    String out = "#1";

	    for (int i=0; i < pass.length(); i++) {
	        char cPass = pass.charAt(i);
	        char cKey = key.charAt(i);

	        out += charList[( (int)cPass / 16 + (int)cKey) % charList.length];
	        out += charList[( (int)cPass % 16 + (int)cKey) % charList.length];
	    }

	    return out;
	}
}
