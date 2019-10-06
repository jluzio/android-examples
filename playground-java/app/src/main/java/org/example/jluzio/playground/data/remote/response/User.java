package org.example.jluzio.playground.data.remote.response;

/**
 * Created by jluzio on 14/03/2018.
 */

public class User {
    public String id;
    public String name;
    public String username;
    public String email;

    public User() {
    }

    public User(String id, String name, String username, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
    }
}
