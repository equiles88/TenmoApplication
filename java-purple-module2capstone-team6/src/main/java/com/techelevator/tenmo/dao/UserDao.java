package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.RegisterUserDTO;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserBalance;

import java.security.Principal;
import java.util.List;

public interface UserDao {

    List<User> findAll();

    User findByUsername(String username);

    int findIdByUsername(String username);
    boolean checkUsername(String username);

    boolean create(String username, String password);

    User findById(int id);
}
