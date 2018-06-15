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

//306454.cer
// booking
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
	/*@Bean
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
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://server.project2018.example.com/soap");
        wsdl11Definition.setSchema(accSchema);
        return wsdl11Definition;
    }
	
	@Bean
    public KeyStoreCallbackHandler securityCallbackHandler(){
        KeyStoreCallbackHandler callbackHandler = new KeyStoreCallbackHandler();
        callbackHandler.setPrivateKeyPassword("password");
        return callbackHandler;
    }
	
	@Bean
	public Wss4jSecurityInterceptor securityInterceptor() throws Exception {
		Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();

        // validate incoming request
        //securityInterceptor.setValidationActions("Timestamp Signature Encrypt");
//		securityInterceptor.setValidationActions("NoSecurity");
//        securityInterceptor.setValidationSignatureCrypto(getCryptoFactoryBean().getObject());
//        securityInterceptor.setValidationDecryptionCrypto(getCryptoFactoryBean().getObject());
//        securityInterceptor.setValidationCallbackHandler(securityCallbackHandler());

        // encrypt the response
//        securityInterceptor.setSecurementEncryptionUser("client-public");
//        //content enkriptuje ceo sadrzaj odgovora
//        
//        securityInterceptor.setSecurementEncryptionParts("{Content}{http://server.project2018.example.com/soap}getAccommodationResponse");
//        securityInterceptor.setSecurementEncryptionCrypto(getCryptoFactoryBean().getObject());

        // sign the response
        securityInterceptor.setSecurementActions("Signature Encrypt");
        securityInterceptor.setSecurementUsername("root");
        securityInterceptor.setSecurementPassword("root");
        securityInterceptor.setSecurementSignatureCrypto(getCryptoFactoryBean().getObject());

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
        cryptoFactoryBean.setKeyStorePassword("password");
        cryptoFactoryBean.setKeyStoreLocation(new ClassPathResource("keystore.jks"));
        return cryptoFactoryBean;
	}
	@Bean
    public XsdSchema empSchema() {
        return new SimpleXsdSchema(new ClassPathResource("test.xsd"));
    }
}
