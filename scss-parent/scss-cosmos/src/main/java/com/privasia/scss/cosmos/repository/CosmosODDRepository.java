package com.privasia.scss.cosmos.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class CosmosODDRepository {

	@Autowired
	@Qualifier("as400JdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Value("${odd.validateODDContainer}")
	private String queryValidateODDContainer;

	@Transactional(readOnly = true)
	public boolean validateODDContainer(String containerNo) {
		containerNo = StringUtils.upperCase(containerNo);
		return jdbcTemplate.queryForObject(queryValidateODDContainer, new Object[] { containerNo }, this::validateContainer);
	}

	private boolean validateContainer(ResultSet rs, int rowNum) throws SQLException {
		
		boolean status = true;
		
		if (rs.next()) {
			System.out.println("ORDER_TYPE : "+rs.getString("ORDER_TYPE"));
			System.out.println("ORDER_STATUS : "+rs.getString("ORDER_STATUS"));
			System.out.println("CONTAINER_HANDLING_STATUS : "+rs.getString("CONTAINER_HANDLING_STATUS"));
			
			if(StringUtils.equals(rs.getString("CONTAINER_HANDLING_STATUS"), "EXE")){
				status =  true;
			}else if(StringUtils.equals(rs.getString("CONTAINER_HANDLING_STATUS"), "ACT")){
				status =  false;
			}else if(StringUtils.equals(rs.getString("CONTAINER_HANDLING_STATUS"), "RGS")){
				status =  false;
			}
		}
		return status;
	}

	
}
