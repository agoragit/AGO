package it.ago.utils;

import it.ago.Product;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SystemPropertyCache
{
	static Properties SYSTEM_PROPERTY = new Properties();
	static String DB_CONF_PATH = "../conf/db.conf";
	static List<Product> cashProductsList = new ArrayList<>();

	static
	{
		loadDBProperties();
		loadProductCategories();
	}

	public static void init()
	{
		//loadDBProperties();
	}

	public static List<Product> getCashProductsList() {
		return cashProductsList;
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
	public static void loadProductCategories( ) throws JSONException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs  = null;
		cashProductsList.clear();
		try
		{
			con = DBConnection.getConnection( DBConnection.MYSQL_CONNECTION_TYPE );
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
			DBConnection.close( con,ps,rs );
		}
	}
}
