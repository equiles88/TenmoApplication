package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import com.techelevator.tenmo.model.TransferDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;
    private AccountDao accountDao;



    public JdbcTransferDao(JdbcTemplate jdbcTemplate, AccountDao accountDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountDao = accountDao;

    }




    @Override
    public List<TransferDTO> getAllMyTransfers(String username) {
        List<TransferDTO> transfers = new ArrayList<>();
        String sql = " SELECT transfer_id , amount , sender_username , recipient_username FROM transfer WHERE sender_username = ? OR recipient_username = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username, username);
        while(results.next()) {
            TransferDTO transfer = new TransferDTO();
            transfer.setTransferId(results.getInt("transfer_id"));
            transfer.setTransferAmount(results.getBigDecimal("amount"));
            transfer.setFrom(results.getString("sender_username"));
            transfer.setTo(results.getString("recipient_username"));
            transfers.add(transfer);
        }
        return transfers;
    }

    @Override
    public TransferDTO getTransferById(int id) {
        TransferDTO transfer = new TransferDTO();
        String sql = "SELECT transfer_id, sender_username, recipient_username, amount, message, status FROM transfer WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            transfer.setTransferId(results.getInt("transfer_id"));
            transfer.setTransferAmount(results.getBigDecimal("amount"));
            transfer.setFrom(results.getString("sender_username"));
            transfer.setTo(results.getString("recipient_username"));
        }
        return transfer;
    }



    @Override
    public Transfer createTransfer(Transfer transfer, String username) {
        String sql = "INSERT INTO transfer (sender_username, recipient_username, amount, message, status) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING transfer_id;";
       Transfer diffTransfer = new Transfer();

        if(!username.equals(transfer.getRecipientName()) && transfer.getAmount().compareTo(BigDecimal.valueOf(0)) > 0

        && accountDao.getAccountByUsername(username).getBalance().compareTo(transfer.getAmount()) >=  0) {
            System.out.println("something");
            try {
                Integer newTransferId = jdbcTemplate.queryForObject(sql, Integer.class, username, transfer.getRecipientName(), transfer.getAmount(), transfer.getMessage(), transfer.getStatus());
                if (newTransferId > 0) {
                    diffTransfer = getTransferByIdUtility(newTransferId);
                }
            }catch(Exception e){
                System.out.println("Error occurred");
            }
          diffTransfer.setStatus("approved");
            String updateSenderBalSql = "UPDATE account SET balance = balance + ? WHERE user_id = (SELECT user_id FROM tenmo_user WHERE username ILIKE ?)";
            jdbcTemplate.update(updateSenderBalSql, transfer.getAmount().negate(), username);
            jdbcTemplate.update(updateSenderBalSql, transfer.getAmount(), transfer.getRecipientName());
        }else{
            diffTransfer.setStatus("rejected");
        }

        return diffTransfer;
    }

    public Transfer getTransferByIdUtility(int id) {
        Transfer transfer = new Transfer();
        String sql = "SELECT transfer_id, sender_username, recipient_username, amount, message, status FROM transfer WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }


    private Transfer mapRowToTransfer (SqlRowSet results){
        Transfer transfer = new Transfer();
        transfer.setTransferId(results.getInt("transfer_id"));
        transfer.setSenderUsername(results.getString("sender_username"));
        transfer.setRecipientName(results.getString("recipient_username"));
        transfer.setAmount(results.getBigDecimal("amount"));
        transfer.setMessage(results.getString("message"));
        transfer.setStatus(results.getString("status"));
        return transfer;
    }
}
