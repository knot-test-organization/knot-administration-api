package com.nttdata.knot.administrationapi.Models.UserPackage;

import java.util.List;
import java.util.ArrayList;

public class UserList {
    private List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
