package defunct.store.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "server.tomcat-connector-property")
public class EmbeddedTomcatProperties {

	private Map<String, String> http = new HashMap<String, String>();
	private Map<String, String> ajp = new HashMap<String, String>();

	public Map<String, String> getHttp() {
		return http;
	}

	public Map<String, String> getAjp() {
		return ajp;
	}
}
