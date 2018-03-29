package com.fik.springmvc.service;
import java.util.List;

import com.fik.springmvc.domain.FIKResponse;
import com.fik.springmvc.model.LoginRequest;
import com.fik.springmvc.model.Trainer;
 
public interface TrainerService {
 
    Trainer findById(int id) throws Exception;
    
    FIKResponse login(LoginRequest loginRequest);
     
    FIKResponse saveTrainer(Trainer trainer);
     
    void updateTrainer(Trainer trainer) throws Exception;
     
    void deleteTrainerBySsn(String ssn) throws Exception;
 
    List<Trainer> findAllTrainers() throws Exception; 
     
    //Trainer findTrainerBySsn(String ssn) throws Exception;
 
    boolean isTrainerSsnUnique(Integer id, String ssn) throws Exception;
     
}