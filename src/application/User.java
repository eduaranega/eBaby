package application;

public class User {

	private String firstName;
	private String lastName;
	private String userEmail;
	private String userName;
	private String password;
	private boolean isLogged, isSeller;

	private User(String firstName, String lastName, String userEmail, String userName, String password) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.userEmail = userEmail;
		this.userName = userName;
		this.password = password;
		this.isLogged=false;
		this.isSeller=false;
	}

	public static User getInstance(String firstName, String lastName, String userEmail, String userName, String password) {
		return new User(firstName, lastName, userEmail, userName, password);
	}

	/* below here are gets and sets */
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLogged() {
		return isLogged;
	}

	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}

	public boolean isSeller() {
		return isSeller;
	}

	public void setSeller(boolean seller) {
		isSeller = seller;
	}

	
	
}
