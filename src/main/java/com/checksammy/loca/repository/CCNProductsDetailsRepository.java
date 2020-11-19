package com.checksammy.loca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.CCNProductsDetails;

@Repository
public interface CCNProductsDetailsRepository extends JpaRepository<CCNProductsDetails, Long> {

}
