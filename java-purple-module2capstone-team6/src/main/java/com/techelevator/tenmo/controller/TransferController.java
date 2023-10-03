package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Path;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/")
public class TransferController {

    private TransferDao transferDao;

    public TransferController(TransferDao transferDao){
        this.transferDao = transferDao;
    }

    @RequestMapping(path = "/transfers/{id}", method = RequestMethod.GET)
    public TransferDTO getTransferById(@PathVariable int id){
        return transferDao.getTransferById(id);
    }
    //This method shows you a specific transfer by entering the transfer id in the endpoint.

    @RequestMapping(path= "/transfers/myTransfers", method = RequestMethod.GET)
    public List<TransferDTO> getAllMyTransfers(Principal principal){
        return transferDao.getAllMyTransfers(principal.getName());
    }
    //This method shows you all transfers involving you as the sender or recipient

    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public Transfer createTransfer(@RequestBody @Valid Transfer transfer, Principal principal){
        return transferDao.createTransfer(transfer, principal.getName());

    }



}
