package com.thinkhr.external.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ajay Jain
 *
 */
@Data
@NoArgsConstructor
public class CompanyJSONModel {

	public String clientName;
	public String displayName; 
	public String phone;
	public String address; 
	public String address2;
	public String city; 
	public String state;
	public String zip; 
	public String industry;
	public String companySize;
	public String producer;
	public String custom1;
	public String custom2;
	public String custom3;
	public String custom4;
}
