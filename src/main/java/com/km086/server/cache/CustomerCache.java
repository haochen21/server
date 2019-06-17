package com.km086.server.cache;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.km086.server.model.security.Customer;
import com.km086.server.repository.security.CustomerRepository;

@Component
public class CustomerCache {

	@Autowired
	CustomerRepository customerRepository;

	// key: openId
	ConcurrentHashMap<String, Customer> openIdCache;

	@PostConstruct
	public void init() {
		openIdCache = new ConcurrentHashMap<>();
		List<Customer> customers = customerRepository.findHasOpenId();
		customers.forEach(customer -> openIdCache.putIfAbsent(customer.getOpenId(), customer));
	}

	public Optional<Customer> findCustomer(String openId) {
		return Optional.ofNullable(openIdCache.get(openId));
	}
	
	public void addOrUpdateCustomer(Customer customer) {
		openIdCache.put(customer.getOpenId(), customer);
	}
}
