package com.privasia.scss.cosmos.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CosmosODDRepository {

	private JdbcTemplate jdbcTemplate;

	@Value("${odd.validateODDContainer}")
	private String queryValidateODDContainer;
	
	@Autowired
	public void setJdbcTemplate(@Qualifier("as400JdbcTemplate") JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional(value = "as400TransactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public boolean validateODDContainer(String containerNo) {
		containerNo = StringUtils.upperCase(containerNo);
		try {
			return jdbcTemplate.queryForObject(queryValidateODDContainer, new Object[] { containerNo },
					this::validateContainer);
		} catch (EmptyResultDataAccessException er) {
			return false;
		}

	}

	private boolean validateContainer(ResultSet rs, int rowNum) throws SQLException {

		boolean status = false;

		if (StringUtils.equals(rs.getString("CONTAINER_HANDLING_STATUS"), "EXE")) {
			status = true;
		} else if (StringUtils.equals(rs.getString("CONTAINER_HANDLING_STATUS"), "ACT")) {
			status = false;
		} else if (StringUtils.equals(rs.getString("CONTAINER_HANDLING_STATUS"), "RGS")) {
			status = false;
		}

		return status;
	}

}
