package com.ovation.utt.domain;

import java.util.Date;

public class UTT {
private String TravelerName;
private double TotalAirFareValue;
private String TicketNumber;

private String CustID;
private String DepartmentCode;
private String PCC;
private String Airline;
private String refundable;
private String RecordLocator;
private String ticketStatus;

private String FareBasisCode;
private String PAR;
private String BAR;
private String CityPair;
private Date IssueDate;
private Date ExpirationDate;


public void setTraveler(String TravelerName)
{
	this.TravelerName = TravelerName;
}
public String getTraveler()
{
	return TravelerName;
}
public void setTotalAirFareValue(double TotalAirFareValue)
{
	this.TotalAirFareValue = TotalAirFareValue;
}
public double getTotalAirFareValue()
{
	return TotalAirFareValue;
}
public void setCustID(String CustID)
{
	this.CustID = CustID;
}
public String getCustID()
{
	return CustID;	
}
public void setDepartmentCode(String DepartmentCode)
{
	this.DepartmentCode= DepartmentCode;
}
public String getDepartmentCode()
{
	return DepartmentCode;
}
public void setTicketNumber(String TicketNumber)
{
	this.TicketNumber=TicketNumber;
	}
public String getTicketNumber()
{
	return TicketNumber;
}
public void setPCC(String PCC)
{
	this.PCC=PCC;	
}
public String getPCC()
{
	return PCC;	
}
public void setAirline(String Airline)
{
	this.Airline=Airline;	
}
public String getAirline()
{
	return Airline;
	}
public void setrefundable(String refundable)
{
	this.refundable=refundable;
}
public String getrefundable()
{
	return refundable;	
}
public void setRecordLocator(String RecordLocator)
{
	this.RecordLocator=RecordLocator;
}
public String getRecordLocator()
{
	return RecordLocator;
}
public void seticketStatus(String ticketStatus)
{
	this.ticketStatus=ticketStatus;	
}
public String getticketStatus()
{
	return ticketStatus;	
}
public void setFareBasisCode(String FareBasisCode)
{
	this.FareBasisCode=FareBasisCode;	
}
public String getFareBasisCode()
{
	return FareBasisCode;	
}
public void setPAR(String PAR)
{
	this.PAR=PAR;	
}
public String getPAR()
{
	return PAR;	
}
public void setBAR(String BAR)
{
	this.BAR=BAR;	
}
public String getBAR()
{
	return BAR;	
}
public void setCityPair(String CityPair)
{
	this.CityPair=CityPair;	
}
public String getCityPair()
{
	return CityPair;
}
public void setIssueDate(Date IssueDate)
{
	this.IssueDate=IssueDate;	
}
public Date getIssueDate()
{
	return IssueDate;
}
public void setExpirationDate(Date ExpirationDate)
{
	this.ExpirationDate=ExpirationDate;
}
public Date getExpirationDate()
{
	return ExpirationDate;
}
}

