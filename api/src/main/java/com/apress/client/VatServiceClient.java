package com.apress.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import eu.europa.ec.taxud.vies.services.checkvat.types.CheckVat;
import eu.europa.ec.taxud.vies.services.checkvat.types.CheckVatResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Service
public class VatServiceClient {

	private WebServiceTemplate webServiceTemplate;

	@Autowired
	public VatServiceClient() {
		this.webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setDefaultUri("http://ec.europa.eu/taxation_customs/vies/services/checkVatTestService");
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan("eu.europa.ec.taxud.vies.services.checkvat.types");
		webServiceTemplate.setMarshaller(marshaller);
		webServiceTemplate.setUnmarshaller(marshaller);
		webServiceTemplate.setCheckConnectionForFault(false);
	}

	/**
	 * @param checkVat
	 * @return {@code null} in case Vies Service is unavailable
	 * Here is the list of VAT Number to use to receive each kind of answer : 
	 * 100 = Valid request with Valid VAT Number
	 * 200 = Valid request with an Invalid VAT Number
	 * 201 = Error : INVALID_INPUT
	 * 202 = Error : INVALID_REQUESTER_INFO
	 * 300 = Error : SERVICE_UNAVAILABLE
	 */
	public CheckVatResponse checkVat(CheckVat checkVat) {
		CheckVatResponse checkVatResponse = null;
		try {
			checkVatResponse = (CheckVatResponse) webServiceTemplate.marshalSendAndReceive(checkVat);
		}catch(SoapFaultClientException sfe) {
			log.warn("Vies Service fault", sfe.getMessage());
		}catch(RuntimeException e) {
			log.error("Vies Service error", e);
		}
		return checkVatResponse;
	}
}
