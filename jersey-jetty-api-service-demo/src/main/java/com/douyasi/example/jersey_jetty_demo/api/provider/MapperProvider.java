package com.douyasi.example.jersey_jetty_demo.api.provider;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

// import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
// import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;

@Provider
public class MapperProvider implements ContextResolver<ObjectMapper> {
    final ObjectMapper mapper;

    public MapperProvider() {
        mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(
                PropertyNamingStrategy.SNAKE_CASE);
        // mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    public ObjectMapper getContext(Class<?> cls) {
        return mapper;
    }
}
