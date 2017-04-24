package com.privasia.scss.core.modelmap.config;

import java.lang.reflect.ParameterizedType;
import java.util.Optional;

import org.modelmapper.internal.util.TypeResolver;
import org.modelmapper.internal.util.Types;
import org.modelmapper.spi.ConditionalConverter;
import org.modelmapper.spi.Mapping;
import org.modelmapper.spi.MappingContext;
import org.modelmapper.spi.PropertyInfo;
import org.modelmapper.spi.PropertyMapping;

public class ModelMapOptionalConverter implements ConditionalConverter<Optional, Optional> {

  public MatchResult match(Class<?> sourceType, Class<?> destinationType) {
    if (Optional.class.isAssignableFrom(destinationType)) {
      return MatchResult.FULL;
    } else {
      return MatchResult.NONE;
    }
  }

  private Class<?> getElementType(MappingContext<Optional, Optional> context) {
    Mapping mapping = context.getMapping();
    if (mapping instanceof PropertyMapping) {
      PropertyInfo destInfo = ((PropertyMapping) mapping).getLastDestinationProperty();
      Class<?> elementType = TypeResolver.resolveArgument(destInfo.getGenericType(), destInfo.getInitialType());
      return elementType == TypeResolver.Unknown.class ? Object.class : elementType;
    } else if (context.getGenericDestinationType() instanceof ParameterizedType) {
      return Types.rawTypeFor(((ParameterizedType) context.getGenericDestinationType()).getActualTypeArguments()[0]);
    }

    return Object.class;
  }

  public Optional<?> convert(MappingContext<Optional, Optional> context) {
    Class<?> optionalType = getElementType(context);
    Optional source = context.getSource();
    Object dest = null;
    if (source != null && source.isPresent()) {
      MappingContext<?, ?> optionalContext = context.create(source.get(), optionalType);
      dest = context.getMappingEngine().map(optionalContext);
    }

    return Optional.ofNullable(dest);
  }

}
