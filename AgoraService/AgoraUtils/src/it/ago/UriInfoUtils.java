package it.ago;

import javax.ws.rs.core.UriInfo;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

public class UriInfoUtils
{
	public static int getIntValue( UriInfo uriInfo, String parameter )
	{
		String stringVal = uriInfo.getQueryParameters().getFirst( parameter );
		if( stringVal != null && stringVal.length() > 0 )
		{
			try
			{
				return Integer.parseInt( stringVal );
			}
			catch ( Exception e)
			{
			}
		}
		return -1;
	}
	public static long getLongValue( UriInfo uriInfo, String parameter )
	{
		String stringVal = uriInfo.getQueryParameters().getFirst( parameter );
		if( stringVal != null && stringVal.length() > 0 )
		{
			try
			{
				return Long.parseLong( stringVal );
			}
			catch ( Exception e)
			{
			}
		}
		return -1;
	}
	public static double getDoubleValue( UriInfo uriInfo, String parameter )
	{
		String stringVal = uriInfo.getQueryParameters().getFirst( parameter );
		if( stringVal != null && stringVal.length() > 0 )
		{
			try
			{
				return Double.parseDouble( stringVal );
			}
			catch ( Exception e)
			{
			}
		}
		return -1;
	}
	public static String getStringValue( UriInfo uriInfo, String parameter )
	{
		return uriInfo.getQueryParameters(  ).getFirst( parameter );
	}
	public static boolean getBooleanValue( UriInfo uriInfo, String parameter )
	{
		return Boolean.parseBoolean( uriInfo.getQueryParameters().getFirst( parameter ) );
	}
	public static Timestamp getTimestamp( UriInfo uriInfo, String parameter )
	{
		try
		{
			return Timestamp.valueOf( uriInfo.getQueryParameters().getFirst( parameter ) );
		}
		catch ( Exception e )
		{
			return null;
		}
	}
	public static boolean isNotNull( UriInfo uriInfo, String parameter )
	{
		String val = getStringValue( uriInfo,parameter );
		if( val == null || val.length() == 0)
		{
			return false;
		}
		return true;
	}
	private static String getInQuaryValueFromCommaSeparatedValues( String value )
	{
		if(value == null || value.length() == 0)
		{
			return null;
		}
		String newData = value.replace( ",","','" );
		return  "'"+newData+"'";

	}
	public static int setPreparedValue(PreparedStatement ps, int count, int sqlType, UriInfo uriInfo, String parameter ) throws SQLException
	{
		if( sqlType == Types.INTEGER )
		{
			int value = getIntValue( uriInfo, parameter );
			if( value == -1)
			{
				//ps.setNull( count, Types.INTEGER );
			}
			else
			{
				ps.setInt( ++count, value );
			}
		}
		else if( sqlType == Types.BIGINT )
		{
			long value = getLongValue( uriInfo, parameter );
			if( value == -1)
			{
				//ps.setNull( count, Types.BIGINT );
			}
			else
			{
				ps.setLong( ++count, value );
			}
		}
		else if( sqlType == Types.DOUBLE )
		{
			double value = getDoubleValue( uriInfo, parameter );
			if( value == -1)
			{
				//ps.setNull( count, Types.DOUBLE );
			}
			else
			{
				ps.setDouble( ++count, value );
			}
		}
		else if( sqlType == Types.VARCHAR )
		{
			String value = getStringValue( uriInfo, parameter );
			if( value == null )
			{
				//ps.setNull( count, Types.VARCHAR );
			}
			else
			{
				ps.setString( ++count, value );
			}
		}
		else if( sqlType == Types.TIMESTAMP )
		{
			Timestamp value = getTimestamp( uriInfo, parameter );
			if( value == null )
			{
				//ps.setNull( count, Types.TIMESTAMP );
			}
			else
			{
				ps.setTimestamp( ++count, value );
			}
		}
		else if( sqlType == Types.BOOLEAN )
		{
			if(uriInfo.getQueryParameters().getFirst( parameter ) != null)
			{
				boolean value = getBooleanValue( uriInfo, parameter );
				ps.setBoolean( ++count, value );
			}
		}
		else if( sqlType == Types.ARRAY )
		{
			String value = getInQuaryValueFromCommaSeparatedValues(UriInfoUtils.getStringValue( uriInfo, parameter ));
			if( value == null )
			{
				//ps.setNull( count, Types.VARCHAR );
			}
			else
			{
				ps.setString( ++count, value );
			}
		}
		else
		{
			throw new SQLException( "err mapping satement type" );
		}
		return count;
	}
}
