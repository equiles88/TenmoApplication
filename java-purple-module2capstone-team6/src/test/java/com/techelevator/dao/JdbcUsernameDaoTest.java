package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcUsernameDao;
import com.techelevator.tenmo.model.UsernameDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcUsernameDaoTest extends BaseDaoTests {


    private JdbcUsernameDao sut;

    @Before
    public void setUp() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcUsernameDao(jdbcTemplate);
    }


    @Test
    public void getCorrectAmountOfUsernames() {
        List<UsernameDTO> usernames = sut.findAllUsernames();

        Assert.assertEquals("We should get 3 Usernames shown", 2, usernames.size());
    }


}
