package upyfx.xbroker.config;

import upyfx.xbroker.security.OAuth1AMQP;
import org.apache.activemq.artemis.core.config.impl.SecurityConfiguration;
import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;
import org.apache.activemq.artemis.spi.core.security.ActiveMQJAASSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@EnableJms
public class AMQPConfig {

    @Bean
    public ActiveMQJAASSecurityManager securityManager() {
        final SecurityConfiguration configuration = new SecurityConfiguration();
        final ActiveMQJAASSecurityManager securityManager =
                new ActiveMQJAASSecurityManager(OAuth1AMQP.class.getName(), configuration);
        return securityManager;
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public EmbeddedActiveMQ embeddedActiveMQ(ActiveMQJAASSecurityManager securityManager) {
        final EmbeddedActiveMQ embeddedActiveMQ = new EmbeddedActiveMQ();
        embeddedActiveMQ.setSecurityManager(securityManager);
        return embeddedActiveMQ;
    }


}