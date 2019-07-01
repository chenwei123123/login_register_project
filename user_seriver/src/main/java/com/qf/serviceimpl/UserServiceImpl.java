package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Integer insertUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public User queryUser(User user) {
        return userMapper.selectById(user.getId());
    }

    @Override
    public List<User> getUserByName(Map map) {
        return userMapper.selectByMap(map);
    }

    @Override
    public Integer updateUser(User user) {
        return userMapper.updateById(user);
    }
}
