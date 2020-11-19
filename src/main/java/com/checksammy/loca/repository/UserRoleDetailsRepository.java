/**
 * 
 */
package com.checksammy.loca.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.UserRoleDetailsView;

/**
 * @author Abhishek Srivastava
 *
 */
@Repository
public interface UserRoleDetailsRepository extends JpaRepository<UserRoleDetailsView, Serializable> {

	@Query(value="SELECT * FROM vgetuserandrole WHERE username =? and is_user_deleted=0", nativeQuery = true)
	List<UserRoleDetailsView> findByUsername(String username);	
	
}
