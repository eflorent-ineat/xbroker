package upyfx.xbroker;


import org.springframework.context.ConfigurableApplicationContext;
import upyfx.xbroker.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class XBrokerApplication {

	public static void main(String[] args) throws InterruptedException {
		final ConfigurableApplicationContext context = SpringApplication.run(XBrokerApplication.class);
	}

}
