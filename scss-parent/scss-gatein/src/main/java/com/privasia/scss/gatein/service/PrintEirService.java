package com.privasia.scss.gatein.service;

import org.springframework.stereotype.Service;

import com.privasia.scss.core.dto.GateInForm;

@Service("")
public class PrintEirService {

  public void insertScssPrintEir(GateInForm f, String customerIpAddress) throws Exception {

    String lineInfoC1 = "";
    String lineInfo2C1 = "";
    String lineInfoC2 = "";
    String lineInfo2C2 = "";
    String icNoOrPassportNo = "";
    String sealC1 = "";
    String sealC2 = "";

    // // First Container
    // if ("F".equalsIgnoreCase(f.getFullOrEmptyC1())) {
    // if (StringUtils.isNotBlank(f.getSeal1C1())) {
    // lineInfoC1 += f.getSeal1OriginC1() + " " + f.getSeal1TypeC1() + " " + f.getSeal1C1() + " ";
    // sealC1 += f.getSeal1OriginC1() + " " + f.getSeal1TypeC1() + " " + f.getSeal1C1();
    // }
    //
    // if (StringUtils.isNotBlank(f.getSeal2C1())) {
    // lineInfoC1 += f.getSeal2OriginC1() + " " + f.getSeal2TypeC1() + " " + f.getSeal2C1() + " ";
    //
    // if (StringUtils.isNotBlank(sealC1)) {
    // sealC1 += " | " + f.getSeal2OriginC1() + " " + f.getSeal2TypeC1() + " " + f.getSeal2C1();
    // } else {
    // sealC1 += f.getSeal2OriginC1() + " " + f.getSeal2TypeC1() + " " + f.getSeal2C1();
    // }
    // }
    // }
    //
    // if ("Y".equalsIgnoreCase(f.getOperationReeferC1())) {
    // lineInfoC1 += "Reefer:" + " " + f.getOperationReeferC1() + " " + f.getTempC1() +
    // f.getTempUnitC1() + " ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getIMDGC1())) {
    // lineInfoC1 += "IMDG:" + " " + f.getIMDGC1() + " ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getUNC1())) {
    // lineInfoC1 += "UN:" + " " + f.getUNC1() + " ";
    // }
    //
    // // // Second Line
    // if (StringUtils.isNotBlank(f.getOOGOHC1())) {
    // lineInfo2C1 += "OH :" + " " + f.getOOGOHC1() + " ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getOOGOLC1())) {
    // lineInfo2C1 += "OL :" + " " + f.getOOGOLC1() + " ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getOOGOFC1())) {
    // lineInfo2C1 += "OF :" + " " + f.getOOGOFC1() + " ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getOOGOAC1())) {
    // lineInfo2C1 += "OA :" + " " + f.getOOGOAC1() + " ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getOOGORC1())) {
    // lineInfo2C1 += "OR :" + " " + f.getOOGORC1() + " ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getDamage1C1())) {
    // lineInfo2C1 += "Damage: ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getDamage1C1())) {
    // lineInfo2C1 += f.getDamage1C1() + " ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getDamage2C1())) {
    // lineInfo2C1 += f.getDamage2C1() + " ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getDamage3C1())) {
    // lineInfo2C1 += f.getDamage3C1() + " ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getDamage4C1())) {
    // lineInfo2C1 += f.getDamage4C1() + " ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getDamage5C1())) {
    // lineInfo2C1 += f.getDamage5C1() + " ";
    // }
    //
    // /***********************************************************/
    //
    // /***************** Second Container No ********************/
    // if (f.getContainerNoC2() != "") {
    // // First Container
    // if ("F".equalsIgnoreCase(f.getFullOrEmptyC2())) {
    // if (StringUtils.isNotBlank(f.getSeal1C1())) {
    // lineInfoC2 += f.getSeal1OriginC2() + " " + f.getSeal1TypeC2() + " " + f.getSeal1C2() + " ";
    // sealC1 += f.getSeal1OriginC2() + " " + f.getSeal1TypeC2() + " " + f.getSeal1C2();
    // }
    //
    // if (StringUtils.isNotBlank(f.getSeal2C1())) {
    // lineInfoC2 += f.getSeal2OriginC2() + " " + f.getSeal2TypeC2() + " " + f.getSeal2C2() + " ";
    //
    // if (StringUtils.isNotBlank(sealC2)) {
    // sealC1 += " | " + f.getSeal2OriginC2() + " " + f.getSeal2TypeC2() + " " + f.getSeal2C2();
    // } else {
    // sealC1 += f.getSeal2OriginC2() + " " + f.getSeal2TypeC2() + " " + f.getSeal2C2();
    // }
    // }
    // }
    //
    // if ("Y".equalsIgnoreCase(f.getOperationReeferC2())) {
    // lineInfoC2 += "Reefer:" + " " + f.getOperationReeferC2() + " " + f.getTempC2() +
    // f.getTempUnitC2() + " ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getIMDGC2())) {
    // lineInfoC2 += "IMDG:" + " " + f.getIMDGC2() + " ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getUNC2())) {
    // lineInfoC2 += "UN:" + " " + f.getUNC2() + " ";
    // }
    //
    // // // Second Line
    // if (StringUtils.isNotBlank(f.getOOGOHC2())) {
    // lineInfo2C2 += "OH :" + " " + f.getOOGOHC2() + " ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getOOGOLC2())) {
    // lineInfo2C2 += "OL :" + " " + f.getOOGOLC2() + " ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getOOGOFC2())) {
    // lineInfo2C2 += "OF :" + " " + f.getOOGOFC2() + " ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getOOGOAC2())) {
    // lineInfo2C2 += "OA :" + " " + f.getOOGOAC2() + " ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getOOGORC2())) {
    // lineInfo2C2 += "OR :" + " " + f.getOOGORC2() + " ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getDamage1C2())) {
    // lineInfo2C2 += "Damage: ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getDamage1C2())) {
    // lineInfo2C2 += f.getDamage1C2() + " ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getDamage2C2())) {
    // lineInfo2C2 += f.getDamage2C2() + " ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getDamage3C2())) {
    // lineInfo2C2 += f.getDamage3C2() + " ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getDamage4C2())) {
    // lineInfo2C2 += f.getDamage4C2() + " ";
    // }
    //
    // if (StringUtils.isNotBlank(f.getDamage5C2())) {
    // lineInfo2C2 += f.getDamage5C2() + " ";
    // }
    // }
    // /********************************************************/
    //
    // if ("MY".equalsIgnoreCase(f.getNationality())) {
    // if (StringUtils.isNotBlank(f.getNewICNo())) {
    // icNoOrPassportNo = f.getNewICNo();
    // } else {
    // icNoOrPassportNo = f.getOldICNo();
    // }
    // } else {
    // icNoOrPassportNo = f.getPassportNo();
    // }
    //
    // String weight01 = null;
    // if (f.isShipperVGM() && f.isC1WithInTolerance()) {
    // weight01 = String.valueOf(f.getShipperVGMC1());
    // } else {
    // if (StringUtils.isNotBlank(f.getNetWeightC1())) {
    // weight01 = f.getNetWeightC1();
    // }
    // }
    //
    // String weight02 = null;
    // if (StringUtils.isNotBlank(f.getContainerNoC2())) {
    // if (f.isShipperVGM() && f.isC2WithInTolerance()) {
    // weight02 = String.valueOf(f.getShipperVGMC2());
    // } else {
    // if (StringUtils.isNotBlank(f.getNetWeightC2())) {
    // weight02 = f.getNetWeightC2();
    // }
    // }
    // }
    //
    //
    // scssPrintEirIdSeq = SQL.NEXT_VALUE("SEQ_SCSS_PRINT_EIR");
    //
    // String sql = "INSERT INTO SCSS_PRINT_EIR(" + " PRINT_NO " + ", TIME_IN " + ", CALL_CARD " +
    // ", TRUCK_HEAD_NO "
    // + ", COMP_NAME_PRINT " + ", BAYCODE_C1 " + ", INOROUT_C1 " + ", FULL_OR_EMPTY_C1 " + ",
    // POSITIONONTRUCK_C1 "
    // + ", CONTAINERNO_C1 " + ", LINE_C1 " + ", ISO_C1 " + ", SIZE_C1 " + ", HEIGHT_C1 " + ",
    // TYPE_C1 "
    // + ", NETWEIGHT_C1 " + ", CONTAINERNO_C2 " + ", BAYCODE_C2 " + ", INOROUT_C2 " + ",
    // FULLOREMPTY_C2 "
    // + ", POSITIONONTRUCK_C2 " + ", LINE_C2 " + ", ISO_C2 " + ", SIZE_C2 " + ", HEIGHT_C2 " + ",
    // TYPE_C2 "
    // + ", NETWEIGHT_C2 " + ", LINE_INFO1_C1 " + ", LINE_INFO2_C1 " + ", LINE_INFO1_C2 " + ",
    // LINE_INFO2_C2 "
    // + ", ICNOORPASSPORT " + ", SCUNAME " + ", TRUCKPLATENO " + ", GATEINNO " + ", STATUS " + ",
    // CLIENT_IP "
    // + ", SEAL_C1 " + ", SEAL_C2 " + ") VALUES ( " + SQL.format(scssPrintEirIdSeq) + ", " +
    // SQL.format(f.getTimeIn())
    // + ", " + SQL.format(f.getCallCard()) + ", " + SQL.format(f.getTruckHeadNo()) + ", "
    // + SQL.format(f.getCompNamePrint()) + ", " + SQL.format(f.getBayCodeC1()) + ", " +
    // SQL.format(f.getInOrOutC1())
    // + ", " + SQL.format(f.getFullOrEmptyC1()) + ", " + SQL.format(f.getPositionOnTruckC1()) + ",
    // "
    // + SQL.format(f.getContainerNoC1()) + ", " + SQL.format(f.getLineC1()) + ", " +
    // SQL.format(f.getISOC1()) + ", "
    // + SQL.format(f.getSizeC1()) + ", " + SQL.format(f.getHeightC1()) + ", " +
    // SQL.format(f.getTypeC1()) + ", "
    // + SQL.format(weight01) + ", " + SQL.format(f.getContainerNoC2()) + ", " +
    // SQL.format(f.getBayCodeC2()) + ", "
    // + SQL.format(f.getInOrOutC2()) + ", " + SQL.format(f.getFullOrEmptyC2()) + ", "
    // + SQL.format(f.getPositionOnTruckC2()) + ", " + SQL.format(f.getLineC2()) + ", " +
    // SQL.format(f.getISOC2())
    // + ", " + SQL.format(f.getSizeC2()) + ", " + SQL.format(f.getHeightC2()) + ", " +
    // SQL.format(f.getTypeC2())
    // + ", " + SQL.format(weight02) + ", " + SQL.format(lineInfoC1) + ", " +
    // SQL.format(lineInfo2C1) + ", "
    // + SQL.format(lineInfoC2) + ", " + SQL.format(lineInfo2C2) + ", " +
    // SQL.format(icNoOrPassportNo) + ", "
    // + SQL.format(f.getSCUName()) + ", " + SQL.format(f.getTruckPlateNo()) + ", " +
    // SQL.format(f.getGateInNo())
    // + ", " + SQL.format("ACTV") + ", " + SQL.format(customerIpAddress) + ", " +
    // SQL.format(sealC1) + ", "
    // + SQL.format(sealC2) + ")";
    //
    // SCSSDatabase db = null;
    // try {
    // db = SCSSDatabase.getInstance();
    // db.executeUpdate(sql);
    // f.setPrintEIRNo(scssPrintEirIdSeq);
    // } catch (Exception e) {
    // String stackTrace = ExceptionUtil.getExceptionStacktrace(e);
    // log.error(stackTrace);
    // }
  }


}
