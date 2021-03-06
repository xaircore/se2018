package com.xairlab.otus.di.service;

import com.xairlab.otus.di.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
