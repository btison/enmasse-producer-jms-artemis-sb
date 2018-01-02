package com.redhat.btison.enmasse.producer.jms.sb;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@Configuration
@ConfigurationProperties(prefix = "spring.artemis")
public class ApplicationConfiguration {

    private int sessionCacheSize;

    private String url;

    private String user;

    private String password;

    @Bean
    public JacksonJsonProvider jsonProvider(ObjectMapper objectMapper) {
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        provider.setMapper(objectMapper);
        return provider;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory cf = new ActiveMQConnectionFactory(url, user, password);
        CachingConnectionFactory ccf = new CachingConnectionFactory(cf);
        ccf.setSessionCacheSize(sessionCacheSize);
        return ccf;
    }

    @Bean
    JmsTemplate jmsTemplate(ConnectionFactory cf) throws JMSException {
        JmsTemplate jmsTemplate = new JmsTemplate(cf);
        jmsTemplate.setExplicitQosEnabled(true);
        return jmsTemplate;

    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSessionCacheSize(int sessionCacheSize) {
        this.sessionCacheSize = sessionCacheSize;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
