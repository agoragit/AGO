package it.ago;


import it.ago.utils.DBConnection;
import it.ago.utils.db.Savable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import javax.xml.bind.annotation.XmlType;
@XmlType(name = "AdvProperty", namespace = "http://lass")
public class PropertyAvertisement extends Advertisement
{
	private int beds;
	private int bath;
	private float houseSize;
	private float landSize;
	private String description;
	private boolean parking;
	private int distanceToMainRd;
 	private int status;

	public PropertyAvertisement()
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
		super.save( con );
		String action = "";
		try
		{
			if( this.status == Savable.NEW )
			{
				action = "Inserting";
				checkValidity();
				insert(con);
			}
			else if ( this.status == Savable.MODIFIED )
			{
				action = "Updating";
				checkValidity();
 				update(con);
 			}
			else if (this.status == Savable.DELETED )
			{
				action = "Deleting";
				checkValidity();
				delete(con);
			}
			else if( this.status == Savable.UNCHANGED )
			{
				//Do nothing
			}
			else
			{
				throw new SQLException("Incorret setting of Status flag!");
			}
		}
		catch( SQLException se )
		{
			throw new SQLException( "Error in "+ action +
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
		String str = "INSERT INTO property_advertisement ( "
 		 + "ADV_ID, "
//		 + "PROPERTY_TYPE, "
		 + "BEDS, "
		 + "BATH, "
		 + "HOUSE_SIZE, "
		 + "LAND_SIZE, "
		 + "DESCRIPTION, "
		 + "PARKING, "
		 + "DISTANCE_TO_MAIN_RD )VALUES(?,?,?,?,?,?,?,? )";
		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setLong( ++count, super.getAdvId() );
		if( this.beds == -1 )
		{
			ps.setNull( ++count, java.sql.Types.NUMERIC );
		}
		else
		{
			ps.setInt( ++count, this.beds );
		}
		if( this.bath == -1 )
		{
			ps.setNull( ++count, java.sql.Types.NUMERIC );
		}
		else
		{
			ps.setInt( ++count, this.bath );
		}
		if( this.houseSize == -1 )
		{
			ps.setNull( ++count, java.sql.Types.NUMERIC );
		}
		else
		{
			ps.setFloat( ++count, this.houseSize );
		}
		if( this.landSize == -1 )
		{
			ps.setNull( ++count, java.sql.Types.NUMERIC );
		}
		else
		{
			ps.setFloat( ++count, this.landSize );
		}
		if( this.description == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.description );
		}

		ps.setBoolean( ++count, this.parking );

		if( this.distanceToMainRd == -1 )
		{
			ps.setNull( ++count, java.sql.Types.NUMERIC );
		}
		else
		{
			ps.setInt( ++count, this.distanceToMainRd );
		}
		ps.execute();
		DBConnection.close(ps);
	}

	/**
	 * This deletes the ........
	 */
	public void delete( Connection con ) throws SQLException
	{
		String str = "DELETE FROM property_advertisement WHERE ADV_ID = ?";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setLong( ++count, super.getAdvId() );
		ps.execute();
		DBConnection.close(ps);
	}
	/**
	 * This updates the .......
	 *
	 */
	public void update( Connection con ) throws SQLException
	{
		String str = "UPDATE property_advertisement SET "
//		 + "PROPERTY_TYPE = ?, "
		 + "BEDS = ?, "
		 + "BATH = ?, "
		 + "HOUSE_SIZE = ?, "
		 + "LAND_SIZE = ?, "
		 + "DESCRIPTION = ?, "
		 + "PARKING = ?, "
		 + "DISTANCE_TO_MAIN_RD = ? WHERE ADV_ID = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		if( this.beds == -1 )
		{
			ps.setNull( ++count, java.sql.Types.NUMERIC );
		}
		else
		{
			ps.setInt( ++count, this.beds );
		}
		if( this.bath == -1 )
		{
			ps.setNull( ++count, java.sql.Types.NUMERIC );
		}
		else
		{
			ps.setInt( ++count, this.bath );
		}
		if( this.houseSize == -1 )
		{
			ps.setNull( ++count, java.sql.Types.NUMERIC );
		}
		else
		{
			ps.setFloat( ++count, this.houseSize );
		}
		if( this.landSize == -1 )
		{
			ps.setNull( ++count, java.sql.Types.NUMERIC );
		}
		else
		{
			ps.setFloat( ++count, this.landSize );
		}
		if( this.description == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.description );
		}

		ps.setBoolean( ++count, this.parking );

		if( this.distanceToMainRd == -1 )
		{
			ps.setNull( ++count, java.sql.Types.NUMERIC );
		}
		else
		{
			ps.setInt( ++count, this.distanceToMainRd );
		}
		ps.setLong( ++count, super.getAdvId() );

		ps.execute();
		DBConnection.close(ps);
	}
	/**
	 * This loads the .......
	 */
	public void load( ResultSet rs, Connection con, int level ) throws SQLException
	{
		super.load( rs,con,level );
		this.status = Savable.UNCHANGED;

		if( rs.getObject( "BEDS" ) == null )
		{
			this.beds = -1;
		}
		else
		{
			this.beds = rs.getInt( "BEDS" );
		}
		if( rs.getObject( "BATH" ) == null )
		{
			this.bath = -1;
		}
		else
		{
			this.bath = rs.getInt( "BATH" );
		}
		if( rs.getObject( "HOUSE_SIZE" ) == null )
		{
			this.houseSize = -1;
		}
		else
		{
			this.houseSize = rs.getFloat( "HOUSE_SIZE" );
		}
		if( rs.getObject( "LAND_SIZE" ) == null )
		{
			this.landSize = -1;
		}
		else
		{
			this.landSize = rs.getFloat( "LAND_SIZE" );
		}
		if( rs.getObject( "DESCRIPTION" ) == null )
		{
			this.description = null;
		}
		else
		{
			this.description = rs.getString( "DESCRIPTION" );
		}
		if( rs.getObject( "PARKING" ) == null )
		{
			this.parking = false;
		}
		else
		{
			this.parking = rs.getBoolean( "PARKING" );
		}
		if( rs.getObject( "DISTANCE_TO_MAIN_RD" ) == null )
		{
			this.distanceToMainRd = -1;
		}
		else
		{
			this.distanceToMainRd = rs.getInt( "DISTANCE_TO_MAIN_RD" );
		}


	}

	public int getBeds()
	{
		return this.beds;
	}

	public void setBeds( int beds )
	{
		this.beds = beds;
	}

	public int getBath()
	{
		return this.bath;
	}

	public void setBath( int bath )
	{
		this.bath = bath;
	}

	public float getHouseSize()
	{
		return this.houseSize;
	}

	public void setHouseSize( float houseSize )
	{
		this.houseSize = houseSize;
	}

	public float getLandSize()
	{
		return this.landSize;
	}

	public void setLandSize( float landSize )
	{
		this.landSize = landSize;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

	public boolean isParking()
	{
		return this.parking;
	}

	public void setParking( boolean parking )
	{
		this.parking = parking;
	}

	public int getDistanceToMainRd()
	{
		return this.distanceToMainRd;
	}

	public void setDistanceToMainRd( int distanceToMainRd )
	{
		this.distanceToMainRd = distanceToMainRd;
	}

	public int getStatus()
	{
		return this.status;
	}

	public void setStatus( int status )
	{
		this.status = status;
		super.setStatus( status );
	}

}
