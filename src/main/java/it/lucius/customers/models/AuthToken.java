package it.lucius.customers.models;

public class AuthToken {

    private String token;
    private String username;
    private String role;
    
    public AuthToken() {

    }

    public AuthToken(String token, User user){
        this.token = token;
        
        if(user != null) {
	        this.username = user.getUsername();
	        this.role = user.getRole().getRole();
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
