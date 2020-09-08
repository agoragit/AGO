package it.ago;

import it.ago.utils.DBConnection;
import it.ago.utils.db.Savable;

import javax.xml.bind.annotation.XmlType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "LocationDistrict", namespace = "http://lass")
public class LocationDistrict extends Savable
{
	private String districtCode;
	private String districtName;
	private int status;
	private List<LocationCity> locationCities;

	public LocationDistrict()
	{
		locationCities = new ArrayList<>(  );
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
		String str = "INSERT INTO LOCATION_DISTRICT ( "
				+ "DISTRICT_CODE, "
				+ "DISTRICT_NAME )VALUES(?,? )";
		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setString( ++count, this.districtCode );
		ps.setString( ++count, this.districtName );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This deletes the ........
	 */
	public void delete( Connection con ) throws SQLException
	{
		String str = "DELETE FROM LOCATION_DISTRICT WHERE "
				+ "DISTRICT_CODE = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setString( ++count, this.districtCode );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This updates the .......
	 */
	public void update( Connection con ) throws SQLException
	{
		String str = "UPDATE LOCATION_DISTRICT SET "
				+ "DISTRICT_NAME = ? WHERE "
				+ "DISTRICT_CODE = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setString( ++count, this.districtName );
		ps.setString( ++count, this.districtCode );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This loads the .......
	 */
	public void load( ResultSet rs, Connection con, int level ) throws SQLException
	{
		this.status = Savable.UNCHANGED;
		this.districtCode = rs.getString( "DISTRICT_CODE" );
		this.districtName = rs.getString( "DISTRICT_NAME" );

		if(level > 0)
		{
			loadCities( con, level );
		}

	}

	public String getDistrictCode()
	{
		return this.districtCode;
	}

	public void setDistrictCode( String districtCode )
	{
		this.districtCode = districtCode;
	}

	public String getDistrictName()
	{
		return this.districtName;
	}

	public void setDistrictName( String districtName )
	{
		this.districtName = districtName;
	}

	public int getStatus()
	{
		return this.status;
	}

	public void setStatus( int status )
	{
		this.status = status;
	}

	public List<LocationCity> getLocationCities()
	{
		return locationCities;
	}

	public void setLocationCities( List<LocationCity> locationCities )
	{
		this.locationCities = locationCities;
	}

	private void loadCities( Connection con, int level )
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			ps = con.prepareStatement( "SELECT * FROM location_city WHERE DISTRICT_CODE = ?");
			ps.setString( 1, this.districtCode );
			rs = ps.executeQuery();
			while (rs.next())
			{
				LocationCity locationCity = new LocationCity();
				locationCity.load(rs, con, level);
				locationCities.add(locationCity);
			}

		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			DBConnection.close(rs);
			DBConnection.close(ps);
		}

	}
}
