package com.jss.app.repository;

import com.jss.app.model.entity.User;

public interface UserRepository extends RowBaseRepository<User, Long> {
	User findByTypeAndUsernameAndPwd(Integer type, String username, String pwd);
}
