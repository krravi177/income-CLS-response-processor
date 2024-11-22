package com.xpanse.los.income.cls.response.processor.model.respone;

import lombok.Data;

import java.util.List;

@Data
public class Extensions {
	private int eventCode;
	private String eventDescription;
	private String extPartnerId;
	private String extPartnerName;
	private OrderIdentifier orderIdentifier;
	private String originReportId;
	private String originSourceId;
	private String currencyCode;
	private int positionTenure;
	private String positionTitle;
	private String positionEndDate;
	private String employerDisclaimers;
	private EmploymentStatus employmentStatus;
	private String originalHireDate;
	private String mostRecentHireDate;
	private List<PaymentHistory> paymentHistory;
	private List<TotalAnnualRemuneration> totalAnnualRemuneration;
}
