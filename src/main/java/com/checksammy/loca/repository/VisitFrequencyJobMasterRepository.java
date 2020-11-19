package com.checksammy.loca.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.VisitFrequencyJobMaster;

@Repository
public interface VisitFrequencyJobMasterRepository extends JpaRepository<VisitFrequencyJobMaster, Serializable>{

}
