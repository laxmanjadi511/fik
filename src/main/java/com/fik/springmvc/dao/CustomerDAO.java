package com.fik.springmvc.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fik.springmvc.model.Customer;

@Repository
public class CustomerDAO {
	
	private static List<Customer> customers;
	{
		customers=new ArrayList<>();
		customers.add(new Customer(101, "Himanshu", "Roy", "khr@gmail.com", "1234567879"));
		customers.add(new Customer(201, "abc", "fff", "abc@gmail.com", "45789545"));
	}

	public List list(){
		return customers;
		
	}
	
	public Customer get(Long id){
		for (Customer c : customers) {
			if (c.getId().equals(id)) {
				return c;
			}
		}
		return null;
	}
	
	public Customer create(Customer customer){
		customer.setId(System.currentTimeMillis());
		customers.add(customer);
		return customer;
		
	}
	
	public Long delete(Long id){
		for (Customer c : customers) {
			if (c.getId().equals(id)) {
				customers.remove(c);
				return id;
			}
		}
		return null;
	}
	
	public Customer update(Long id,Customer customer){
		for (Customer c : customers) {
			if (c.getId().equals(id)) {
				customer.setId(c.getId());
				customers.remove(c);
				customers.add(customer);
				return customer;
				
				
			}
		}
		return null;
	}
}
