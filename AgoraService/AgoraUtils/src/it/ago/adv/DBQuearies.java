package it.ago.adv;

import it.ago.Constants;
import it.ago.UriInfoUtils;

import javax.ws.rs.core.UriInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBQuearies
{
	public static String Q_SEARCH_ADV_BY_ID = "SELECT * FROM AGO_ADVERTISEMENT WHERE ADV_ID  = ?";
	public static String Q_SEARCH_ADV_BY_OWNER_ID = "SELECT * FROM AGO_ADVERTISEMENT,OWNER AGO_ADVERTISEMENT.OWNER_ID = OWNER.OWNER_ID WHERE AGO_ADVERTISEMENT.OWNER_ID  = ?";
	public static String Q_SEARCH_VEHI_ADV_BY_ADV_ID = "SELECT * FROM VEHICLE_ADVERTISEMENT WHERE ADV_ID  = ?";
	public static String Q_LOAD_ADV_IMAGE = "SELECT * FROM ADV_IMAGE WHERE ADV_ID  = ? AND IMAGE_ID = ?";
	public static String Q_ACTIVE_ADV_BY_ID = "UPDATE AGO_ADVERTISEMENT SET ACTIVE= ? WHERE ADV_ID  = ?";
	public static String Q_ACTIVE_OWNER_BY_ID = "UPDATE OWNER SET ACTIVE= ? WHERE OWNER_ID  = ?";



	private static String getUniversalAdvSearchQueary( List<String> advTypes, boolean isAll, UriInfo uriInfo )
	{
		if ( advTypes == null || advTypes.isEmpty() )
		{
			return null;
		}
		StringBuilder sb = new StringBuilder( " SELECT * FROM ago_advertisement AA  INNER JOIN OWNER O ON O.OWNER_ID = AA.OWNER_ID " );
		// create inner join - customize here for other products
		if ( isAll || advTypes.contains( Constants.ADV_PROD_VEHICLE ) )
		{
			sb.append( " LEFT JOIN VEHICLE_ADVERTISEMENT VA ON VA.ADV_ID = AA.ADV_ID "  );
		}
		if ( isAll || advTypes.contains( Constants.ADV_PROD_PROPERTY) )
		{
			sb.append( " LEFT JOIN PROPERTY_ADVERTISEMENT PA ON PA.ADV_ID = AA.ADV_ID " );
		}
		//ToDO : other product Left joins

		sb.append( " WHERE " );
		sb.append( " AA.ACTIVE = true " );
		sb.append( " AND O.ACTIVE = true " );

		if( !isAll )
		{
			sb.append( " AND AA.PRODUCT_CODE IN ( "+_getInQueary( advTypes )+") ");
		}
		if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_ADV_ID ) )
			sb.append( " AND AA.ADV_ID         = ? " );
		if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_ADV_VALID_FROM ) )
			sb.append( " AND AA.VALID_FROM       >= ? " );
		if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_ADV_VALID_TO ) )
			sb.append( " AND AA.VALID_TO         <= ? " );
		if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_ADV_ACTIVE) )
			sb.append( " AND AA.ACTIVE           = ? " );
		if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_ADV_CREATED_DATE_FROM) )
			sb.append( " AND AA.CREATED_DATE     >= ? " );
		if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_ADV_CREATED_DATE_TO) )
			sb.append( " AND AA.CREATED_DATE     <= ? " );
		if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_ADV_LAST_MODIFIED) )
			sb.append( " AND AA.LAST_MODIFIED    = ? " );
		if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_ADV_PRODUCT_CODE) )
			sb.append( " AND AA.PRODUCT_CODE     = ? " );
		if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_ADV_OWNER_ID) )
			sb.append( " AND AA.OWNER_ID         = ? " );
		if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_ADV_CITY_CODE) )
			sb.append( " AND AA.CITY_CODE       IN (?) " );
		if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_ADV_PRICE_FROM) )
			sb.append( " AND AA.PRICE           >= ? " );
		if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_ADV_PRICE_TO) )
			sb.append( " AND AA.PRICE           <= ? " );
		if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_ADV_RENT) )
			sb.append( " AND AA.RENT         	 = ? " );
		if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_ADV_WANTED_TO_BUY) )
			sb.append( " AND AA.WANTED_TO_BUY    = ? " );
		if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_OWNER_TYPE) )
			sb.append( " AND O.TYPE         = ? " );
		if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_ADV_KEYWORDS ) )
			sb.append( generateKeywordsQueary( UriInfoUtils.getStringValue( uriInfo, Constants.PARAM_ADV_KEYWORDS )) );
		if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_ADV_BRAND_ID) )
			sb.append( " AND AA.BRAND_ID         = ? " );
		if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_ADV_MODEL_ID) )
			sb.append( " AND AA.MODEL_ID         = ? " );
		if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_ADV_MODEL_YEAR_FROM) )
			sb.append( " AND VA.MODEL_YEAR      >= ? " );
		if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_ADV_MODEL_YEAR_TO) )
			sb.append( " AND VA.MODEL_YEAR      <= ? " );
		if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_ADV_TYPE_LEVEL_1) && UriInfoUtils.isNotZero( uriInfo,Constants.PARAM_ADV_TYPE_LEVEL_1))
			sb.append( " AND AA.TYPE_LEVEL_1         = ? " );
		if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_ADV_TYPE_LEVEL_2) && UriInfoUtils.isNotZero( uriInfo,Constants.PARAM_ADV_TYPE_LEVEL_2) )
			sb.append( " AND AA.TYPE_LEVEL_2         = ? " );
		if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_ADV_TYPE_LEVEL_3) && UriInfoUtils.isNotZero( uriInfo,Constants.PARAM_ADV_TYPE_LEVEL_3))
			sb.append( " AND AA.TYPE_LEVEL_3         = ? " );
		// add product wise filter columns here
		//_________________________________________________
		if ( isAll || advTypes.contains( Constants.ADV_PROD_VEHICLE ) )
		{
			if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_VEHI_CONDITION) )
				sb.append( " AND VA.V_CONDITION     IN (?) " );
			if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_VEHI_TRANSMISSION) )
				sb.append( " AND VA.TRANSMISSION    IN (?) " );
			if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_VEHI_BODY_TYPE) )
				sb.append( " AND VA.BODY_TYPE       IN (?) " );
			if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_VEHI_FUEL_TYPE) )
				sb.append( " AND VA.FUEL_TYPE       IN (?) " );
			if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_VEHI_ENGINE_CAPACITY_FROM) )
				sb.append( " AND VA.ENGINE_CAPACITY >= ? " );
			if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_VEHI_ENGINE_CAPACITY_TO) )
				sb.append( " AND VA.ENGINE_CAPACITY <= ? " );
			if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_VEHI_MILLAGE_FROM) )
				sb.append( " AND VA.MILAGE          >= ? " );
			if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_VEHI_MILLAGE_TO) )
				sb.append( " AND VA.MILAGE          <= ? " );
		}
		if ( isAll || advTypes.contains( Constants.ADV_PROD_PROPERTY ) )
		{
			if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_PROP_DISTANCE_TO_MAIN_RD) )
				sb.append( " AND PA.DISTANCE_TO_MAIN_RD         <= ? " );
			if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_PROP_LAND_SIZE_FROM) )
				sb.append( " AND PA.LAND_SIZE         >= ? " );
			if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_PROP_LAND_SIZE_TO) )
				sb.append( " AND PA.LAND_SIZE         <= ? " );
			if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_PROP_HOUSE_SIZE_FROM) )
				sb.append( " AND PA.HOUSE_SIZE      >= ? " );
			if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_PROP_HOUSE_SIZE_TO) )
				sb.append( " AND PA.HOUSE_SIZE      <= ? " );
			if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_PROP_BATH) )
				sb.append( " AND PA.BATH     >= ? " );
			if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_PROP_BEDS_FROM) )
				sb.append( " AND PA.BEDS    >= ? " );
			if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_PROP_BEDS_TO) )
				sb.append( " AND PA.BEDS  <= ? " );
			if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_PROP_TYPE_ID) )
				sb.append( " AND PA.PROPERTY_TYPE       IN (?) " );
			if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_PROP_PARKING) )
				sb.append( " AND PA.PARKING >= ? " );
		}
		//___________________________________________________
		sb.append( " ORDER BY AA.ADV_ID DESC LIMIT ?,? " ); //offset, rowcount

		return sb.toString();
	}

	public static PreparedStatement getUniversalAdvSearchStatement( Connection con, UriInfo uriInfo ) throws SQLException
	{
		String proTypes = UriInfoUtils.getStringValue( uriInfo, Constants.PARAM_PRODUCT_TYPES  );
		List<String> advTypesList = new ArrayList<>(  );
		if( proTypes != null && proTypes.length() > 0 )
		{
			String[] elements = proTypes.split( "," );
			advTypesList = Arrays.asList( elements );
		}
		else
		{
			advTypesList.add( Constants.ADV_PRODUCT_ANY );
		}
		boolean isAll = advTypesList.contains( Constants.ADV_PRODUCT_ANY );
		PreparedStatement ps = con.prepareStatement( getUniversalAdvSearchQueary( advTypesList, isAll, uriInfo ) );
		int count = 0;
		count = UriInfoUtils.setPreparedValue( ps, count, Types.BIGINT, uriInfo, Constants.PARAM_ADV_ID );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.TIMESTAMP, uriInfo, Constants.PARAM_ADV_VALID_FROM );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.TIMESTAMP, uriInfo, Constants.PARAM_ADV_VALID_TO );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.BOOLEAN, uriInfo, Constants.PARAM_ADV_ACTIVE );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.TIMESTAMP, uriInfo, Constants.PARAM_ADV_CREATED_DATE_FROM );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.TIMESTAMP, uriInfo, Constants.PARAM_ADV_CREATED_DATE_TO );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.TIMESTAMP, uriInfo, Constants.PARAM_ADV_LAST_MODIFIED );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.VARCHAR, uriInfo, Constants.PARAM_ADV_PRODUCT_CODE );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.BIGINT, uriInfo, Constants.PARAM_ADV_OWNER_ID );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.ARRAY, uriInfo, Constants.PARAM_ADV_CITY_CODE );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.DOUBLE, uriInfo, Constants.PARAM_ADV_PRICE_FROM );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.DOUBLE, uriInfo, Constants.PARAM_ADV_PRICE_TO );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.BOOLEAN, uriInfo, Constants.PARAM_ADV_RENT);
		count = UriInfoUtils.setPreparedValue( ps, count, Types.BOOLEAN, uriInfo, Constants.PARAM_ADV_WANTED_TO_BUY);
		count = UriInfoUtils.setPreparedValue( ps, count, Types.VARCHAR, uriInfo, Constants.PARAM_OWNER_TYPE );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.VARCHAR, uriInfo, Constants.PARAM_ADV_KEYWORDS );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_ADV_BRAND_ID );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_ADV_MODEL_ID);
		count = UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_ADV_MODEL_YEAR_FROM );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_ADV_MODEL_YEAR_TO );
		if( UriInfoUtils.isNotZero( uriInfo,Constants.PARAM_ADV_TYPE_LEVEL_1 ) )
		count = UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_ADV_TYPE_LEVEL_1);
		if( UriInfoUtils.isNotZero( uriInfo,Constants.PARAM_ADV_TYPE_LEVEL_2 ) )
		count = UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_ADV_TYPE_LEVEL_2 );
		if( UriInfoUtils.isNotZero( uriInfo,Constants.PARAM_ADV_TYPE_LEVEL_3 ) )
		count = UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_ADV_TYPE_LEVEL_3 );

		//------- vehicle adv filter data
		if ( isAll || advTypesList.contains( Constants.ADV_PROD_VEHICLE ) )
	{
		count = UriInfoUtils.setPreparedValue( ps, count, Types.VARCHAR, uriInfo, Constants.PARAM_VEHI_CONDITION );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.VARCHAR, uriInfo, Constants.PARAM_VEHI_TRANSMISSION );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.VARCHAR, uriInfo, Constants.PARAM_VEHI_BODY_TYPE );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.VARCHAR, uriInfo, Constants.PARAM_VEHI_FUEL_TYPE );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_VEHI_ENGINE_CAPACITY_FROM );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_VEHI_ENGINE_CAPACITY_TO );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_VEHI_MILLAGE_FROM );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_VEHI_MILLAGE_TO );

	}
		//------- property adv filter data
	if ( isAll || advTypesList.contains( Constants.ADV_PROD_PROPERTY ) )
	{
		count = UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_PROP_DISTANCE_TO_MAIN_RD);
		count = UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_PROP_LAND_SIZE_FROM );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_PROP_LAND_SIZE_TO );
		count = UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_PROP_HOUSE_SIZE_FROM);
		count = UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_PROP_HOUSE_SIZE_TO);
		count = UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_PROP_BATH);
		count = UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_PROP_BEDS_FROM);
		count = UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_PROP_BEDS_TO);
		count = UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_PROP_TYPE_ID);
		count = UriInfoUtils.setPreparedValue( ps, count, Types.BOOLEAN, uriInfo, Constants.PARAM_PROP_PARKING);

	}

		//finally
		count = UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_ADV_QUARY_OFFSET);
		count = UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_ADV_QUARY_ROW_COUNT);


		return ps;
	}

	private static String _getInQueary( List<String> advTypes )
	{
		String q="";
		for ( int i = 0; i < advTypes.size(); i++ )
		{
			String advT = advTypes.get( i );
			q = q +"'"+ advT + "'";
			if ( i <advTypes.size()-1 )
			{
				q+=",";
			}
		}
		return q;
	}

	private static String generateKeywordsQueary( String keywords )
	{
		if( keywords == null || keywords.length() == 0 )
		{
			return "";
		}
		else
		{
			keywords = keywords.toLowerCase();
			if( keywords.contains( "delete " )|| keywords.contains( "insert " ) || keywords.contains( "update " ) || keywords.contains( "alter " ) || keywords.contains( "create " ))
			{
				return "";
			}
			keywords = keywords.replaceAll( " ","," );
		}
		String q = " AND ( ";

		String keys[] = keywords.split( "," );
		for ( int i = 0; i < keys.length; i++ )
		{
			if( keys[i] != null && keys[i].length() > 2 )
			{
				q = q + " AA.KEYWORDS LIKE '%" + keys[i] + "%' ";
			}
			if ( keys.length - 1 < i && q.contains( " AA.KEYWORDS LIKE '%" ) )
			{
				q= q+" OR ";
			}
		}
		q=q+" ) ";
		return q;
	}
}
