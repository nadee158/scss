package com.privasia.scss.cosmos.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

import com.privasia.scss.common.dto.SealInfo;
import com.privasia.scss.cosmos.util.TextString;

public class ImportSealRowMapper implements RowMapper<Optional<List<SealInfo>>> {

	@Override
	public Optional<List<SealInfo>> mapRow(ResultSet rs, int rowNum) throws SQLException {

		SealInfo sealInfo = null;
		List<SealInfo> sealInfoList = new ArrayList<>();
		if (rs.next()) {
			sealInfo = new SealInfo();
			sealInfo.setSealOrigin(TextString.format(rs.getString("slor2k")));
			sealInfo.setSealType(TextString.format(rs.getString("sltp2k")));
			sealInfo.setSealNo(TextString.format(rs.getString("seal2k")));
			sealInfoList.add(sealInfo);
		}
		if (rs.next()) {
			sealInfo = new SealInfo();
			sealInfo.setSealOrigin(TextString.format(rs.getString("slor2k")));
			sealInfo.setSealType(TextString.format(rs.getString("sltp2k")));
			sealInfo.setSealNo(TextString.format(rs.getString("seal2k")));
			sealInfoList.add(sealInfo);
		}

		return Optional.of(sealInfoList);
	}

}
