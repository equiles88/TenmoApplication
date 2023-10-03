package com.techelevator.dao;

import com.techelevator.tenmo.controller.AccountController;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

public class JdbcAccountDaoTests extends BaseDaoTests{

    private static final Account ACCOUNT_1 = new Account(2001, 1001, new BigDecimal("1000.00"));
    private static final Account ACCOUNT_2 = new Account(2002, 1002, new BigDecimal("1000.00"));

    private JdbcAccountDao sut;


    @Before
    public void setUp() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);

    }



    @Test
    public void getAccountById() {
        Account account1 = sut.getAccountById(2001);
        assertAccountMatch(account1, ACCOUNT_1);

        Account account2 = sut.getAccountById(2002);
        assertAccountMatch(account2, ACCOUNT_2);
    }

    @Test
    public void getAccountById_returns_null_when_id_is_not_found(){
        Account account1 = sut.getAccountById(5344);
        Assert.assertNull(account1);

        Account account2 = sut.getAccountById(6344);
        Assert.assertNull(account2);

    }

    @Test
    public void getAccountByUsername() {
        Account account1 = sut.getAccountByUsername("bob");
        assertAccountMatch(account1, ACCOUNT_1);

        Account account2 = sut.getAccountByUsername("user");
        assertAccountMatch(account2, ACCOUNT_2);
    }



    public void assertAccountMatch(Account expected, Account actual) {
        Assert.assertEquals(expected.getAccountId(), actual.getAccountId());
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        Assert.assertEquals(expected.getBalance(), actual.getBalance());
    }


}
