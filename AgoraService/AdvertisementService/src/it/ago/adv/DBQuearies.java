package it.ago.adv;

public class DBQuearies
{
	public static String Q_SEARCH_ADV_BY_ID = "SELECT * FROM AGO_ADVERTISEMENT WHERE ADV_ID  = ?";
	public static String Q_SEARCH_ADV_BY_OWNER_ID = "SELECT * FROM AGO_ADVERTISEMENT WHERE OWNER_ID  = ?";
	public static String Q_SEARCH_VEHI_ADV_BY_ADV_ID= "SELECT * FROM VEHICLE_ADVERTISEMENT WHERE ADV_ID  = ?";
	public static String Q_UNIVERSIAL_ADV_SEARCH = "SELECT * "
			+ "FROM ago_advertisement AA "
			+ "INNER JOIN VEHICLE_ADVERTISEMENT VA "
			+ "ON VA.ADV_ID            = AA.ADV_ID "
			+ "WHERE AA.ADV_ID         = ? "
			+ "AND AA.VALID_FROM       = ? "
			+ "AND AA.VALID_TO         = ? "
			+ "AND AA.ACTIVE           = ? "
			+ "AND AA.CREATED_DATE     = ? "
			+ "AND AA.LAST_MODIFIED    = ? "
			+ "AND AA.PRODUCT_CODE     = ? "
			+ "AND AA.OWNER_ID         = ? "
			+ "AND AA.CITY_CODE       IN (?) "
			+ "AND AA.PRICE           >= ? "
			+ "AND AA.PRICE           <= ? "
			+ "AND VA.brand_id         = ? "
			+ "AND VA.MODEL_ID         = ? "
			+ "AND VA.MODEL_YEAR      >= ? "
			+ "AND VA.MODEL_YEAR      <= ? "
			+ "AND VA.V_CONDITION     IN (?) "
			+ "AND VA.TRANSMISSION    IN (?) "
			+ "AND VA.BODY_TYPE       IN (?) "
			+ "AND VA.FUEL_TYPE       IN (?) "
			+ "AND VA.ENGINE_CAPACITY >= ? "
			+ "AND VA.ENGINE_CAPACITY <= ? "
			+ "AND VA.MILAGE          >= ? "
			+ "AND VA.MILAGE          <= ? "
			+ "ORDER BY AA.ADV_ID DESC";
}
