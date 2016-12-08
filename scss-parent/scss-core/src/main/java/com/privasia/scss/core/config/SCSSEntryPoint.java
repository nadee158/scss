package com.privasia.scss.core.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;

import org.modelmapper.AbstractConverter;
import org.modelmapper.AbstractProvider;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.Provider;
import org.modelmapper.spi.MappingContext;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.ErrorPageFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.privasia.scss.common.dto.KioskBoothRightsDTO;
import com.privasia.scss.common.enums.ClientType;
import com.privasia.scss.common.enums.KioskLockStatus;
import com.privasia.scss.common.enums.Profiles;
import com.privasia.scss.common.enums.RecordStatus;
import com.privasia.scss.common.enums.TransactionType;
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.core.model.KioskBoothRights;
import com.privasia.scss.core.util.service.CurrentDateTimeService;

@Configuration
@Import({PersistenceContext.class})
public class SCSSEntryPoint {

  @Bean
  public ModelMapper modelMapper() {
	  ModelMapper  modelMapper = new ModelMapper();  
	  modelMapper.createTypeMap(KioskLockStatus.class, String.class).setConverter(new Converter<KioskLockStatus, String>() { 
			@Override
			public String convert(MappingContext<KioskLockStatus, String> context) {
				if(context.getSource() != null){
					switch (context.getSource()) { 
			          case ACTIVE: 
			            return KioskLockStatus.ACTIVE.getValue(); 
			          case LOCK: 
				            return KioskLockStatus.LOCK.getValue();  
			          case COMPLETE:
				            return KioskLockStatus.COMPLETE.getValue();
			          case RELEASED: 
				            return KioskLockStatus.RELEASED.getValue();  
			          default: 
			            return null; 
			        } 
				}else{
					return null; 
				}
			} 
     });   
	  
	  modelMapper.createTypeMap(TransactionType.class, String.class).setConverter(new Converter<TransactionType, String>() { 
			@Override
			public String convert(MappingContext<TransactionType, String> context) {
				if(context.getSource() != null){
					switch (context.getSource()) { 
			          case IMPORT: 
			            return TransactionType.IMPORT.getValue(); 
			          case EXPORT: 
				            return TransactionType.EXPORT.getValue();  
			          case IMPORT_EXPORT:
				            return TransactionType.IMPORT_EXPORT.getValue();
			          case ODD_EXPORT: 
				            return TransactionType.ODD_EXPORT.getValue(); 
			          case ODD_IMPORT: 
				            return TransactionType.ODD_IMPORT.getValue();
			          case ODD_IMPORT_EXPORT: 
				            return TransactionType.ODD_IMPORT_EXPORT.getValue();
			          default: 
			            return null; 
			        } 
				}else{
					return null;
				}
				
			} 
	  });
	  
	  modelMapper.createTypeMap(RecordStatus.class, String.class).setConverter(new Converter<RecordStatus, String>() { 
			@Override
			public String convert(MappingContext<RecordStatus, String> context) {
				if(context.getSource() != null){
					switch (context.getSource()) { 
			          case ACTIVE: 
			            return RecordStatus.ACTIVE.getValue(); 
			          case INACTIVE: 
			        	  return RecordStatus.INACTIVE.getValue(); 
			          default: 
			            return null; 
			        } 
				}else{
					return null; 
				}
			} 
      });  
	  
	  modelMapper.createTypeMap(ClientType.class, String.class).setConverter(new Converter<ClientType, String>() { 
			@Override
			public String convert(MappingContext<ClientType, String> context) {
				if(context.getSource() != null){
					switch (context.getSource()) { 
			          case GATE_IN: 
			            return ClientType.GATE_IN.getValue(); 
			          case CCC: 
			        	  return ClientType.CCC.getValue(); 
			          case GREEN_GATE: 
			        	  return ClientType.GREEN_GATE.getValue(); 
			          case OTHERS: 
			        	  return ClientType.OTHERS.getValue(); 
			          case SPV: 
			        	  return ClientType.SPV.getValue(); 
			          case SECOND_GBOOTH: 
			        	  return ClientType.SECOND_GBOOTH.getValue(); 
			          case GATE_OUT: 
			        	  return ClientType.GATE_OUT.getValue(); 
			          case SECOND_GKIOSK: 
			        	  return ClientType.SECOND_GKIOSK.getValue();
			          default: 
			            return null; 
			        } 
				}else{
					return null; 
				}
			} 
	  }); 
	  
	  
	  Provider<LocalDateTime> localDateProvider = new AbstractProvider<LocalDateTime>() {
	        @Override
	        public LocalDateTime get() {
	            return LocalDateTime.now();
	        }
	  };

	  Converter<String, LocalDateTime> toStringDate = new AbstractConverter<String, LocalDateTime>() {
	        @Override
	        protected LocalDateTime convert(String source) {
	            DateTimeFormatter format = DateTimeFormatter.ofPattern(CommonUtil.GLOBAL_DATE_PATTERN);
	            LocalDateTime localDate = LocalDateTime.parse(source, format);
	            return localDate;
	        }
	  };
	  
	  /*modelMapper.createTypeMap(LocalDateTime.class, String.class).setConverter(new Converter<LocalDateTime, String>() { 
			@Override
			public String convert(MappingContext<LocalDateTime, String> context) {
				return CommonUtil.getFormatteDate(LocalDateTime.now());
			} 
	  });  */ 
	  
	  PropertyMap<KioskBoothRights, KioskBoothRightsDTO> kioskBoothRightsPKmap = new PropertyMap<KioskBoothRights, KioskBoothRightsDTO>() {
		  protected void configure() {
			  map().setBoothClientID(source.getKioskBoothRightsID().getBooth().getClientID());
			  map().setKioskClientID(source.getKioskBoothRightsID().getKiosk().getClientID());
		  }
	  };
	  
	  modelMapper.addMappings(kioskBoothRightsPKmap);
	  modelMapper.createTypeMap(String.class, LocalDateTime.class);
	  modelMapper.addConverter(toStringDate);
	  modelMapper.getTypeMap(String.class, LocalDateTime.class).setProvider(localDateProvider);
    return modelMapper;
  }

  @Profile(Profiles.APPLICATION)
  @Bean
  CurrentDateTimeService getCurrentDateTimeService() {
    return new CurrentDateTimeService();
  }


  @Bean
  public Filter characterEncodingFilter() {
    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
    characterEncodingFilter.setEncoding("UTF-8");
    characterEncodingFilter.setForceEncoding(true);
    return characterEncodingFilter;
  }

  @Bean
  public ErrorPageFilter errorPageFilter() {
    return new ErrorPageFilter();
  }

  @Bean
  public FilterRegistrationBean disableSpringBootErrorFilter(ErrorPageFilter filter) {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(filter);
    filterRegistrationBean.setEnabled(false);
    return filterRegistrationBean;
  }

  @Bean
  public MultipartConfigElement multipartConfigElement() {
    MultipartConfigFactory factory = new MultipartConfigFactory();
    factory.setMaxFileSize("128000KB");
    factory.setMaxRequestSize("128000KB");
    return factory.createMultipartConfig();
  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }
}
