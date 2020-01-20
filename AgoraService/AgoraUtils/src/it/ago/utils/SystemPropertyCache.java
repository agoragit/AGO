package it.ago.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SystemPropertyCache
{
	static Properties SYSTEM_PROPERTY = new Properties();
	static String DB_CONF_PATH = "../conf/db.conf";

	static
	{
		loadDBProperties();
	}

	public static void init()
	{
		loadDBProperties();
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
}
