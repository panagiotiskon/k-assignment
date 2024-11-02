package backend.kassignment.web.resources;

import backend.kassignment.domain.User;

public class UserResource {
    private Long id;
    private String email;
    private String role;

    public UserResource(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRoles().getFirst().getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
