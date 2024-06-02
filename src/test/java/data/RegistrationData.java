package data;

public class RegistrationData {

    private String firstName = "John";
    private String lastName  = "Doe";
    private String email;
    private String username;
    private Boolean newsletter = false;

    public RegistrationData() {}

    public RegistrationData(String firstName, String lastName, String email, String username, Boolean newsletter) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setUsername(username);
        this.setNewsletter(newsletter);
    }

    public void setFirstName(String param) {
        this.firstName = param;
    }

    public void setLastName(String param) {
        this.lastName = param;
    }

    public void setEmail(String param) {
        this.email = param;
    }

    public void setUsername(String param) {
        this.username = param;
    }

    public void setNewsletter(Boolean param) {
        this.newsletter = param;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUsername() {
        return this.username;
    }

    public Boolean isSubscribedToNewsletter() {
        return this.newsletter;
    }
}
