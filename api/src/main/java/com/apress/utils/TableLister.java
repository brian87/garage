package com.apress.utils;

import java.sql.Connection;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class TableLister implements ApplicationRunner {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final DataSource dataSource;

	TableLister(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		try (Connection con = dataSource.getConnection();
				ResultSet rs = con.getMetaData().getTables("poll", "public", "%", null)) {
			while (rs.next()) {
				logger.info("{}", rs.getString(3));
			}
		}
	}

}
