package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

public interface TransferDao {



    List<TransferDTO> getAllMyTransfers(String username);

    TransferDTO getTransferById(int id);

    Transfer createTransfer(Transfer transfer, String username);





}
