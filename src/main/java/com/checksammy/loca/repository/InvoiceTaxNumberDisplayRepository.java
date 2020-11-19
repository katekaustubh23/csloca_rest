package com.checksammy.loca.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.InvoiceTaxNumberDisplay;

@Repository
public interface InvoiceTaxNumberDisplayRepository extends JpaRepository<InvoiceTaxNumberDisplay, Serializable>{

}
