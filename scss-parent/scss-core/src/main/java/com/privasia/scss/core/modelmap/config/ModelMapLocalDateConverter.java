/**
 * 
 */
package com.privasia.scss.core.modelmap.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.AbstractProvider;
import org.modelmapper.Converter;
import org.modelmapper.Provider;
import org.modelmapper.spi.MappingContext;

import com.privasia.scss.common.util.CommonUtil;

/**
 * @author Janaka
 *
 */

public final class ModelMapLocalDateConverter {

	public static Converter<LocalDateTime, String> convertLocalDateTimeToString() {

		return new Converter<LocalDateTime, String>() {
			@Override
			public String convert(MappingContext<LocalDateTime, String> context) {
				if (context.getSource() != null) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonUtil.GLOBAL_DATE_PATTERN);
					return context.getSource().format(formatter);
				} else {
					return null;
				}
			}
		};

	}
	
	public static Converter<String, LocalDateTime> convertStringToLocalDateTime() {

		return new Converter<String, LocalDateTime>() {
			@Override
			public LocalDateTime convert(MappingContext<String, LocalDateTime> context) {
				if (StringUtils.isNotBlank(context.getSource())) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonUtil.GLOBAL_DATE_PATTERN);
					LocalDateTime localDateTime = LocalDateTime.parse(context.getSource() , formatter);
					return localDateTime;
				} else {
					return null;
				}
			}
		};

	}
	
	public static Provider<LocalDateTime> getLocalDateTimeProvider() {

		return  new AbstractProvider<LocalDateTime>() {
	        @Override
	        public LocalDateTime get() {
	            return LocalDateTime.now();
	        }
	    };

	}

}
