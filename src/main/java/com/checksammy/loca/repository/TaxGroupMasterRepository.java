package com.checksammy.loca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.TaxGroupMaster;

@Repository
public interface TaxGroupMasterRepository extends JpaRepository<TaxGroupMaster, Long>{

}
