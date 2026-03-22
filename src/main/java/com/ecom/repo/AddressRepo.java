package com.ecom.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.entity.Address;

public interface AddressRepo extends JpaRepository<Address, Long>{

}
