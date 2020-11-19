package com.checksammy.loca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.checksammy.loca.model.ApplicationVersion;

public interface ApplicationVersionRepository extends JpaRepository<ApplicationVersion, Long>{

}
