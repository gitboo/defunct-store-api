package defunct.store.config;

import org.apache.catalina.connector.Connector;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.ProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddedTomcatConfig {

	private static final Logger logger = LoggerFactory.getLogger(EmbeddedTomcatConfig.class);

	@Autowired
	private EmbeddedTomcatProperties properties; 
	
	private TomcatConnectorCustomizer customizeHttpConnector() {
    	logger.info("The default tomcat http connector ({})", properties.getHttp());

    	return ( ( connector ) -> {
			ProtocolHandler handler = connector.getProtocolHandler();
			
			if ( handler instanceof AbstractProtocol ) {
				String maxThreads = properties.getHttp().get("maxThreads");
				if ( StringUtils.isNotBlank(maxThreads) ) {
					((AbstractProtocol<?>) handler).setMaxThreads(NumberUtils.toInt(maxThreads, 200));
				}
				properties.getHttp().remove("maxThreads");
				
				String connectionTimeout = properties.getHttp().get("connectionTimeout");
				if ( StringUtils.isNotBlank(connectionTimeout) ) {
					((AbstractProtocol<?>) handler).setConnectionTimeout(NumberUtils.toInt(connectionTimeout, 0));
				}
				properties.getHttp().remove("connectionTimeout");

				String acceptCount = properties.getHttp().get("acceptCount");
				if ( StringUtils.isNotBlank(acceptCount) ) {
					((AbstractProtocol<?>) handler).setAcceptorThreadCount(NumberUtils.toInt(acceptCount, 1));
				}
				properties.getHttp().remove("acceptCount");

				String maxConnections = properties.getHttp().get("maxConnections");
				if ( StringUtils.isNotBlank(maxConnections) ) {
					((AbstractProtocol<?>) handler).setMaxConnections(NumberUtils.toInt(maxConnections, 10000));
				}
				properties.getHttp().remove("maxConnections");
			}
			
            if ( MapUtils.isNotEmpty(properties.getHttp()) ) {
            	for ( String key : properties.getHttp().keySet() ) {
            		connector.setProperty(key, properties.getHttp().get(key));
            	}        	
            }    		
    	});  	
	}
	
	private Connector createAjpConnector() {
		Connector connector = new Connector("AJP/1.3");
		
		logger.info("The default tomcat ajp connector ({})", properties.getAjp());
		
		if ( MapUtils.isNotEmpty(properties.getAjp()) ) {
			String proto = properties.getAjp().get("protocol");
			if ( StringUtils.isNotBlank(proto) ) {
				connector = new Connector(StringUtils.trim(proto));
				properties.getAjp().remove("protocol");
			}
			
			String port = properties.getAjp().get("port");
			connector.setPort(NumberUtils.toInt(port, 0));
			properties.getAjp().remove("port");
			
			String encoding = properties.getAjp().get("uri-encoding");
			if ( StringUtils.isNotBlank(encoding) ) {
				connector.setURIEncoding(StringUtils.trim(encoding));
			}
			properties.getAjp().remove("uri-encoding");

			String asyncTimeout = properties.getAjp().get("asyncTimeout");
			if ( StringUtils.isNotBlank(asyncTimeout) ) {
				connector.setAsyncTimeout(NumberUtils.toInt(asyncTimeout, 30000));
			}
			properties.getAjp().remove("asyncTimeout");

			ProtocolHandler handler = connector.getProtocolHandler();
			if ( handler instanceof AbstractProtocol ) {
				
				String maxThreads = properties.getAjp().get("maxThreads");
				if ( StringUtils.isNotBlank(maxThreads) ) {
					((AbstractProtocol<?>) handler).setMaxThreads(NumberUtils.toInt(maxThreads, 200));
				}
				properties.getAjp().remove("maxThreads");
				
				
				String connectionTimeout = properties.getAjp().get("connectionTimeout");
				if ( StringUtils.isNotBlank(connectionTimeout) ) {
					((AbstractProtocol<?>) handler).setConnectionTimeout(NumberUtils.toInt(connectionTimeout, 0));
				}
				properties.getAjp().remove("connectionTimeout");
				

				String acceptCount = properties.getAjp().get("acceptCount");
				if ( StringUtils.isNotBlank(acceptCount) ) {
					((AbstractProtocol<?>) handler).setAcceptorThreadCount(NumberUtils.toInt(acceptCount, 1));
				}
				properties.getAjp().remove("acceptCount");
				
				

				String maxConnections = properties.getAjp().get("maxConnections");
				if ( StringUtils.isNotBlank(maxConnections) ) {
					((AbstractProtocol<?>) handler).setMaxConnections(NumberUtils.toInt(maxConnections, 10000));
				}
				properties.getAjp().remove("maxConnections");
			}
		}
		
    	connector.setScheme("http");
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        connector.setAllowTrace(false);
        
        if ( MapUtils.isNotEmpty(properties.getAjp()) ) {
        	for ( String key : properties.getAjp().keySet() ) {
        		connector.setProperty(key, properties.getAjp().get(key));
        	}
        }
        return connector;
	}

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
	    tomcat.addAdditionalTomcatConnectors(createAjpConnector());
	    return tomcat;
	}
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return ( ( factory ) -> {
			 if ( factory instanceof TomcatEmbeddedServletContainerFactory ) {
				 TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) factory;
				 tomcat.addConnectorCustomizers(customizeHttpConnector());
			 }			
		});
	}
}
