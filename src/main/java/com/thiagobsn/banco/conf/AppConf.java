package com.thiagobsn.banco.conf;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConf {
	
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
	    
}
