package xyz.pangosoft.encinalbackend.services;

import xyz.pangosoft.encinalbackend.models.User;

import java.util.List;

public interface IUserService {

    public List<User> findAll();

    public User singleUser(Integer id);

    public User singleUser(String username);

    public User create(User user);
}
