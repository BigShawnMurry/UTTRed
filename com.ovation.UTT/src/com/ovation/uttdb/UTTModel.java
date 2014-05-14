package com.ovation.uttdb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ovation.utt.domain.UTT;
import com.ovation.utt.domain.UTTAbstractModel;

public class UTTModel extends UTTAbstractModel<UTT> {
  //Map<UTT,String>utt=new HashMap<UTT,String>(); 
	private static SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
	private final String query="EXEC usp_UTT_Java ?";  
	
  public List<UTT> getUTT()
  {
	  return getUTTByType(query);
  }
  
	public List<UTT> getUTTByType(String t) {
	// TODO Auto-generated method stub
		
	return getObjects(t);
}
	public UTT get()
	{
		List<UTT> ut = getObjects(query);
		return ut.iterator().next();
	}

	@Override
	protected UTT map(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		
		UTT u= new UTT();
		u.setDepartmentCode(rs.getString("NameStmtInfo"));
		u.setAirline(rs.getString("Airline"));
		u.setBAR(rs.getString("BAR"));
		u.setCityPair(rs.getString("CityPair"));
		u.setCustID(rs.getString("CustomerID"));
		u.setFareBasisCode(rs.getString("FareBasisCode"));
		u.seticketStatus(rs.getString("TicketStatus"));
		u.setPAR(rs.getString("PAR"));
		u.setPCC(rs.getString("PCC"));
		u.setRecordLocator(rs.getString("PNR"));
		u.setrefundable(rs.getString("Refundable"));
		u.setTotalAirFareValue(rs.getDouble("TotalAirfare"));
		u.setTraveler(rs.getString("ClientName"));
		try{
			String strdate=rs.getString("ExpireDate");
			Date date= f.parse(strdate);
			u.setExpirationDate(date);
			strdate=rs.getString("IssueDate");
			date=f.parse(strdate);
			u.setIssueDate(date);
		}catch(Exception e)
		{
			e.getStackTrace();
		}
		return u;
	}

}
