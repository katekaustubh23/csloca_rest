package com.checksammy.loca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.WorkStatus;

@Repository
public interface WorkStatusRepository extends JpaRepository<WorkStatus, Long>{

}
