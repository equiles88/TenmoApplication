package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserBalanceDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.UserBalance;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/")
@PreAuthorize("isAuthenticated()")
public class AccountController {
    private AccountDao accountDao;
    private UserBalanceDao userBalanceDao;
    private UserDao userDao;

    public AccountController(AccountDao accountDao, UserBalanceDao userBalanceDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.userBalanceDao = userBalanceDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "accounts/{id}", method = RequestMethod.GET)
    public Account getAccountByID(@PathVariable int id) {
        return accountDao.getAccountById(id);
    }

    @RequestMapping(path = "accounts/mybalance", method = RequestMethod.GET)
    public UserBalance getBalanceByUsername(Principal principal){
        return userBalanceDao.findUserBalance(principal.getName());
    }

}


