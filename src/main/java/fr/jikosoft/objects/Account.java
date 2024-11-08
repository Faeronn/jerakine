package fr.jikosoft.objects;

public class Account {
	private String username;
	private String password;
	private String nickname;
	private int accountID;

	public Account(int accountID, String username, String password, String nickname) {
		this.accountID = accountID;
		this.username = username;
		this.password = password;
		this.nickname = nickname;
	}

	public String getUsername() {return this.username;}
	public String getPassword() {return this.password;}
	public String getNickname() {return this.nickname;}
	public int getAccountID() {return this.accountID;}
}
