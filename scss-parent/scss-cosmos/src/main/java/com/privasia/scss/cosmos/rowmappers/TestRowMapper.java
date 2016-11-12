package com.privasia.scss.cosmos.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.privasia.scss.cosmos.dto.TestDto;
import com.privasia.scss.cosmos.util.TextString;

public class TestRowMapper implements RowMapper<TestDto> {

  @Override
  public TestDto mapRow(ResultSet rs, int rowNum) throws SQLException {
    TestDto test = new TestDto();

    test.setAgentCode(TextString.format(rs.getString("orgv05")));
    test.setContainerNo(TextString.format(rs.getString("cnid03")));
    test.setInOrOut(rs.getString("hdtp03"));
    test.setLine(TextString.format(rs.getString("lynd05")));
    test.setFullOrEmpty(TextString.format(rs.getString("cnbt03")));
    test.setiSO(TextString.format(rs.getString("cnis03")));
    test.setOrderFOT(TextString.format(rs.getString("orrf05")));
    test.setCurPos(TextString.format(rs.getString("psex45")));
    test.setiSOInfo(TextString.format(rs.getString("cnis03")));
    test.setSealInfo(TextString.format(rs.getString("hdid10")));

    // selectISOInfo(f, TextString.format(rs.getString("cnis03")), true);
    // selectSealInfo(f, TextString.format(rs.getString("hdid10")), true);

    return test;
  }

}
