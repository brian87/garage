package com.apress.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class BaseDTO {
	
	@JsonIgnore
	private Collection<String> errors =  new ArrayList<String>();
	
	public void addError(String error) {
		errors.add(error);
	}
	
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	public String getErrors() {
		return StringUtils.join(errors, "|");
	}

}
