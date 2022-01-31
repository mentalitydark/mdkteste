package app.image;

public class Image {
    private Integer id;
    private String name;
    private String url;
    private Integer user_id;
    private String user_name;

    public Image(String name, String url, Integer user_id) {
        this.name = name;
        this.url = url;
        this.user_id = user_id;
    }

    // GET
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getUrl() {
        return url;
    }
    public Integer getUser_id() {
        return user_id;
    }
    public String getUser_name() {
        return user_name;
    }

    // SET
    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
