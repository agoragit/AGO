package it.ago;

import it.ago.utils.DBConnection;
import it.ago.utils.db.Savable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleType extends Savable
{
	private int typeId;
	private String typeName;
	private int status;
	private List<VehicleBrand> vehicleBrands;

	public VehicleType()
	{
		vehicleBrands = new ArrayList<>(  );
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
		String str = "INSERT INTO VEHICLE_TYPE ( "
				+ "TYPE_ID, "
				+ "TYPE_NAME )VALUES(?,? )";
		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setInt( ++count, this.typeId );
		ps.setString( ++count, this.typeName );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This deletes the ........
	 */
	public void delete( Connection con ) throws SQLException
	{
		String str = "DELETE FROM VEHICLE_TYPE WHERE "
				+ "TYPE_ID = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setInt( ++count, this.typeId );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This updates the .......
	 */
	public void update( Connection con ) throws SQLException
	{
		String str = "UPDATE VEHICLE_TYPE SET "
				+ "TYPE_NAME = ? WHERE "
				+ "TYPE_ID = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setString( ++count, this.typeName );
		ps.setInt( ++count, this.typeId );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This loads the .......
	 */
	public void load( ResultSet rs, Connection con, int level ) throws SQLException
	{
		this.status = Savable.UNCHANGED;
		this.typeId = rs.getInt( "TYPE_ID" );
		this.typeName = rs.getString( "TYPE_NAME" );

		if( level > 0 )
		{
			loadBrands( con,level );
		}

	}

	private void loadBrands( Connection con, int level )
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			ps = con.prepareStatement( "SELECT * FROM VEHICLE_BRAND WHERE TYPE_ID = ?");
			ps.setInt( 1, this.typeId );
			rs = ps.executeQuery();
			while (rs.next())
			{
				VehicleBrand vehicleBrand = new VehicleBrand();
				vehicleBrand.load(rs, con, level);
				vehicleBrands.add(vehicleBrand);
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
	public int getTypeId()
	{
		return this.typeId;
	}

	public void setTypeId( int typeId )
	{
		this.typeId = typeId;
	}

	public String getTypeName()
	{
		return this.typeName;
	}

	public void setTypeName( String typeName )
	{
		this.typeName = typeName;
	}

	public int getStatus()
	{
		return this.status;
	}

	public void setStatus( int status )
	{
		this.status = status;
	}

	public List<VehicleBrand> getVehicleBrands()
	{
		return vehicleBrands;
	}

	public void setVehicleBrands( List<VehicleBrand> vehicleBrands )
	{
		this.vehicleBrands = vehicleBrands;
	}
}
