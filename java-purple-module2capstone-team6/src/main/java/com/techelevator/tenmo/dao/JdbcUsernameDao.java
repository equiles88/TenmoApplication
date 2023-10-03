package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.UsernameDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component

public class JdbcUsernameDao implements UsernameDao{
    private JdbcTemplate jdbcTemplate;
    public JdbcUsernameDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<UsernameDTO> findAllUsernames() {
        List<UsernameDTO> usernames = new ArrayList<>();
        String sql = "SELECT username FROM tenmo_user";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            UsernameDTO current = new UsernameDTO();
            current.setUsername(results.getString("username"));
            usernames.add(current);
        }
        return usernames;
    }


}
