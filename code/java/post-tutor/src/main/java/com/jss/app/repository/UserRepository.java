package com.jss.app.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jss.app.model.entity.User;

public interface UserRepository extends RowBaseRepository<User, Long> {
	
	User findByTypeAndUsernameAndPwd(Integer type, String username, String pwd);
	
	@Transactional
	void deleteByStudno(String studno);
	
	@Query(value= "UPDATE t_user  SET pwd = ?2 WHERE id = ?1",nativeQuery=true)
	@Modifying
	@Transactional
	void updatePwd(Long userId,String pwd);
	

}
