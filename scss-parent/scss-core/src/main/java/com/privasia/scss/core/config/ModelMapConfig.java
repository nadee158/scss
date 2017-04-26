/**
 * 
 */
package com.privasia.scss.core.config;

import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.privasia.scss.core.modelmap.config.ModelMapEnumConverter;
import com.privasia.scss.core.modelmap.config.ModelMapLocalDateConverter;
import com.privasia.scss.core.modelmap.config.ModelMapOptionalConverter;
import com.privasia.scss.core.modelmap.config.ModelMapPropertyMap;

/**
 * @author Janaka
 *
 */
@Configuration
public class ModelMapConfig {

  private ModelMapOptionalConverter modelMapOptionalConverter;

  @Autowired
  public void setModelMapOptionalConverter(ModelMapOptionalConverter modelMapOptionalConverter) {
    this.modelMapOptionalConverter = modelMapOptionalConverter;
  }

  @Bean
  public ModelMapper configModelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    configEnums(modelMapper);
    configLocalDateTime(modelMapper);
    configProperty(modelMapper);
    configOptionalMapper(modelMapper);
    return modelMapper;
  }

  public ModelMapper configEnums(ModelMapper modelMapper) {

    modelMapper.addConverter(ModelMapEnumConverter.convertKioskLockStatusToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertTransactionTypeToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertRecordStatusToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertClientTypeToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertKioskHLTCheckStatusToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToKioskHLTCheckStatus());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToKioskLockStatus());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToTransactionType());
    modelMapper.addConverter(ModelMapEnumConverter.convertContainerSizeToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToContainerSize());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToContainerFullEmptyType());
    modelMapper.addConverter(ModelMapEnumConverter.convertContainerFullEmptyTypeToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertImpExpFlagStatusToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToImpExpFlagStatus());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToGateInOutStatus());
    modelMapper.addConverter(ModelMapEnumConverter.convertGateInOutStatusToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToTransactionStatus());
    modelMapper.addConverter(ModelMapEnumConverter.convertTransactionStatusToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToGatePassStatus());
    modelMapper.addConverter(ModelMapEnumConverter.convertGatePassStatusToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToContainerPosition());
    modelMapper.addConverter(ModelMapEnumConverter.convertContainerPositionToString());

    modelMapper.addConverter(ModelMapEnumConverter.convertStringToExportOPTFlagType());
    modelMapper.addConverter(ModelMapEnumConverter.convertExportOPTFlagTypeToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToReferTempType());
    modelMapper.addConverter(ModelMapEnumConverter.convertReferTempTypeToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToVesselStatus());
    modelMapper.addConverter(ModelMapEnumConverter.convertVesselStatusToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToGCS_SSRBlockStatusType());
    modelMapper.addConverter(ModelMapEnumConverter.convertGCS_SSRBlockStatusTypeToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToHpatReferStatus());
    modelMapper.addConverter(ModelMapEnumConverter.convertHpatReferStatusToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToCardUsageStatus());
    modelMapper.addConverter(ModelMapEnumConverter.convertCardUsageStatusToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToSolasInstructionType());
    modelMapper.addConverter(ModelMapEnumConverter.convertSolasInstructionTypeToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToBookingType());
    modelMapper.addConverter(ModelMapEnumConverter.convertBookingTypeToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertHDBSStatusToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToHDBSStatus());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToHDBSArrivalStatus());
    modelMapper.addConverter(ModelMapEnumConverter.convertHDBSArrivalStatusToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertHDBSBookingTypeToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToHDBSBookingType());
    modelMapper.addConverter(ModelMapEnumConverter.convertSCSSHDBSStatusToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToSCSSHDBSStatus());
    modelMapper.addConverter(ModelMapEnumConverter.convertCardStatusToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToCardStatus());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToCompanyStatus());
    modelMapper.addConverter(ModelMapEnumConverter.convertCompanyStatusToString());

    modelMapper.addConverter(ModelMapEnumConverter.convertReadWriteStatusToString());
    modelMapper.addConverter(ModelMapEnumConverter.convertStringToReadWriteStatus());

    modelMapper.addConverter(ModelMapEnumConverter.convertIntegerToSolasWeightTypeSize());
    modelMapper.addConverter(ModelMapEnumConverter.convertSolasWeightTypeSizeToInteger());

    modelMapper.addConverter(ModelMapEnumConverter.convertStringToSolasWeightType());
    modelMapper.addConverter(ModelMapEnumConverter.convertSolasWeightTypeToString());

    return modelMapper;
  }

  public ModelMapper configProperty(ModelMapper modelMapper) {
    modelMapper.addMappings(ModelMapPropertyMap.kioskBoothRightsDomainToDto());
    modelMapper.addMappings(ModelMapPropertyMap.kioskBoothRightsDtoToDomain());
    modelMapper.addMappings(ModelMapPropertyMap.gatePassToImportContainer());
    modelMapper.addMappings(ModelMapPropertyMap.exportsToExportsQ());
    modelMapper.addMappings(ModelMapPropertyMap.importContainerToGatePass());
    modelMapper.addMappings(ModelMapPropertyMap.exportContainerToExports());
    modelMapper.addMappings(ModelMapPropertyMap.exportsToExportContainer());
    // modelMapper.addMappings(ModelMapPropertyMap.referRejectDTOToReferReject());
    //modelMapper.addMappings(ModelMapPropertyMap.referRejectDetailDTOToReferRejectDetail());
    modelMapper.addMappings(ModelMapPropertyMap.referRejectDetailToReferRejectDetailDTO());
    modelMapper.addMappings(ModelMapPropertyMap.referRejectToReferRejectDTO()); 

    return modelMapper;
  }

  public ModelMapper configLocalDateTime(ModelMapper modelMapper) {
    modelMapper.createTypeMap(String.class, LocalDateTime.class)
        .setProvider(ModelMapLocalDateConverter.getLocalDateTimeProvider());
    modelMapper.addConverter(ModelMapLocalDateConverter.convertStringToLocalDateTime());
    modelMapper.createTypeMap(LocalDateTime.class, String.class);
    modelMapper.addConverter(ModelMapLocalDateConverter.convertLocalDateTimeToString());
    return modelMapper;
  }

  public ModelMapper configOptionalMapper(ModelMapper modelMapper) {
    modelMapper.createTypeMap(Optional.class, Optional.class).setConverter(modelMapOptionalConverter);
    // modelMapper.addConverter(new ModelMapOptionalConverter());
    return modelMapper;
  }
}
