package com.fik.springmvc.dao;
import java.util.List;

import com.fik.springmvc.model.Trainer;
 
public interface TrainerDao {
 
    Trainer findById(int id) throws Exception;
 
    void saveTrainer(Trainer trainer) throws Exception;
     
    void deleteTrainerBySsn(String ssn) throws Exception;
     
    List<Trainer> findAllTrainers() throws Exception;
 
    Trainer findTrainerByEmail(String ssn) throws Exception;
 
}