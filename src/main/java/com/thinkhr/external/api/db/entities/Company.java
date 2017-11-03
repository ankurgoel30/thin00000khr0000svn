package com.thinkhr.external.api.db.entities;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "clients")
public class Company {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "clientID", nullable = false) 
	private Long clientId;
	
	@Column(name = "search_help",nullable = false) 
	private String searchHelp;
	
	@Column(name = "Client_Type",nullable = false) 
	private String clientType;
	
	@Column(name = "Client_Name",nullable = false) 
	private String clientName;
	
	@Column(name = "display_name",nullable = true) 
	private String displayName;
	
	@Column(name = "aspect",nullable = true) 
	private String aspect;
	
	@Column(name = "Broker",nullable = true) 
	private int broker;
	
	@Column(name = "Client_Phone",nullable = false) 
	private String clientPhone;
	
	@Column(name = "Website",nullable = false) 
	private String website;
	
	@Column(name = "Client_Since",nullable = false) 
	private Date clientSince;
	
	@Column(name = "tempID",nullable = false) 
	private String tempID;
	
	@Column(name = "Client_Status",nullable = false) 
	private int clientStatus;
	
	@Column(name = "enhanced_password",nullable = false) 
	private int enhancedPassword;
	
	@Column(name = "client_hours",nullable = false) 
	private String clientHours;
	
	@Column(name = "issuesBroker",nullable = true) 
	private int issuesBroker;
	
	@Column(name = "issuesClient",nullable = true) 
	private int issuesClient;
	
	@Column(name = "issue_frequency",nullable = false) 
	private int issueFrequency;
	
	@Column(name = "industry",nullable = true) 
	private String industry;
	
	@Column(name = "companySize",nullable = true) 
	private String companySize;
	
	@Column(name = "actualCompanySize",nullable = true) 
	private int actualCompanySize;
	
	@Column(name = "companyType",nullable = true) 
	private String companyType;
	
	@Column(name = "salesNotes",nullable = true) 
	private String salesNotes;
	
	@Column(name = "customClient",nullable = false) 
	private int customClient;
	
	@Column(name = "groupID",nullable = false) 
	private String groupID;
	
	@Column(name = "deactivationDate",nullable = false) 
	private Date deactivationDate;
	
	@Column(name = "deactivationID",nullable = true) 
	private int deactivationID;
	
	@Column(name = "login",nullable = false) 
	private int login;
	
	@Column(name = "producer",nullable = true) 
	private String producer;
	
	@Column(name = "specialDomain",nullable = true) 
	private String specialDomain;
	
	@Column(name = "addedBy",nullable = true) 
	private String addedBy;
	
	@Column(name = "channel",nullable = true) 
	private String channel;
	
	@Column(name = "directID",nullable = true) 
	private int directID;
	
	@Column(name = "resellerID",nullable = true) 
	private int resellerID;
	
	@Column(name = "parentID",nullable = true) 
	private int parentID;
	
	@Column(name = "familiesID",nullable = true) 
	private int familiesID;
	
	@Column(name = "referral",nullable = true) 
	private String referral;
	
	@Column(name = "tally",nullable = false) 
	private int tally;
	
	@Column(name = "optOut",nullable = false) 
	private int optOut;
	
	@Column(name = "optOutWelcome",nullable = false) 
	private int optOutWelcome;
	
	@Column(name = "newsletterID",nullable = false) 
	private int newsletterID;
	
	@Column(name = "newsletterPrivateLabel",nullable = false) 
	private int newsletterPrivateLabel;
	
	@Column(name = "officeLocation",nullable = true) 
	private String officeLocation;
	
	@Column(name = "partnerClientType",nullable = true) 
	private String partnerClientType;
	
	@Column(name = "marketID",nullable = true) 
	private int marketID;
	
	@Column(name = "marketCode",nullable = true) 
	private String marketCode;
	
	@Column(name = "suspended",nullable = true) 
	private Timestamp suspended;
	
	@Column(name = "marketingCampaign",nullable = true) 
	private String marketingCampaign;
	
	@Column(name = "marketingFree",nullable = true) 
	private int marketingFree;
	
	@Column(name = "avoidTerms",nullable = false) 
	private int avoidTerms;
	
	@Column(name = "custom1",nullable = true) 
	private String custom1;
	
	@Column(name = "custom2",nullable = true) 
	private String custom2;
	
	@Column(name = "custom3",nullable = true) 
	private String custom3;
	
	@Column(name = "custom4",nullable = true) 
	private String custom4;
	
	@Column(name = "custom5",nullable = true) 
	private String custom5;
	
	@Column(name = "customDate",nullable = true) 
	private Timestamp customDate;
	
	@Column(name = "noReporting",nullable = false) 
	private int noReporting;
	
	@Column(name = "noTerms",nullable = false) 
	private int noTerms;
	
	@Column(name = "expiryDate",nullable = true) 
	private Date expiryDate;
	
	@Column(name = "partnerAdmin",nullable = false) 
	private int partnerAdmin;
	
	@Column(name = "level",nullable = true) 
	private int level;
	
	@Column(name = "brainID",nullable = true) 
	private String brainID;
	
	@Column(name = "tokenID",nullable = true) 
	private String tokenID;
	
	@Column(name = "subscriptionID",nullable = true) 
	private String subscriptionID;
	
	@Column(name = "posters",nullable = false) 
	private int posters;
	
	@Column(name = "complyLinks",nullable = false) 
	private int complyLinks;
	
	@Column(name = "resources",nullable = false) 
	private int resources;
	
	@Column(name = "newLook",nullable = false) 
	private int newLook;
	
	@Column(name = "customStyle",nullable = false) 
	private int customStyle;
	
	@Column(name = "setup_fee",nullable = true) 
	private int setupFee;
	
	@Column(name = "customerSuccessManager",nullable = true) 
	private int customerSuccessManager;
	
	@Column(name = "trial",nullable = true) 
	private int trial;
	
	@Column(name = "upsellLearn",nullable = false) 
	private int upsellLearn;
	
	@Column(name = "sales_rep",nullable = true) 
	private String salesRep;
	
	@Column(name = "exported",nullable = false) 
	private int exported;
	
	@Column(name = "direct_landing",nullable = true) 
	private int directLanding;
	
	@Column(name = "revenue",nullable = true) 
	private String revenue;
	
	@Column(name = "workplaceUsers",nullable = true) 
	private int workplaceUsers;
	
	@Column(name = "temp_Client_Status",nullable = true) 
	private int tempClientStatus;
	
	@Column(name = "renewal_date",nullable = true) 
	private Date renewalDate;
	
	@Column(name = "re_manager",nullable = true) 
	private int reManager;
	
	@Column(name = "partner_manager",nullable = true) 
	private int partnerManager;
	
	@Column(name = "auto_welcome_email",nullable = false) 
	private int autoWelcomeEmail;
	
	@Column(name = "contact_assignments",nullable = false) 
	private int contactAssignments;
	
	@Column(name = "salesforceID",nullable = true) 
	private String salesforceID;
	
	@Column(name = "special_note",nullable = false) 
	private String specialNote;
	
	@Column(name = "sourceID",nullable = false) 
	private int sourceID;

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getSearchHelp() {
		return searchHelp;
	}

	public void setSearchHelp(String searchHelp) {
		this.searchHelp = searchHelp;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getAspect() {
		return aspect;
	}

	public void setAspect(String aspect) {
		this.aspect = aspect;
	}

	public int getBroker() {
		return broker;
	}

	public void setBroker(int broker) {
		this.broker = broker;
	}

	public String getClientPhone() {
		return clientPhone;
	}

	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Date getClientSince() {
		return clientSince;
	}

	public void setClientSince(Date clientSince) {
		this.clientSince = clientSince;
	}

	public String getTempID() {
		return tempID;
	}

	public void setTempID(String tempID) {
		this.tempID = tempID;
	}

	public int getClientStatus() {
		return clientStatus;
	}

	public void setClientStatus(int clientStatus) {
		this.clientStatus = clientStatus;
	}

	public int getEnhancedPassword() {
		return enhancedPassword;
	}

	public void setEnhancedPassword(int enhancedPassword) {
		this.enhancedPassword = enhancedPassword;
	}

	public String getClientHours() {
		return clientHours;
	}

	public void setClientHours(String clientHours) {
		this.clientHours = clientHours;
	}

	public int getIssuesBroker() {
		return issuesBroker;
	}

	public void setIssuesBroker(int issuesBroker) {
		this.issuesBroker = issuesBroker;
	}

	public int getIssuesClient() {
		return issuesClient;
	}

	public void setIssuesClient(int issuesClient) {
		this.issuesClient = issuesClient;
	}

	public int getIssueFrequency() {
		return issueFrequency;
	}

	public void setIssueFrequency(int issueFrequency) {
		this.issueFrequency = issueFrequency;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getCompanySize() {
		return companySize;
	}

	public void setCompanySize(String companySize) {
		this.companySize = companySize;
	}

	public int getActualCompanySize() {
		return actualCompanySize;
	}

	public void setActualCompanySize(int actualCompanySize) {
		this.actualCompanySize = actualCompanySize;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getSalesNotes() {
		return salesNotes;
	}

	public void setSalesNotes(String salesNotes) {
		this.salesNotes = salesNotes;
	}

	public int getCustomClient() {
		return customClient;
	}

	public void setCustomClient(int customClient) {
		this.customClient = customClient;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public Date getDeactivationDate() {
		return deactivationDate;
	}

	public void setDeactivationDate(Date deactivationDate) {
		this.deactivationDate = deactivationDate;
	}

	public int getDeactivationID() {
		return deactivationID;
	}

	public void setDeactivationID(int deactivationID) {
		this.deactivationID = deactivationID;
	}

	public int getLogin() {
		return login;
	}

	public void setLogin(int login) {
		this.login = login;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getSpecialDomain() {
		return specialDomain;
	}

	public void setSpecialDomain(String specialDomain) {
		this.specialDomain = specialDomain;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public int getDirectID() {
		return directID;
	}

	public void setDirectID(int directID) {
		this.directID = directID;
	}

	public int getResellerID() {
		return resellerID;
	}

	public void setResellerID(int resellerID) {
		this.resellerID = resellerID;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

	public int getFamiliesID() {
		return familiesID;
	}

	public void setFamiliesID(int familiesID) {
		this.familiesID = familiesID;
	}

	public String getReferral() {
		return referral;
	}

	public void setReferral(String referral) {
		this.referral = referral;
	}

	public int getTally() {
		return tally;
	}

	public void setTally(int tally) {
		this.tally = tally;
	}

	public int getOptOut() {
		return optOut;
	}

	public void setOptOut(int optOut) {
		this.optOut = optOut;
	}

	public int getOptOutWelcome() {
		return optOutWelcome;
	}

	public void setOptOutWelcome(int optOutWelcome) {
		this.optOutWelcome = optOutWelcome;
	}

	public int getNewsletterID() {
		return newsletterID;
	}

	public void setNewsletterID(int newsletterID) {
		this.newsletterID = newsletterID;
	}

	public int getNewsletterPrivateLabel() {
		return newsletterPrivateLabel;
	}

	public void setNewsletterPrivateLabel(int newsletterPrivateLabel) {
		this.newsletterPrivateLabel = newsletterPrivateLabel;
	}

	public String getOfficeLocation() {
		return officeLocation;
	}

	public void setOfficeLocation(String officeLocation) {
		this.officeLocation = officeLocation;
	}

	public String getPartnerClientType() {
		return partnerClientType;
	}

	public void setPartnerClientType(String partnerClientType) {
		this.partnerClientType = partnerClientType;
	}

	public int getMarketID() {
		return marketID;
	}

	public void setMarketID(int marketID) {
		this.marketID = marketID;
	}

	public String getMarketCode() {
		return marketCode;
	}

	public void setMarketCode(String marketCode) {
		this.marketCode = marketCode;
	}

	public Timestamp getSuspended() {
		return suspended;
	}

	public void setSuspended(Timestamp suspended) {
		this.suspended = suspended;
	}

	public String getMarketingCampaign() {
		return marketingCampaign;
	}

	public void setMarketingCampaign(String marketingCampaign) {
		this.marketingCampaign = marketingCampaign;
	}

	public int getMarketingFree() {
		return marketingFree;
	}

	public void setMarketingFree(int marketingFree) {
		this.marketingFree = marketingFree;
	}

	public int getAvoidTerms() {
		return avoidTerms;
	}

	public void setAvoidTerms(int avoidTerms) {
		this.avoidTerms = avoidTerms;
	}

	public String getCustom1() {
		return custom1;
	}

	public void setCustom1(String custom1) {
		this.custom1 = custom1;
	}

	public String getCustom2() {
		return custom2;
	}

	public void setCustom2(String custom2) {
		this.custom2 = custom2;
	}

	public String getCustom3() {
		return custom3;
	}

	public void setCustom3(String custom3) {
		this.custom3 = custom3;
	}

	public String getCustom4() {
		return custom4;
	}

	public void setCustom4(String custom4) {
		this.custom4 = custom4;
	}

	public String getCustom5() {
		return custom5;
	}

	public void setCustom5(String custom5) {
		this.custom5 = custom5;
	}

	public Timestamp getCustomDate() {
		return customDate;
	}

	public void setCustomDate(Timestamp customDate) {
		this.customDate = customDate;
	}

	public int getNoReporting() {
		return noReporting;
	}

	public void setNoReporting(int noReporting) {
		this.noReporting = noReporting;
	}

	public int getNoTerms() {
		return noTerms;
	}

	public void setNoTerms(int noTerms) {
		this.noTerms = noTerms;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getPartnerAdmin() {
		return partnerAdmin;
	}

	public void setPartnerAdmin(int partnerAdmin) {
		this.partnerAdmin = partnerAdmin;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getBrainID() {
		return brainID;
	}

	public void setBrainID(String brainID) {
		this.brainID = brainID;
	}

	public String getTokenID() {
		return tokenID;
	}

	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}

	public String getSubscriptionID() {
		return subscriptionID;
	}

	public void setSubscriptionID(String subscriptionID) {
		this.subscriptionID = subscriptionID;
	}

	public int getPosters() {
		return posters;
	}

	public void setPosters(int posters) {
		this.posters = posters;
	}

	public int getComplyLinks() {
		return complyLinks;
	}

	public void setComplyLinks(int complyLinks) {
		this.complyLinks = complyLinks;
	}

	public int getResources() {
		return resources;
	}

	public void setResources(int resources) {
		this.resources = resources;
	}

	public int getNewLook() {
		return newLook;
	}

	public void setNewLook(int newLook) {
		this.newLook = newLook;
	}

	public int getCustomStyle() {
		return customStyle;
	}

	public void setCustomStyle(int customStyle) {
		this.customStyle = customStyle;
	}

	public int getSetupFee() {
		return setupFee;
	}

	public void setSetupFee(int setupFee) {
		this.setupFee = setupFee;
	}

	public int getCustomerSuccessManager() {
		return customerSuccessManager;
	}

	public void setCustomerSuccessManager(int customerSuccessManager) {
		this.customerSuccessManager = customerSuccessManager;
	}

	public int getTrial() {
		return trial;
	}

	public void setTrial(int trial) {
		this.trial = trial;
	}

	public int getUpsellLearn() {
		return upsellLearn;
	}

	public void setUpsellLearn(int upsellLearn) {
		this.upsellLearn = upsellLearn;
	}

	public String getSalesRep() {
		return salesRep;
	}

	public void setSalesRep(String salesRep) {
		this.salesRep = salesRep;
	}

	public int getExported() {
		return exported;
	}

	public void setExported(int exported) {
		this.exported = exported;
	}

	public int getDirectLanding() {
		return directLanding;
	}

	public void setDirectLanding(int directLanding) {
		this.directLanding = directLanding;
	}

	public String getRevenue() {
		return revenue;
	}

	public void setRevenue(String revenue) {
		this.revenue = revenue;
	}

	public int getWorkplaceUsers() {
		return workplaceUsers;
	}

	public void setWorkplaceUsers(int workplaceUsers) {
		this.workplaceUsers = workplaceUsers;
	}

	public int getTempClientStatus() {
		return tempClientStatus;
	}

	public void setTempClientStatus(int tempClientStatus) {
		this.tempClientStatus = tempClientStatus;
	}

	public Date getRenewalDate() {
		return renewalDate;
	}

	public void setRenewalDate(Date renewalDate) {
		this.renewalDate = renewalDate;
	}

	public int getReManager() {
		return reManager;
	}

	public void setReManager(int reManager) {
		this.reManager = reManager;
	}

	public int getPartnerManager() {
		return partnerManager;
	}

	public void setPartnerManager(int partnerManager) {
		this.partnerManager = partnerManager;
	}

	public int getAutoWelcomeEmail() {
		return autoWelcomeEmail;
	}

	public void setAutoWelcomeEmail(int autoWelcomeEmail) {
		this.autoWelcomeEmail = autoWelcomeEmail;
	}

	public int getContactAssignments() {
		return contactAssignments;
	}

	public void setContactAssignments(int contactAssignments) {
		this.contactAssignments = contactAssignments;
	}

	public String getSalesforceID() {
		return salesforceID;
	}

	public void setSalesforceID(String salesforceID) {
		this.salesforceID = salesforceID;
	}

	public String getSpecialNote() {
		return specialNote;
	}

	public void setSpecialNote(String specialNote) {
		this.specialNote = specialNote;
	}

	public int getSourceID() {
		return sourceID;
	}

	public void setSourceID(int sourceID) {
		this.sourceID = sourceID;
	}
}
