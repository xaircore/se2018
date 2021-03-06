package com.xairlab.otus.jetty.service;

import com.xairlab.otus.jetty.entity.User;

import java.util.List;

public class UserService {

    private final StoreService<User> service;

    public UserService(StoreService<User> service) {
        this.service = service;
    }

    public void save(User user) {
        service.save(user);
    }

    public List<User> all() {
        return service.all();
    }

    public User findByName(String name) {
        return service.findByName(name);
    }
}
