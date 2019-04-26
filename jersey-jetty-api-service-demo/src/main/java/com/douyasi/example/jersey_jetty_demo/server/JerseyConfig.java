package com.douyasi.example.jersey_jetty_demo.server;


import org.glassfish.jersey.server.ResourceConfig;

import com.douyasi.example.jersey_jetty_demo.api.provider.MapperProvider;
import com.douyasi.example.jersey_jetty_demo.security.filter.AuthenticationFilter;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(MapperProvider.class);
        register(AuthenticationFilter.class);
    }
}