package it.ago.utils;

import it.ago.LocationDistrict;
import it.ago.Product;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SystemPropertyCache
{
	static Properties SYSTEM_PROPERTY = new Properties();
	static String DB_CONF_PATH = "../conf/db.conf";
	static List<Product> cashProductsList = new ArrayList<>();
	static List<LocationDistrict> locationDistricts = new ArrayList<>();


	static
	{
		loadDBProperties();
		Connection con = null;
		try
		{
			con = DBConnection.getConnection( DBConnection.MYSQL_CONNECTION_TYPE );
			loadProductCategories( con );
			loadLocationDistricts( con );
		}
		catch ( SQLException e )
		{
			e.printStackTrace();
		}
		finally
		{
			DBConnection.close( con );
		}

	}

	public static void init()
	{
		//loadDBProperties();
	}

	public static List<Product> getCashProductsList() {
		return cashProductsList;
	}

	public static List<LocationDistrict> getLocationDistricts()
	{
		return locationDistricts;
	}

	private static void loadDBProperties()
	{

		try (InputStream input = new FileInputStream( DB_CONF_PATH ))
		{

			// load a properties file
			SYSTEM_PROPERTY.load( input );

		}
		catch ( IOException ex )
		{
			ex.printStackTrace();
		}

	}
	public static void loadProductCategories( Connection con ) throws JSONException
	{
		PreparedStatement ps = null;
		ResultSet rs  = null;
		cashProductsList.clear();
		try
		{
			ps = con.prepareStatement( "SELECT * FROM PRODUCT " );
			rs  = ps.executeQuery();
			while( rs.next() )
			{
				Product product = new Product();
				product.load( rs, con, 1 );
				cashProductsList.add(product);
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			DBConnection.close( null,ps,rs );
		}
	}
	public static void loadLocationDistricts( Connection con ) throws JSONException
	{
		PreparedStatement ps = null;
		ResultSet rs  = null;
		cashProductsList.clear();
		try
		{
			con = DBConnection.getConnection( DBConnection.MYSQL_CONNECTION_TYPE );
			ps = con.prepareStatement( "SELECT * FROM LOCATION_DISTRICT " );
			rs  = ps.executeQuery();
			while( rs.next() )
			{
				LocationDistrict locationDistrict = new LocationDistrict();
				locationDistrict.load( rs, con, 1000 );
				locationDistricts.add(locationDistrict);
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			DBConnection.close( null,ps,rs );
		}
	}
}
