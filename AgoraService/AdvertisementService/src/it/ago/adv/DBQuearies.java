package it.ago.adv;

public class DBQuearies
{
	public static String Q_SEARCH_ADV_BY_ID = "SELECT * FROM AGO_ADVERTISEMENT WHERE ADV_ID  = ?";
	public static String Q_SEARCH_ADV_BY_OWNER_ID = "SELECT * FROM AGO_ADVERTISEMENT WHERE OWNER_ID  = ?";
	public static String Q_SEARCH_VEHI_ADV_BY_ADV_ID= "SELECT * FROM VEHICLE_ADVERTISEMENT WHERE ADV_ID  = ?";

}
