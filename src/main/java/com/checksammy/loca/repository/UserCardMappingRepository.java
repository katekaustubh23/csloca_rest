package com.checksammy.loca.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.UserCardMapping;

@Repository
public interface UserCardMappingRepository extends JpaRepository<UserCardMapping, Serializable>{

	List<UserCardMapping> findByUserId(Long userId);

}
