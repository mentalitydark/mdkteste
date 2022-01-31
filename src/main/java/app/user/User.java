package app.user;

public class User {
    private Integer id;
    private String username;
    private String email;
    private String password;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    //GET
    public Integer getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    //SET
    public void setId(Integer id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
