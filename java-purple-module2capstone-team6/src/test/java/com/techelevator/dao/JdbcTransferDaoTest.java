package com.techelevator.dao;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

public class JdbcTransferDaoTest extends BaseDaoTests {

    private Transfer testTransfer;

    private JdbcTransferDao sut;
    private AccountDao accountDao;




    @Before
    public void setUp(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        testTransfer = new Transfer( "bob", "user", new BigDecimal("1000.00"), "ITS TIME TO PARTAYY", "approved");
        accountDao = new JdbcAccountDao(jdbcTemplate);
        sut = new JdbcTransferDao(jdbcTemplate,accountDao);

    }

    @Test
    public void getCorrectTransfersForSpecificUser() {
        List<TransferDTO> transfers = sut.getAllMyTransfers("bob");
        Assert.assertEquals("Should get all transfers from or to bob", 2, transfers.size());

    }

    @Test
    public void findCorrectTransferById(){
        TransferDTO transfer1 = sut.getTransferById(3001);
        Assert.assertEquals("Get a single transfer by id 3001", 3001, transfer1.getTransferId());

        TransferDTO transfer2 = sut.getTransferById(3002);
        Assert.assertEquals("Get a single transfer by id 3002", 3002, transfer2.getTransferId());


    }

    @Test
    public void createNewTransfer(){
        Transfer createdTransfer =sut.createTransfer(testTransfer, "bob");

        int newId = createdTransfer.getTransferId();
        Assert.assertTrue("This should create a new transfer id that is greater than 0", newId > 0);

        Transfer retrievedTransfer = sut.getTransferByIdUtility(newId);
        assertTransferMatch(createdTransfer, retrievedTransfer);
    }

    private void assertTransferMatch(Transfer expected, Transfer actual) {
        Assert.assertEquals("The transferId do not match", expected.getTransferId(), actual.getTransferId());
        Assert.assertEquals("The sender username do not match", expected.getSenderUsername(), actual.getSenderUsername());
        Assert.assertEquals("The recipient username do not match", expected.getRecipientName(), actual.getRecipientName());
        Assert.assertEquals("The amount do not match", expected.getAmount(), actual.getAmount());
        Assert.assertEquals("The message do not match", expected.getMessage(), actual.getMessage());
        Assert.assertEquals("The status do not match", expected.getStatus(), actual.getStatus());
    }
}
