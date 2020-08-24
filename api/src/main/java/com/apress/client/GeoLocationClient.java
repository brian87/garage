package com.apress.client;

import org.apache.ws.security.WSConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;

import garage.services.geolocation.types.GetLocationRequest;
import garage.services.geolocation.types.GetLocationResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Service
public class GeoLocationClient {

	@Value("${garage.geolocation.url}")
	private String geolocationUrl;

	private WebServiceTemplate webServiceTemplate;

	@Autowired
	public GeoLocationClient() {
		this.webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setDefaultUri(geolocationUrl);
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan("garage.services.geolocation.types");
		webServiceTemplate.setMarshaller(marshaller);
		webServiceTemplate.setUnmarshaller(marshaller);
		ClientInterceptor[] interceptors = new ClientInterceptor[] { getSecurityInterceptor() };
		webServiceTemplate.setInterceptors(interceptors);
	}

	public Wss4jSecurityInterceptor getSecurityInterceptor() {
		Wss4jSecurityInterceptor wss4jSecurityInterceptor = new Wss4jSecurityInterceptor();
		wss4jSecurityInterceptor.setSecurementActions("UsernameToken");
		wss4jSecurityInterceptor.setSecurementUsername("admin");
		wss4jSecurityInterceptor.setSecurementPassword("secret");
		wss4jSecurityInterceptor.setSecurementPasswordType(WSConstants.PW_TEXT);
		wss4jSecurityInterceptor.setSecurementMustUnderstand(false);
		return wss4jSecurityInterceptor;
	}

	public GetLocationResponse getLocation(GetLocationRequest getLocationRequest) {
		GetLocationResponse getLocationResponse = null;
		try {
			getLocationResponse = (GetLocationResponse) webServiceTemplate.marshalSendAndReceive(getLocationRequest);
		} catch (SoapFaultClientException sfe) {
			log.warn("Geolocation Service fault", sfe.getMessage());
		} catch (RuntimeException e) {
			log.error("Geolocation Service error", e);
		}
		return getLocationResponse;
	}
}
