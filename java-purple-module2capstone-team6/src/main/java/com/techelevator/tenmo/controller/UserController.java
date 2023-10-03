package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.dao.UsernameDao;
import com.techelevator.tenmo.model.RegisterUserDTO;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UsernameDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/")

public class UserController {
    private UserDao userDao;
    private UsernameDao usernameDao;

    public UserController(UserDao userDao, UsernameDao usernameDao) {
        this.userDao = userDao;
      this.usernameDao = usernameDao;
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "users", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @RequestMapping(path = "users/{id}", method = RequestMethod.GET)
    public User findById(@PathVariable int id){
        return userDao.findById(id);
    }

    @RequestMapping(path = "users/{username}", method = RequestMethod.GET)
    public User findByUsername(@PathVariable String username){
        return userDao.findByUsername(username);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "users/{id}/{username}", method = RequestMethod.GET)
    public int findIdByUsername(@PathVariable String username){
        return userDao.findIdByUsername(username);
    }

    @PreAuthorize("permitAll")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "users", method = RequestMethod.POST)
    public boolean createUser(@Valid @RequestBody RegisterUserDTO user){
        try {

        }catch (RestClientResponseException | ResourceAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return userDao.create(user.getUsername(), user.getPassword());
    }

    @RequestMapping(path = "/usercheck/{username}", method = RequestMethod.GET)
    public boolean userCheck(@PathVariable String username){
        return userDao.checkUsername(username);
    }

    @RequestMapping(path = "users/recipientList", method = RequestMethod.GET)
    public List<UsernameDTO> getAllUsersExceptForLoggedInUser(Principal principal) {
           //TODO Would this work??

        List<UsernameDTO> usernames = new ArrayList<>(); //all usernames minus myself
        List<UsernameDTO> allusernames = usernameDao.findAllUsernames(); //all usernames list that we will loop thru
        for(UsernameDTO current: allusernames) {
            if(!current.getUsername().equals(principal.getName()))
            usernames.add(current);
        }
        return usernames;
        // The purpose of this method is to give the user and endpoint that gives them a list of usernames of people they can send money to, not including their own username.
    }
}
