/**
 * 
 */
package com.privasia.scss.gatein.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.core.dto.ExportContainer;
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
    if ("E".equals(c.getFullOrEmpty())) {
      ISOCode isoCodeDto = isoCodeService.getIsoCodeTarWeight(c.getContainerISO());

      c.setNetWeight(isoCodeDto.getTareWeight());
      c.setEmptyWeight(isoCodeDto.getTareWeight());
    }

  }

  /**
   * Calculate the WeightBridge For Empty ExportContainer
   */
  public void calculateWeightBridgeForEmptyContainer(ExportContainer c1, ExportContainer c2) {

    if (StringUtils.isNotBlank(c1.getContainerNumber()) && StringUtils.isNotBlank(c2.getContainerNumber())) {
      if ("E".equals(c1.getFullOrEmpty()) && "E".equals(c2.getFullOrEmpty())) {
        c1.setNetWeight(c1.getEmptyWeight());
        c2.setNetWeight(c2.getEmptyWeight());

        double totalWeightBridge = new Double(c1.getEmptyWeight()) + new Double(c2.getEmptyWeight());
        c1.setTotalWeightBridge(totalWeightBridge);
        c2.setTotalWeightBridge(totalWeightBridge);

      } else if (StringUtils.isNotBlank(c1.getContainerNumber())) {
        c1.setNetWeight(c1.getEmptyWeight());
        c1.setTotalWeightBridge(c1.getEmptyWeight());

      } else if (StringUtils.isNotBlank(c2.getContainerNumber())) {
        c2.setNetWeight(c2.getEmptyWeight());
        c2.setTotalWeightBridge(c2.getEmptyWeight());

      }
    }
  }

}
