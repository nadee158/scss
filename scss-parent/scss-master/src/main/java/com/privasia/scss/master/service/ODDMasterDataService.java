/**
 * 
 */
package com.privasia.scss.master.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.enums.RecordStatus;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.ODDExportReason;
import com.privasia.scss.core.model.ODDImportReason;
import com.privasia.scss.core.model.ODDLocation;
import com.privasia.scss.core.repository.ODDExportReasonRepository;
import com.privasia.scss.core.repository.ODDImportReasonRepository;
import com.privasia.scss.core.repository.ODDLocationRepository;

/**
 * @author Janaka
 *
 */
@Service("oddMasterDataService")
public class ODDMasterDataService {

  @Autowired
  private ODDImportReasonRepository oddImportReasonRepository;

  @Autowired
  private ODDExportReasonRepository oddExportReasonRepository;

  @Autowired
  private ODDLocationRepository oddLocationRepository;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<ODDImportReason> findAllImportReason() throws ResultsNotFoundException {

    Stream<ODDImportReason> oddImportReason = oddImportReasonRepository.findAll();

    if (oddImportReason != null) {

      return oddImportReason.collect(Collectors.toList());

    } else {
      throw new ResultsNotFoundException("No ODD Import Reasons were found!");
    }
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<ODDExportReason> findAllExporteason() throws ResultsNotFoundException {

    Stream<ODDExportReason> oddExportReason = oddExportReasonRepository.findAll();

    if (oddExportReason != null) {

      return oddExportReason.collect(Collectors.toList());

    } else {
      throw new ResultsNotFoundException("No ODD Export Reasons were found!");
    }
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<ODDLocation> findActiveODDLocation() throws ResultsNotFoundException {

    RecordStatus recordStatus = RecordStatus.ACTIVE;

    Stream<ODDLocation> activeLocations = oddLocationRepository.findByStatusCodeOrderByOddCodeAsc(recordStatus);

    if (activeLocations != null) {

      return activeLocations.collect(Collectors.toList());

    } else {
      throw new ResultsNotFoundException("No ODD Locations were found!");
    }
  }

}
