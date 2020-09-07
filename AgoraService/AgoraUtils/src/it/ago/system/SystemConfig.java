package it.ago.system;

import it.ago.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SystemConfig
{
	private static Map<String, String> configurationMap = new HashMap<>(  );

	public static int AGO_SESSION_TIME_OUT = 1;
	public static int AGO_CACHE_REFRESH_TIME = 1;
	public static int ADV_MAX_UPLOAD_IMAGE_COUNT = 5;
	public static String ADV_IMAGE_UPLOAD_PATH = "resources\\images\\adv\\";
	public static String ADV_ROOT_URL= "http://localhost:8080/";
	public static int ADV_MAX_RESULTS_LIMIT_PER_SEARCH = 100;
	public static final int SERVICE_ADVERTISEMENT = 0;
	public static final int SERVICE_SICURITY = 1;

	public static void loadConfigurations( int service )
	{
		if(configurationMap.isEmpty())
		{
			configurationMap.clear();
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try
			{
				con = DBConnection.getConnection( DBConnection.MYSQL_CONNECTION_TYPE );
				ps = con.prepareStatement( "SELECT * FROM SYSTEM_CONFIGURATION WHERE SERVICE = ? OR SERVICE = 0" );
				ps.setInt( 1, service );
				rs = ps.executeQuery();
				while ( rs.next() )
				{
					configurationMap.put( rs.getString( "CONFIG_CODE" ), rs.getString( "VALUE" ) );
				}
			}
			catch ( SQLException e )
			{

			}
			finally
			{
				DBConnection.close( con, ps, rs );
			}
		}
	}

	public static String getString( String parameter )
	{
		return configurationMap.get( parameter );
	}
	public static int getInt( String parameter )
	{
		try
		{
			String val = configurationMap.get( parameter );
			if ( val != null && val.length() > 0 )
			{
				return Integer.parseInt( val );
			}
		}
		catch ( Exception e )
		{
			return -1;
		}
		return -1;
	}
	public static long getLong( String parameter )
	{
		try
		{
			String val = configurationMap.get( parameter );
			if ( val != null && val.length() > 0 )
			{
				return Long.parseLong( val );
			}
		}
		catch ( Exception e )
		{
			return -1;
		}
		return -1;
	}
	public static boolean getBoolean( String parameter )
	{
		try
		{
			String val = configurationMap.get( parameter );
			if ( val != null && val.length() > 0 )
			{
				return Boolean.parseBoolean( val );
			}
		}
		catch ( Exception e )
		{
			return false;
		}
		return false;
	}
}
