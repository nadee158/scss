/**
 * 
 */
package com.privasia.scss.core.config;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.privasia.scss.core.modelmap.config.ModelMapEnumConverter;
import com.privasia.scss.core.modelmap.config.ModelMapLocalDateConverter;
import com.privasia.scss.core.modelmap.config.ModelMapPropertyMap;

/**
 * @author Janaka
 *
 */
@Configuration
public class ModelMapConfig {
	
	@Bean
	public ModelMapper configModelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		configEnums(modelMapper);
		configLocalDateTime(modelMapper);
		configProperty(modelMapper);
		return modelMapper;
	}

	public ModelMapper configEnums(ModelMapper modelMapper) {

		modelMapper.addConverter(ModelMapEnumConverter.convertKioskLockStatusToString());
		modelMapper.addConverter(ModelMapEnumConverter.convertTransactionTypeToString());
		modelMapper.addConverter(ModelMapEnumConverter.convertRecordStatusToString());
		modelMapper.addConverter(ModelMapEnumConverter.convertClientTypeToString());
		modelMapper.addConverter(ModelMapEnumConverter.convertStringToKioskHLTCheckStatus());
		modelMapper.addConverter(ModelMapEnumConverter.convertStringToKioskLockStatus());
		modelMapper.addConverter(ModelMapEnumConverter.convertStringToTransactionType());
		modelMapper.addConverter(ModelMapEnumConverter.convertContainerSizeToString());
		modelMapper.addConverter(ModelMapEnumConverter.convertStringToContainerSize());
		modelMapper.addConverter(ModelMapEnumConverter.convertStringToContainerFullEmptyType());
		modelMapper.addConverter(ModelMapEnumConverter.convertContainerFullEmptyTypeToString());
		
		return modelMapper;
	}
	
	public ModelMapper configProperty(ModelMapper modelMapper) {

		modelMapper.addMappings(ModelMapPropertyMap.kioskBoothRightsDomainToDto());
		modelMapper.addMappings(ModelMapPropertyMap.kioskBoothRightsDtoToDomain());
		return modelMapper;
	}
	
	public ModelMapper configLocalDateTime(ModelMapper modelMapper) {
		modelMapper.createTypeMap(String.class, LocalDateTime.class).setProvider(ModelMapLocalDateConverter.getLocalDateTimeProvider());
		modelMapper.addConverter(ModelMapLocalDateConverter.convertStringToLocalDateTime());
		modelMapper.createTypeMap(LocalDateTime.class, String.class);
		modelMapper.addConverter(ModelMapLocalDateConverter.convertLocalDateTimeToString());
		return modelMapper;
	}
}
