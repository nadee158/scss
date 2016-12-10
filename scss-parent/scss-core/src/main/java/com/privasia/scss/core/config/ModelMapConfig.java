/**
 * 
 */
package com.privasia.scss.core.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.AbstractConverter;
import org.modelmapper.AbstractProvider;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.privasia.scss.common.dto.KioskBoothRightsDTO;
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.core.model.KioskBoothRights;
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
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		configEnums(modelMapper);
		configProperty(modelMapper);
		
		Provider<LocalDateTime> localDateProvider = new AbstractProvider<LocalDateTime>() {
	        @Override
	        public LocalDateTime get() {
	            return LocalDateTime.now();
	        }
		};
		
		Converter<String, LocalDateTime> toStringDate = new AbstractConverter<String, LocalDateTime>() {
	        @Override
	        protected LocalDateTime convert(String source) {
	        	if(StringUtils.isNotBlank(source)){
	        		DateTimeFormatter format = DateTimeFormatter.ofPattern(CommonUtil.GLOBAL_DATE_PATTERN);
		            LocalDateTime localDate = LocalDateTime.parse(source, format);
		            return localDate;
	        	}else{
	        		return null;
	        	}
	            
	        }
	    };


	    modelMapper.createTypeMap(String.class, LocalDateTime.class).setProvider(localDateProvider);
	    modelMapper.addConverter(toStringDate);
	    
		//configLocalDateTime(modelMapper);
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
		modelMapper.createTypeMap(String.class, LocalDateTime.class);
		modelMapper.addConverter(ModelMapLocalDateConverter.convertStringToLocalDateTime());
		//modelMapper.createTypeMap(LocalDateTime.class, String.class);
		//modelMapper.addConverter(ModelMapLocalDateConverter.convertLocalDateTimeToString());
		modelMapper.getTypeMap(String.class, LocalDateTime.class).setProvider(ModelMapLocalDateConverter.getLocalDateTimeProvider());
		
		return modelMapper;
	}
}
