package com.privasia.scss.cosmos.repository;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.cosmos.dto.TestDto;
import com.privasia.scss.cosmos.rowmappers.TestRowMapper;



@Repository
public class TestRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;


  private String sql = "SELECT cnid03" + ", hdtp03" + ", cnbt03" + ", lynd05" + ", orgv05" + ", cnis03" + ", orrf05"
      + ", psex45" + ", hdid10" + " FROM TCTCSE.cthndl5" + ", TCTCSE.ctlthd2" + ", TCTCSE.ctordru" + ", PSPACEE.spcinfi"
      + " WHERE hdtm03='WPT1'" + " AND cnid03=?"
      // + ", PSPACEE.spcinfi" + " WHERE hdtm03='WPT1'" + " AND cnid03 LIKE " + "'%" + containerNo +
      // "%'"
      + " AND hdtp03='OUT'" + " AND lttp10='ORD'" + " AND hdid10 = hdid03" + " AND orid10 = orid05"
      + " AND lhfs10='RGS'" + " AND (ortp05='FOT'" + " OR ortp05 = 'BKG'" + " OR ortp05 = 'CNA' )"
      + " AND trmc45='WPT1'" + " AND cnid45=cnid03" + " AND psex45 IS NOT NULL";


  @Transactional(readOnly = true)
  public List<TestDto> testQuery(String containerNo) {
    System.out.println("CAME HERE TestRepository :" + containerNo);
    containerNo = StringUtils.upperCase(containerNo);
    return jdbcTemplate.query(sql, new Object[] {containerNo}, new TestRowMapper());
  }

}
