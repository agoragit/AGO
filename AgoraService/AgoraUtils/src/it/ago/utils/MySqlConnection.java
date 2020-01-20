package it.ago.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract class MySqlConnection extends DBConnection
{
	protected static Connection getConnection() throws SQLException
	{
		try
		{
			Class.forName( "com.mysql.jdbc.Driver" ).newInstance();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		return DriverManager.getConnection(
				SystemPropertyCache.SYSTEM_PROPERTY.getProperty( SystemConstance.MYSQL_DB_URL ), SystemPropertyCache.SYSTEM_PROPERTY.getProperty( SystemConstance.MYSQL_DB_USER ), SystemPropertyCache.SYSTEM_PROPERTY.getProperty( SystemConstance.MYSQL_DB_PASSWORD ) );
	}
}
