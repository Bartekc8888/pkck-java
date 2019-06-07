package com.politechnika.pkckbinding;

import com.politechnika.pkckbinding.converter.StringToXmlGregorianCalendarConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@SpringBootApplication
public class PkckBindingApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(PkckBindingApplication.class, args);
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new StringToXmlGregorianCalendarConverter());
	}
}
