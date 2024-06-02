package data;

public class LoginData {
    private String username;
    private String password;

    public LoginData() {}

    public LoginData(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public void setUsername(String param) {
        this.username = param;
    }

    public void setPassword(String param) {
        this.password = param;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}
