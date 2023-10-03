package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.UsernameDTO;

import java.util.List;


public interface UsernameDao {
    List<UsernameDTO> findAllUsernames();
}
