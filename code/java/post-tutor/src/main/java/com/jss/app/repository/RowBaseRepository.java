package com.jss.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@SuppressWarnings("hiding")
@NoRepositoryBean
public interface RowBaseRepository<T, Long> extends JpaRepository<T, Long> {
	 List<T> findTop3ByOrderByUpdateTimeDescIdAsc();
}
