package com.fik.springmvc.dao;
 
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.fik.springmvc.model.Trainer;
 
@Repository("trainerDao")
public class TrainerDaoImpl extends AbstractDao<Integer, Trainer> implements TrainerDao {
 
    public Trainer findById(int id) throws Exception{
        return getByKey(id);
    }
 
    public void saveTrainer(Trainer trainer) throws Exception{
        persist(trainer);
    }
 
    public void deleteTrainerBySsn(String ssn) throws Exception{
        Query query = getSession().createSQLQuery("delete from Trainer where ssn = :ssn");
        query.setString("ssn", ssn);
        query.executeUpdate();
    }
 
    @SuppressWarnings("unchecked")
    public List<Trainer> findAllTrainers() throws Exception{
        Criteria criteria = createEntityCriteria();
        return (List<Trainer>) criteria.list();
    }
 
    public Trainer findTrainerByEmail(String email) throws Exception{
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("emailId", email));
        return (Trainer) criteria.uniqueResult();
    }
    
   
}