package com.jss.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.jss.app.model.entity.Institute;
import com.jss.app.repository.InstituteRepository;
import com.jss.app.service.PoiService;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
public class PostTutorApplication implements ApplicationRunner {

	@Autowired
	private PoiService poiService;
	
	@Autowired
	private InstituteRepository instituteRepository;

	public static void main(String[] args) {
		SpringApplication.run(PostTutorApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		initData();

	}

	@Transactional
	private void initData() {
		Institute findByName = instituteRepository.findByName("信息与控制工程学院");
		System.out.println(findByName);

	}

}
