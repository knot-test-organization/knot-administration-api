package com.nttdata.knot.administrationapi.Models.UserPackage;

public class UserFront {
    private String label;
    private Long id;
    private String username;
    
    
    public UserFront(String label, Long id, String username) {
        this.label = label;
        this.id = id;
        this.username = username;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}