package com.techelevator.tenmo.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.UserBalance;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }


    @Override
    public Account getAccountById(int id) {
        Account account = null;
        String sql = "SELECT account_id, user_id, balance FROM account WHERE account_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()){
            account = mapRowToAccount(results);
        }
        return account;
    }

    @Override
    public Account getAccountByUsername(String username){
        Account account = null;
        String sql = "SELECT account_id, account.user_id, balance, username FROM account JOIN tenmo_user ON tenmo_user.user_id = account.user_id WHERE username ILIKE ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        if (results.next()){
            account = mapRowToAccount(results);
        }
        return account;
    }

    private Account mapRowToAccount(SqlRowSet results){
        Account account = new Account();
        account.setAccountId(results.getInt("account_id"));
        account.setUserId(results.getInt("user_id"));
        account.setBalance(results.getBigDecimal("balance"));
//        account.setUsername(results.getString("username"));
        return account;
    }
}
