package it.ago;

import javax.ws.rs.core.UriInfo;
import java.sql.Timestamp;

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
}
