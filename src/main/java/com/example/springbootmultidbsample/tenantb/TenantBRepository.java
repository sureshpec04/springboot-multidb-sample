package com.example.springbootmultidbsample.tenantb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springbootmultidbsample.domain.Person;

@Repository
public interface TenantBRepository extends JpaRepository<Person, Long> {

 

}