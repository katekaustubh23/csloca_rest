package com.checksammy.loca.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.ZapierSample;

@Repository
public interface ZapierSampleRepository extends JpaRepository<ZapierSample, Serializable>{

}
