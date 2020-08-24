package com.apress.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.dataaccess.webservicesserver.NumberToDollars;
import com.dataaccess.webservicesserver.NumberToDollarsResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j

@Service
public class NumberConversionClient {

	private WebServiceTemplate webServiceTemplate;

	@Autowired
	public NumberConversionClient() {
		this.webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setDefaultUri("https://www.dataaccess.com/webservicesserver/numberconversion.wso");
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan("com.dataaccess.webservicesserver");
		webServiceTemplate.setMarshaller(marshaller);
		webServiceTemplate.setUnmarshaller(marshaller);
	}

	public NumberToDollarsResponse numberToWords(NumberToDollars numberToDollars) {
		NumberToDollarsResponse numberToDollarsResponse = null;
		try {
			numberToDollarsResponse = (NumberToDollarsResponse) webServiceTemplate
					.marshalSendAndReceive(numberToDollars);
		} catch (SoapFaultClientException sfe) {
			log.warn("NumberConversion Service fault", sfe.getMessage());
		} catch (RuntimeException e) {
			log.error("NumberConversion Service error", e);
		}
		return numberToDollarsResponse;
	}
}
