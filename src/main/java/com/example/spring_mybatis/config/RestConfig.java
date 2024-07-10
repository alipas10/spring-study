package com.example.spring_mybatis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.example.spring_mybatis.projection.OrderProjection;
import com.example.spring_mybatis.validator.OrderValidator;

@Configuration
public class RestConfig implements RepositoryRestConfigurer {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		// if want to expose serialize of all Etity
//		Class[] classes = entityManager.getMetamodel()
//		          .getEntities().stream().map(Type::getJavaType).toArray(Class[]::new);
//		        config.exposeIdsFor(classes);
		
		// add default conjection class
		config.getProjectionConfiguration().addProjection(OrderProjection.class);
//			config.exposeIdsFor(OrderDTO.class); // expose serialize properties of Entity
	}

	@Override
	public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
		validatingListener.addValidator("beforeCreate", new OrderValidator());
	}

	
	
		
}
