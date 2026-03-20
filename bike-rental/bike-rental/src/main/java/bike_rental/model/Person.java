package bike_rental.model;

public abstract class Person {

    private String userId;
    private String name;
    private String email;
    private String password;

    public Person(String userId, String name, String email, String password) {
        this.userId   = userId;
        this.name     = name;
        this.email    = email;
        this.password = password;
    }

    public Person() {}

    public String getUserId()   { return userId; }
    public String getName()     { return name; }
    public String getEmail()    { return email; }
    public String getPassword() { return password; }


    public void setUserId(String userId)     { this.userId = userId; }
    public void setName(String name)         { this.name = name; }
    public void setEmail(String email)       { this.email = email; }
    public void setPassword(String password) { this.password = password; }


    public abstract String getRole();


    public String getDisplayInfo() {
        return "[" + getRole() + "] " + name + " | " + email;
    }
}
