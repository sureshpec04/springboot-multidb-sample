package com.example.springbootmultidbsample.tenanta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springbootmultidbsample.domain.Person;

@Repository
public interface TenantARepository extends JpaRepository<Person, Long> {

 

}