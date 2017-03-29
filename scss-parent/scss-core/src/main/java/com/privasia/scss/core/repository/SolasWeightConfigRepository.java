package com.privasia.scss.core.repository;

import java.util.List;
import java.util.Optional;

import com.privasia.scss.common.enums.SolasWeightType;
import com.privasia.scss.common.enums.SolasWeightTypeSize;
import com.privasia.scss.core.model.SolasWeightConfig;

/**
 * @author Janaka
 *
 */

public interface SolasWeightConfigRepository extends BaseRepository<SolasWeightConfig, Long> {

  Optional<List<SolasWeightConfig>> findByWeightType(SolasWeightType weightType);

  Optional<List<SolasWeightConfig>> findByWeightTypeSize(SolasWeightTypeSize weightTypeSize);

  Optional<List<SolasWeightConfig>> findByWeightTypeAndWeightTypeSize(SolasWeightType weightType,
      SolasWeightTypeSize weightTypeSize);


}
