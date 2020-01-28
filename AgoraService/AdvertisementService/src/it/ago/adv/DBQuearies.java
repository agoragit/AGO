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
	public static String Q_SEARCH_ADV_BY_OWNER_ID = "SELECT * FROM AGO_ADVERTISEMENT WHERE OWNER_ID  = ?";
	public static String Q_SEARCH_VEHI_ADV_BY_ADV_ID = "SELECT * FROM VEHICLE_ADVERTISEMENT WHERE ADV_ID  = ?";

	private static String getUniversalAdvSearchQuary( List<String> advTypes, boolean isAll, UriInfo uriInfo )
	{
		if ( advTypes == null || advTypes.isEmpty() )
		{
			return null;
		}
		StringBuilder sb = new StringBuilder( " SELECT * FROM ago_advertisement AA " );
		// create inner join - customize here for other products
		if ( isAll || advTypes.contains( Constants.ADV_PROD_VEHICLE ) )
		{
			sb.append( " INNER JOIN VEHICLE_ADVERTISEMENT VA ON VA.ADV_ID = AA.ADV_ID " );
		}
		//ToDO : other product inner joins

		sb.append( " WHERE true " );
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

		// add product wise filter columns here
		//_________________________________________________
		if ( isAll || advTypes.contains( Constants.ADV_PROD_VEHICLE ) )
		{
			if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_VEHI_BRAND_ID) )
				sb.append( " AND VA.BRAND_ID         = ? " );
			if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_VEHI_MODEL_ID) )
			sb.append( " AND VA.MODEL_ID         = ? " );
			if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_VEHI_MODEL_YEAR_FROM) )
			sb.append( " AND VA.MODEL_YEAR      >= ? " );
			if( UriInfoUtils.isNotNull( uriInfo,Constants.PARAM_VEHI_MODEL_YEAR_TO) )
			sb.append( " AND VA.MODEL_YEAR      <= ? " );
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
		//___________________________________________________
		sb.append( " ORDER BY AA.ADV_ID DESC " );

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
		PreparedStatement ps = con.prepareStatement( getUniversalAdvSearchQuary( advTypesList, isAll, uriInfo ) );
		int count = 0;
		UriInfoUtils.setPreparedValue( ps, count, Types.BIGINT, uriInfo, Constants.PARAM_ADV_ID );
		UriInfoUtils.setPreparedValue( ps, count, Types.TIMESTAMP, uriInfo, Constants.PARAM_ADV_VALID_FROM );
		UriInfoUtils.setPreparedValue( ps, count, Types.TIMESTAMP, uriInfo, Constants.PARAM_ADV_VALID_TO );
		UriInfoUtils.setPreparedValue( ps, count, Types.BOOLEAN, uriInfo, Constants.PARAM_ADV_ACTIVE );
		UriInfoUtils.setPreparedValue( ps, count, Types.TIMESTAMP, uriInfo, Constants.PARAM_ADV_CREATED_DATE_FROM );
		UriInfoUtils.setPreparedValue( ps, count, Types.TIMESTAMP, uriInfo, Constants.PARAM_ADV_CREATED_DATE_TO );
		UriInfoUtils.setPreparedValue( ps, count, Types.TIMESTAMP, uriInfo, Constants.PARAM_ADV_LAST_MODIFIED );
		UriInfoUtils.setPreparedValue( ps, count, Types.VARCHAR, uriInfo, Constants.PARAM_ADV_PRODUCT_CODE );
		UriInfoUtils.setPreparedValue( ps, count, Types.BIGINT, uriInfo, Constants.PARAM_ADV_OWNER_ID );
		UriInfoUtils.setPreparedValue( ps, count, Types.ARRAY, uriInfo, Constants.PARAM_ADV_CITY_CODE );
		UriInfoUtils.setPreparedValue( ps, count, Types.DOUBLE, uriInfo, Constants.PARAM_ADV_PRICE_FROM );
		UriInfoUtils.setPreparedValue( ps, count, Types.DOUBLE, uriInfo, Constants.PARAM_ADV_PRICE_TO );

		//------- vehicle adv filter data
		if ( isAll || advTypesList.contains( Constants.ADV_PROD_VEHICLE ) )
		{
			UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_VEHI_BRAND_ID );
			UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_VEHI_MODEL_ID );
			UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_VEHI_MODEL_YEAR_FROM );
			UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_VEHI_MODEL_YEAR_TO );
			UriInfoUtils.setPreparedValue( ps, count, Types.VARCHAR, uriInfo, Constants.PARAM_VEHI_CONDITION );
			UriInfoUtils.setPreparedValue( ps, count, Types.VARCHAR, uriInfo, Constants.PARAM_VEHI_TRANSMISSION );
			UriInfoUtils.setPreparedValue( ps, count, Types.VARCHAR, uriInfo, Constants.PARAM_VEHI_BODY_TYPE );
			UriInfoUtils.setPreparedValue( ps, count, Types.VARCHAR, uriInfo, Constants.PARAM_VEHI_FUEL_TYPE );
			UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_VEHI_ENGINE_CAPACITY_FROM );
			UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_VEHI_ENGINE_CAPACITY_TO );
			UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_VEHI_MILLAGE_FROM );
			UriInfoUtils.setPreparedValue( ps, count, Types.INTEGER, uriInfo, Constants.PARAM_VEHI_MILLAGE_TO );

		}
		return ps;
	}
}
