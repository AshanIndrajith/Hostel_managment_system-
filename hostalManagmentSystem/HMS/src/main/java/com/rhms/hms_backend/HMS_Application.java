package com.rhms.hms_backend;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class HMS_Application {

	@Autowired
	private DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(HMS_Application.class, args);
	}

	@PreDestroy
	public void closeDataSource() {
		if (dataSource instanceof HikariDataSource) {
			((HikariDataSource) dataSource).close();
		}
	}

}
