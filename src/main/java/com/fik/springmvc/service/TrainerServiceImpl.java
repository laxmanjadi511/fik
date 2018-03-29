package com.fik.springmvc.service;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fik.springmvc.dao.TrainerDao;
import com.fik.springmvc.domain.FIKResponse;
import com.fik.springmvc.model.LoginRequest;
import com.fik.springmvc.model.Trainer;
import com.fik.springmvc.utils.FIKStatusCodes;
 

@Service("trainerService")
@Transactional
public class TrainerServiceImpl implements TrainerService {
 
    @Autowired
    private TrainerDao dao;
     
    public Trainer findById(int id) throws Exception{
        return dao.findById(id);
    }
 
    public FIKResponse saveTrainer(Trainer trainer) {
    	FIKResponse response = new FIKResponse();
        try {
        	Trainer dbTrainer = dao.findTrainerByEmail(trainer.getEmailId());
			if(dbTrainer == null ) { 
				dao.saveTrainer(trainer);
				response.setStatus(FIKStatusCodes.SUCCESS);
				response.setResponseCode(FIKStatusCodes.SUCCESS_CODE);
				response.setMessage("Trainer registered successfully.");
			} else {
				response.setStatus(FIKStatusCodes.ALREADY_EXIST);
				response.setResponseCode(FIKStatusCodes.ALREADY_EXIST_CODE);
				response.setMessage("Trainer registered failed. Try with different email id." );
			}
			
		} catch (Exception e) {
			response.setStatus(FIKStatusCodes.FAILED);
			response.setResponseCode(FIKStatusCodes.FAILED_CODE);
			response.setMessage("Trainer registered failed. Try with different email id." + e.getMessage());
			System.out.println(""+e.getMessage());
		}
        return response;
    }
 
    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends. 
     */
    public void updateTrainer(Trainer trainer) throws Exception {
    	Trainer entity = dao.findById(trainer.getId());
        if(entity!=null){
            /*entity.setName(trainer.getName());
            entity.setJoiningDate(trainer.getJoiningDate());
            entity.setSalary(trainer.getSalary());
            entity.setSsn(trainer.getSsn());*/
        }
    }
 
    public void deleteTrainerBySsn(String ssn) throws Exception{
        dao.deleteTrainerBySsn(ssn);
    }
     
    public List<Trainer> findAllTrainers() throws Exception{
        return dao.findAllTrainers();
    }
 /*
    public Trainer findTrainerBySsn(String ssn) throws Exception{
        return dao.findTrainerBySsn(ssn);
    }*/

	@Override
	public boolean isTrainerSsnUnique(Integer id, String ssn) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FIKResponse login(LoginRequest loginRequest) {
		FIKResponse response = new FIKResponse();
        try {
        	Trainer trainer = dao.findTrainerByEmail(loginRequest.getEmailId());
        	
			if(trainer != null && trainer.getEmailId().equals(loginRequest.getEmailId()) && trainer.getPassword().equals(loginRequest.getPassword())) { 
			
				response.setStatus(FIKStatusCodes.SUCCESS);
				response.setResponseCode(FIKStatusCodes.SUCCESS_CODE);
				response.setMessage("Logged in successfully");
			} else if(trainer != null && !(trainer.getPassword().equals(loginRequest.getPassword())))  {
				response.setStatus(FIKStatusCodes.LOGIN_FAILED_PASSWORD_NOT_MATCHED);
				response.setResponseCode(FIKStatusCodes.LOGIN_FAILED_PASSWORD_NOT_MATCHED_CODE);
				response.setMessage("Login Failed. password does not match." );
			} else {
				response.setStatus(FIKStatusCodes.LOGIN_FAILED_EMAIL_DOES_NOT_EXIST);
				response.setResponseCode(FIKStatusCodes.LOGIN_FAILED_EMAIL_DOES_NOT_EXIST_CODE);
				response.setMessage("Login Failed. Email does not exist." );
			}
			
		} catch (Exception e) {
			response.setStatus(FIKStatusCodes.FAILED);
			response.setResponseCode(FIKStatusCodes.FAILED_CODE);
			response.setMessage("Trainer registered failed. Try with different email id." + e.getMessage());
			System.out.println(""+e.getMessage());
		}
        return response;
	}
 
    /*public boolean isTrainerSsnUnique(Integer id, String ssn) throws Exception{
    	Trainer trainer = findTrainerBySsn(ssn);
        return ( trainer == null || ((id != null) && (trainer.getId() == id)));
    }*/
}