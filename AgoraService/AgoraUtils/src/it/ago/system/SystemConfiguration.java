package it.ago.system;

import it.ago.utils.DBConnection;
import it.ago.utils.db.Savable;

import javax.xml.bind.annotation.XmlType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@XmlType(name = "SystemConfiguration", namespace = "http://lass")
public class SystemConfiguration extends Savable
{
	private String configCode;
	private String value;
	private String description;
	private int service;
	private int status;

	public SystemConfiguration()
	{
	}

	public void checkValidity() throws SQLException
	{
	}

	/**
	 * This insert/modify or update depending on the action
	 */
	public void save( Connection con ) throws SQLException
	{
		String action = "";
		try
		{
			if ( this.status == Savable.NEW )
			{
				action = "Inserting";
				checkValidity();
				insert( con );
			}
			else if ( this.status == Savable.MODIFIED )
			{
				action = "Updating";
				checkValidity();
				update( con );
			}
			else if ( this.status == Savable.DELETED )
			{
				action = "Deleting";
				checkValidity();
				delete( con );
			}
			else if ( this.status == Savable.UNCHANGED )
			{
				//Do nothing
			}
			else
			{
				throw new SQLException( "Incorret setting of Status flag!" );
			}
		}
		catch ( SQLException se )
		{
			se.printStackTrace();
			throw new SQLException( "Error in " + action +
					se.getMessage(),
					se.getSQLState(),
					se.getErrorCode() );
		}
	}

	/**
	 * This inserts the .........
	 */
	private void insert( Connection con ) throws SQLException
	{
		String str = "INSERT INTO SYSTEM_CONFIGURATION ( "
				+ "CONFIG_CODE, "
				+ "VALUE, "
				+ "DESCRIPTION, SERVICE )VALUES(?,?,?,? )";
		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setString( ++count, this.configCode );
		ps.setString( ++count, this.value );
		ps.setString( ++count, this.description );
		ps.setInt( ++count, this.service );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This deletes the ........
	 */
	public void delete( Connection con ) throws SQLException
	{
		String str = "DELETE FROM SYSTEM_CONFIGURATION WHERE "
				+ "CONFIG_CODE = ? AND SERVICE = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setString( ++count, this.configCode );
		ps.setInt( ++count, this.service );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This updates the .......
	 */
	public void update( Connection con ) throws SQLException
	{
		String str = "UPDATE SYSTEM_CONFIGURATION SET "
				+ "VALUE = ?, "
				+ "DESCRIPTION = ?, SERVICE = ?  WHERE "
				+ "CONFIG_CODE = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setString( ++count, this.value );
		ps.setString( ++count, this.description );
		ps.setInt( ++count, this.service );
		ps.setString( ++count, this.configCode );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This loads the .......
	 */
	public void load( ResultSet rs, Connection con, int level ) throws SQLException
	{
		this.status = Savable.UNCHANGED;
		this.configCode = rs.getString( "CONFIG_CODE" );
		this.value = rs.getString( "VALUE" );
		this.description = rs.getString( "DESCRIPTION" );

	}

	public String getConfigCode()
	{
		return this.configCode;
	}

	public void setConfigCode( String configCode )
	{
		this.configCode = configCode;
	}

	public String getValue()
	{
		return this.value;
	}

	public void setValue( String value )
	{
		this.value = value;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

	public int getStatus()
	{
		return this.status;
	}

	public void setStatus( int status )
	{
		this.status = status;
	}

	public int getService()
	{
		return service;
	}

	public void setService( int service )
	{
		this.service = service;
	}
}
