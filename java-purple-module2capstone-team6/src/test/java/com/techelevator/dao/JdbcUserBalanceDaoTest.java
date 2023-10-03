package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserBalanceDao;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserBalance;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class JdbcUserBalanceDaoTest extends BaseDaoTests {

    private static final UserBalance USER_BALANCE_1 = new UserBalance("bob",new BigDecimal("1000.00"));
    private static final UserBalance USER_BALANCE_2 = new UserBalance("user",new BigDecimal("1000.00"));
    private JdbcUserBalanceDao sut;

    @Before
    public void setUp() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcUserBalanceDao(jdbcTemplate);
    }

    @Test
    public void findCorrectUserBalance() {
        UserBalance userBalance1 = sut.findUserBalance("bob");
        assertUserBalanceMatch(userBalance1, USER_BALANCE_1);

        UserBalance userBalance2 = sut.findUserBalance("user");
        assertUserBalanceMatch(userBalance2, USER_BALANCE_2);
    }

    public void assertUserBalanceMatch(UserBalance expected, UserBalance actual){
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getBalance(), actual.getBalance());
    }



}
