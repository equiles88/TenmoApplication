package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.UserBalance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component

public class JdbcUserBalanceDao implements UserBalanceDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcUserBalanceDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public UserBalance findUserBalance(String username) {
        UserBalance current = new UserBalance();
        String sql = "SELECT username, balance FROM account JOIN tenmo_user on tenmo_user.user_id = account.user_id WHERE username ILIKE ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        if (results.next()){
            current.setUsername(results.getString("username"));
           current.setBalance(results.getBigDecimal("balance"));
        }
        return current;
}
    //This method takes in the username of the user logged in, and shows them their balance.


}
