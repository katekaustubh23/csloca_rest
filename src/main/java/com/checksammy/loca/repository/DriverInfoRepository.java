package com.checksammy.loca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.DriverInfo;

@Repository
public interface DriverInfoRepository extends JpaRepository<DriverInfo, Long>{

}
