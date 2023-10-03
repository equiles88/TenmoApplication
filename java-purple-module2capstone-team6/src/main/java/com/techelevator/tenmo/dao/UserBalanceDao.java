package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.UserBalance;

public interface UserBalanceDao{
    UserBalance findUserBalance(String username);
}
