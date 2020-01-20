package it.ago;

import it.ago.utils.DBConnection;
import it.ago.utils.db.Savable;

import javax.xml.bind.annotation.XmlType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@XmlType(name = "LocationCity", namespace = "http://lass")
public class LocationCity extends Savable
{
	private String cityCode;
	private String cityName;
	private String districtCode;
	private int status;

	public LocationCity()
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
		String str = "INSERT INTO LOCATION_CITY ( "
				+ "CITY_CODE, "
				+ "CITY_NAME, "
				+ "DISTRICT_CODE )VALUES(?,?,? )";
		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setString( ++count, this.cityCode );
		ps.setString( ++count, this.cityName );
		ps.setString( ++count, this.districtCode );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This deletes the ........
	 */
	public void delete( Connection con ) throws SQLException
	{
		String str = "DELETE FROM LOCATION_CITY WHERE "
				+ "CITY_CODE = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setString( ++count, this.cityCode );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This updates the .......
	 */
	public void update( Connection con ) throws SQLException
	{
		String str = "UPDATE LOCATION_CITY SET "
				+ "CITY_NAME = ?, "
				+ "DISTRICT_CODE = ? WHERE "
				+ "CITY_CODE = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setString( ++count, this.cityName );
		ps.setString( ++count, this.districtCode );
		ps.setString( ++count, this.cityCode );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This loads the .......
	 */
	public void load( ResultSet rs, Connection con, int level ) throws SQLException
	{
		this.status = Savable.UNCHANGED;
		this.cityCode = rs.getString( "CITY_CODE" );
		this.cityName = rs.getString( "CITY_NAME" );
		this.districtCode = rs.getString( "DISTRICT_CODE" );

	}

	public String getCityCode()
	{
		return this.cityCode;
	}

	public void setCityCode( String cityCode )
	{
		this.cityCode = cityCode;
	}

	public String getCityName()
	{
		return this.cityName;
	}

	public void setCityName( String cityName )
	{
		this.cityName = cityName;
	}

	public String getDistrictCode()
	{
		return this.districtCode;
	}

	public void setDistrictCode( String districtCode )
	{
		this.districtCode = districtCode;
	}

	public int getStatus()
	{
		return this.status;
	}

	public void setStatus( int status )
	{
		this.status = status;
	}

}
