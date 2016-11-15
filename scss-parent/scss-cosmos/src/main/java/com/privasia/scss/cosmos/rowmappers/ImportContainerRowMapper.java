package com.privasia.scss.cosmos.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.cosmos.util.TextString;

public class ImportContainerRowMapper implements RowMapper<ImportContainer> {

	private ImportContainer importContainer;

	public ImportContainerRowMapper(ImportContainer importContainer) {
		this.importContainer = importContainer;
	}

	@Override
	public ImportContainer mapRow(ResultSet rs, int rowNum) throws SQLException {

		this.importContainer.setAgentCode(TextString.format(rs.getString("orgv05")));
		this.importContainer.setContainerNumber(TextString.format(rs.getString("cnid03")));
		this.importContainer.setInOrOut(rs.getString("hdtp03"));
		this.importContainer.setLine(TextString.format(rs.getString("lynd05")));
		this.importContainer.setFullOrEmpty(TextString.format(rs.getString("cnbt03")));
		this.importContainer.setIsoCode(TextString.format(rs.getString("cnis03")));
		this.importContainer.setOrderFOT(TextString.format(rs.getString("orrf05")));
		this.importContainer.setCurrentPosition(TextString.format(rs.getString("psex45")));
		this.importContainer.setHandlingID(TextString.format(rs.getString("hdid10")));

		return importContainer;
	}

}
