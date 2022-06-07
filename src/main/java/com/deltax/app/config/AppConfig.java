package com.deltax.app.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.deltax.app.dao.SpotifyAppDao;
import com.deltax.app.dao.SpotifyAppDaoImpl;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.deltax")
@PropertySource(value = { "classpath:database.properties" })
public class AppConfig implements WebMvcConfigurer {
	@Autowired
	private Environment environment;

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource source = new DriverManagerDataSource();
		source.setDriverClassName(environment.getProperty("jdbc.driver"));
		source.setUrl(environment.getProperty("jdbc.url"));
		source.setUsername(environment.getProperty("jdbc.username"));
		source.setPassword(environment.getProperty("jdbc.password"));
		return source;
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource source) {
		JdbcTemplate template = new JdbcTemplate(dataSource());
		template.setResultsMapCaseInsensitive(true);
		return template;
	}

	@Bean
	public MultipartResolver multipartResolver() {
		return new CommonsMultipartResolver();
	}

	@Bean
	public SpotifyAppDao dao() {
		return new SpotifyAppDaoImpl();
	}
}
