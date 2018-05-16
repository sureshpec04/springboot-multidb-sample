package com.example.springbootmultidbsample.tenantc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springbootmultidbsample.domain.Person;

@Repository
public interface Tenant_C_Repository extends JpaRepository<Person, Long> {

 

}