package com.jss.app.repository;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface RowBaseRepository<T, Long> extends PagingAndSortingRepository<T, Long> {
	 List<T> findTop3ByOrderByUpdateTimeDescIdAsc();
}
