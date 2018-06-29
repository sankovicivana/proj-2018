package com.example.project2018.server.config;

import java.io.IOException;
import java.util.List;

import javax.security.auth.callback.CallbackHandler;

import org.springframework.boot.web.servlet.ServletRegistrationBean;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.KeyStoreCallbackHandler;
import org.springframework.ws.soap.security.wss4j2.support.CryptoFactoryBean;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

//885550 agent
//306454 booking
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/ws/*");
	}
/*	//Verzija SOAP protokola 1.2
		@Bean
		public SaajSoapMessageFactory messageFactory() {
		    SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory();
		    messageFactory.setSoapVersion(SoapVersion.SOAP_12);
		    return messageFactory;
		}*/
	//name mora biti naziv scheme
	@Bean(name = "test")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema accSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("AccomodationPort");
        wsdl11Definition.setServiceName("AccomodationService");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://server.project2018.example.com/soap");
        wsdl11Definition.setSchema(accSchema);
        return wsdl11Definition;
    }
	
	@Bean
    public KeyStoreCallbackHandler securityCallbackHandler(){
        KeyStoreCallbackHandler callbackHandler = new KeyStoreCallbackHandler();
        callbackHandler.setPrivateKeyPassword("B00king2oo18");
        //callbackHandler.setPrivateKeyPassword("booking");
        return callbackHandler;
    }
	
	@Bean
	public Wss4jSecurityInterceptor securityInterceptor() throws Exception {
		Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();


		//Validacija dolazne poruke
        securityInterceptor.setValidationActions("Timestamp Signature Encrypt");
        securityInterceptor.setValidationSignatureCrypto(getCryptoFactoryBean().getObject());
        securityInterceptor.setValidationDecryptionCrypto(getCryptoFactoryBean().getObject());
        securityInterceptor.setValidationCallbackHandler(securityCallbackHandler());
        
        //Enkripcija celog odgovora
        securityInterceptor.setSecurementEncryptionUser("agent2018-public");
        //securityInterceptor.setSecurementEncryptionUser("885550");
        securityInterceptor.setSecurementEncryptionParts("{Content}{http://server.project2018.example.com/soap}getAccommodationResponse");
        //Enkripcija elementa
        //securityInterceptor.setSecurementEncryptionParts("{Element}{http://server.project2018.example.com/soap}name");
        securityInterceptor.setSecurementEncryptionCrypto(getCryptoFactoryBean().getObject());

        // Digitalno potpisivanje odgovora
        securityInterceptor.setSecurementActions("Signature Encrypt");
        securityInterceptor.setSecurementUsername("booking2018");
        securityInterceptor.setSecurementPassword("B00king2oo18");
        //securityInterceptor.setSecurementUsername("306454");
        //securityInterceptor.setSecurementPassword("booking");
        securityInterceptor.setSecurementSignatureCrypto(getCryptoFactoryBean().getObject());

        //securityInterceptor.setSecurementActions("NoSecurity");
        //securityInterceptor.setValidationActions("NoSecurity");
        return securityInterceptor;

	}
	@Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        try {
            interceptors.add(securityInterceptor());
        } catch (Exception e) {
            throw new RuntimeException("could not initialize security interceptor");
        }
    }
	@Bean
	public CryptoFactoryBean getCryptoFactoryBean() throws IOException {
        CryptoFactoryBean cryptoFactoryBean = new CryptoFactoryBean();
        cryptoFactoryBean.setKeyStorePassword("Password1");
        cryptoFactoryBean.setKeyStoreLocation(new ClassPathResource("server.jks"));
        //cryptoFactoryBean.setKeyStorePassword("password");
        //cryptoFactoryBean.setKeyStoreLocation(new ClassPathResource("keystore.jks"));
        return cryptoFactoryBean;
	}
	
	@Bean
    public XsdSchema empSchema() {
        return new SimpleXsdSchema(new ClassPathResource("test.xsd"));
    }
}
