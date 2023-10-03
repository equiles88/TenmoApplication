package com.techelevator.dao;


import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class JdbcUserDaoTests extends BaseDaoTests{

    private JdbcUserDao sut;



    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcUserDao(jdbcTemplate);
    }


    @Test
    public void createNewUser() {
        boolean userCreated = sut.create("TEST_USER","test_password");
        Assert.assertTrue(userCreated);
        User user = sut.findByUsername("TEST_USER");
        Assert.assertEquals("TEST_USER", user.getUsername());
    }

    @Test
    public void findAll() {
        List<User> users = sut.findAll();
        Assert.assertEquals( 2, users.size());
    }

    @Test
    public void findByUsername() {
        User user1 = sut.findByUsername("bob");
        Assert.assertEquals("Should only one user should show up named bob", "bob", user1.getUsername());

        User user2 = sut.findByUsername("user");
        Assert.assertEquals("Should only one user should show up named bob", "user", user2.getUsername());
    }

    @Test
    public void findById() {
        User user1 = sut.findById(1001);
        Assert.assertEquals("Expect only one user to show up",1001, user1.getId());

        User user2 = sut.findById(1002);
        Assert.assertEquals("Expect only one user to show up",1002, user2.getId());
    }

    @Test
    public void findIdByUsername() {
        int user1 = sut.findIdByUsername("bob");
        Assert.assertEquals("Should return the Id of bob", 1001, user1);

        int user2 = sut.findIdByUsername("user");
        Assert.assertEquals("Should return the Id of user", 1002, user2);


    }



}
