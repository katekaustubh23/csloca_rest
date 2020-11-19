package com.checksammy.loca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.TransFlow;

@Repository
public interface TransFlowRepository extends JpaRepository<TransFlow, Long>{

}
