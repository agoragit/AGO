package it.ago.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DBConnection
{
	public static String MYSQL_CONNECTION_TYPE = "MYSQL";

	public static Connection getConnection( String connectionType ) throws SQLException
	{
		if ( MYSQL_CONNECTION_TYPE.equals( connectionType ) )
		{
			return MySqlConnection.getConnection();
		}
		return null;
	}

	public static void close( Connection con, PreparedStatement ps, ResultSet rs )
	{
		if ( con != null )
		{
			try
			{
				con.close();
			}
			catch ( SQLException e )
			{
				e.printStackTrace();
			}
		}
		if ( ps != null )
		{
			try
			{
				ps.close();
			}
			catch ( SQLException e )
			{
				e.printStackTrace();
			}
		}
		if ( rs != null )
		{
			try
			{
				rs.close();
			}
			catch ( SQLException e )
			{
				e.printStackTrace();
			}
		}
	}

	public static void close( Connection con )
	{
		if ( con != null )
		{
			try
			{
				con.close();
			}
			catch ( SQLException e )
			{
				e.printStackTrace();
			}
		}
	}

	public static void close( PreparedStatement ps )
	{
		if ( ps != null )
		{
			try
			{
				ps.close();
			}
			catch ( SQLException e )
			{
				e.printStackTrace();
			}
		}
	}

	public static void close( ResultSet rs )
	{
		if ( rs != null )
		{
			try
			{
				rs.close();
			}
			catch ( SQLException e )
			{
				e.printStackTrace();
			}
		}
	}
}
