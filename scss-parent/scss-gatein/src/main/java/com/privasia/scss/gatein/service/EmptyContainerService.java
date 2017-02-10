/**
 * 
 */
package com.privasia.scss.gatein.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.ISOCode;

/**
 * @author Janaka
 *
 */
@Service("emptyContainerService")
public class EmptyContainerService {

  @Autowired
  private IsoCodeService isoCodeService;

  public void refreshWeightForEmptyContainer(ExportContainer c) throws ResultsNotFoundException {
    /**
     * Refresh the Weight For Empty ExportContainer
     */
    if ("E".equals(c.getContainer().getContainerFullOrEmpty())) {
      ISOCode isoCodeDto = isoCodeService.getIsoCodeTarWeight(c.getContainer().getContainerISOCode());

      // c.setNetWeight(isoCodeDto.getTareWeight());
      // c.setEmptyWeight(isoCodeDto.getTareWeight());
      c.setExpNetWeight(isoCodeDto.getTareWeight());
      c.setExpWeightBridge(isoCodeDto.getTareWeight());
    }

  }

  /**
   * Calculate the WeightBridge For Empty ExportContainer
   */
  public void calculateWeightBridgeForEmptyContainer(ExportContainer c1, ExportContainer c2) {
    /*
     * if (StringUtils.isNotBlank(c1.getContainer().getContainerNumber()) &&
     * StringUtils.isNotBlank(c2.getContainer().getContainerNumber())) { if
     * ("E".equals(c1.getContainer().getContainerFullOrEmpty()) &&
     * "E".equals(c2.getContainer().getContainerFullOrEmpty())) {
     * 
     * c1.setNetWeight(c1.getEmptyWeight()); c2.setNetWeight(c2.getEmptyWeight());
     * 
     * double totalWeightBridge = new Double(c1.getEmptyWeight()) + new Double(c2.getEmptyWeight());
     * c1.setTotalWeightBridge(totalWeightBridge); c2.setTotalWeightBridge(totalWeightBridge);
     * 
     * } else if (StringUtils.isNotBlank(c1.getContainer().getContainerNumber())) {
     * c1.setNetWeight(c1.getEmptyWeight()); c1.setTotalWeightBridge(c1.getEmptyWeight());
     * 
     * } else if (StringUtils.isNotBlank(c2.getContainer().getContainerNumber())) {
     * c2.setNetWeight(c2.getEmptyWeight()); c2.setTotalWeightBridge(c2.getEmptyWeight());
     * 
     * }
     * 
     * }
     */
  }

}
