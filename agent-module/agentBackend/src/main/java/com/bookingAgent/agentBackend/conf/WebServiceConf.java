package com.bookingAgent.agentBackend.conf;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.KeyStoreCallbackHandler;
import org.springframework.ws.soap.security.wss4j2.support.CryptoFactoryBean;
import org.springframework.ws.transport.http.MessageDispatcherServlet;


@Configuration
public class WebServiceConf extends WsConfigurerAdapter {
	
	
	@Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.bookingAgent.agentBackend.ws");
        marshaller.setSupportDtd(false);
        marshaller.setProcessExternalEntities(false);
        return marshaller;
    }
/*	//Verzija SOAP protokola 1.2
	@Bean
	public SaajSoapMessageFactory messageFactory() {
	    SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory();
	    messageFactory.setSoapVersion(SoapVersion.SOAP_12);
	    return messageFactory;
	}*/
    @Bean
    public AgentClient getAgentClient(Jaxb2Marshaller marshaller) throws Exception {
        AgentClient client = new AgentClient();
        client.setDefaultUri("https://localhost:8443/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        System.out.println(client.getMessageFactory().toString());
        ClientInterceptor[] interceptors = new ClientInterceptor[]{securityInterceptor()};
        client.setInterceptors(interceptors);
        return client;
    }
    @Bean
    public Wss4jSecurityInterceptor securityInterceptor() throws Exception {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
        
        // Podesavamo sigurnosne akcije. 
        securityInterceptor.setSecurementActions("Timestamp Signature Encrypt");
       
        //Digitalno potpisivanje poruke 
        //Prosledjujemo alias sertifikata iz keystora
        securityInterceptor.setSecurementUsername("agent2018");
        securityInterceptor.setSecurementPassword("Ag3nt2oo18");
        //securityInterceptor.setSecurementUsername("885550");
        //securityInterceptor.setSecurementPassword("agent");
        securityInterceptor.setSecurementSignatureCrypto(getCryptoFactoryBean().getObject());
        
        //Enkripcija sadrzaja poruke (moze ceo Body ili neki odjedjeni deo)
        securityInterceptor.setSecurementEncryptionUser("booking2018-public");
        //securityInterceptor.setSecurementEncryptionUser("306454");
        securityInterceptor.setSecurementEncryptionCrypto(getCryptoFactoryBean().getObject());
        securityInterceptor.setSecurementEncryptionParts("{Content}{http://server.project2018.example.com/soap}getAccommodationRequest");
        
        // sign the response
        securityInterceptor.setValidationActions("Signature Encrypt");
        securityInterceptor.setValidationSignatureCrypto(getCryptoFactoryBean().getObject());
        securityInterceptor.setValidationDecryptionCrypto(getCryptoFactoryBean().getObject());
        securityInterceptor.setValidationCallbackHandler(securityCallbackHandler());
        
        //securityInterceptor.setSecurementActions("NoSecurity");
        //securityInterceptor.setValidationActions("NoSecurity");
        return securityInterceptor;
    }

    @Bean
    public CryptoFactoryBean getCryptoFactoryBean() throws IOException {
        CryptoFactoryBean cryptoFactoryBean = new CryptoFactoryBean();
        cryptoFactoryBean.setKeyStorePassword("Pa$$vv0rd");
        //cryptoFactoryBean.setKeyStorePassword("password");
        cryptoFactoryBean.setKeyStoreLocation(new ClassPathResource("agent.jks"));
        //cryptoFactoryBean.setKeyStoreLocation(new ClassPathResource("keystore.jks"));

        return cryptoFactoryBean;
    }
    
    @Bean
    public KeyStoreCallbackHandler securityCallbackHandler(){
    KeyStoreCallbackHandler callbackHandler = new KeyStoreCallbackHandler();
    callbackHandler.setPrivateKeyPassword("Ag3nt2oo18");
    //callbackHandler.setPrivateKeyPassword("agent");

    return callbackHandler;
    }
}
