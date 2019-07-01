package com.qf.service;

import com.qf.entity.User;

import java.util.List;
import java.util.Map;

public interface IUserService {
    public Integer insertUser(User user);
    public User  queryUser(User user);
    public List<User> getUserByName(Map map);
    public Integer updateUser(User user);
}
