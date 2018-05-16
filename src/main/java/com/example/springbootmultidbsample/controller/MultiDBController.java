package com.example.springbootmultidbsample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootmultidbsample.domain.Person;
import com.example.springbootmultidbsample.tenanta.TenantARepository;
import com.example.springbootmultidbsample.tenantb.TenantBRepository;
import com.example.springbootmultidbsample.tenantc.Tenant_C_Repository;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MultiDBController {

  private final TenantARepository fooRepo;
  private final TenantBRepository barRepo;
  private final Tenant_C_Repository tenantCRepo;

  @Autowired
  MultiDBController(TenantARepository fooRepo, TenantBRepository barRepo, Tenant_C_Repository tenantCRepo) {
    this.fooRepo = fooRepo;
    this.barRepo = barRepo;
    this. tenantCRepo = tenantCRepo;
  }

  @RequestMapping("/multidb/{id}")
  public String fooBar(@PathVariable("id") Long id) throws Exception {
	 
    Person foo = fooRepo.findById(id).orElseThrow(() -> new Exception("Unable to find a person with id in Tenant A Datasource : " + id));
    log.info("Person retrieved from Tenant A datasource: {} " , foo.toString());
    Person bar = barRepo.findById(id).orElseThrow(() -> new Exception("Unable to find a person with id in Tenant B DB : " + id));
    log.info("Person retrieved from Tenant B datasource: {} " , bar.toString());
    
    Person thirdTenant = tenantCRepo.findById(id).orElseThrow(() -> new Exception("Unable to find a person with id in Tenant C DB : " + id));
    log.info("Person retrieved from Tenant C datasource: {} " , thirdTenant);
    
    return "Tenant A Full Name: " + foo.getFirstName() + " " + foo.getLastName()
    + " Tenant B Full Name: "+  bar.getFirstName() + " " + bar.getLastName() + "!";
  }

}