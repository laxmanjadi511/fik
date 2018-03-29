package com.fik.springmvc.controller;

import java.util.List;

import javax.persistence.PostUpdate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fik.springmvc.dao.CustomerDAO;
import com.fik.springmvc.model.Customer;

public class CustomerRestController {
	
	CustomerDAO customerDAO;
	
	@GetMapping("/customers")
	public List getCustomers(){
		return customerDAO.list();
	}
	
	@GetMapping("/customers/{id}")
	public ResponseEntity getcustomer(@PathVariable("id") Long id){
		Customer customer=customerDAO.get(id);
		if (customer==null) {
			return new ResponseEntity("No Customer found for ID" + id,HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity(customer,HttpStatus.OK);
	}
	
	@PostMapping(value="/customers")
	public ResponseEntity creatcustomers(@RequestBody Customer customer)
	{
		customerDAO.create(customer);
		return new ResponseEntity(customer,HttpStatus.OK);
	}
	
	@DeleteMapping("/customers/{id}")
	public ResponseEntity deletecustomer(@PathVariable Long id){
		if (null== customerDAO.delete(id)) {
			
			return new ResponseEntity("No Customer found for ID" + id,HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity(id,HttpStatus.OK);
	}
	
	@PutMapping("/customers/{id}")
	public ResponseEntity updatecustomer(@PathVariable Long id,@RequestBody Customer customer){
		customer = customerDAO.update(id, customer);

		if (null == customer) {
			return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(customer, HttpStatus.OK);
	}

}
