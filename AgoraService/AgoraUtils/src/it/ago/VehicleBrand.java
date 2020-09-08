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

@XmlType(name = "VehicleBrand", namespace = "http://lass")
public class VehicleBrand extends Savable
{
	private int brandId;
	private String brandName;
	private int typeId;
	private int status;
	private List<VehicleModelx> vehicleModelxes;
	public VehicleBrand()
	{
		vehicleModelxes = new ArrayList<>(  );
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
		String str = "INSERT INTO VEHICLE_BRAND ( "
				+ "BRAND_ID, "
				+ "BRAND_NAME, "
				+ "TYPE_ID )VALUES(?,?,? )";
		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		if ( this.brandId == -1 )
		{
			ps.setNull( ++count, java.sql.Types.NUMERIC );
		}
		else
		{
			ps.setInt( ++count, this.brandId );
		}
		ps.setString( ++count, this.brandName );
		if ( this.typeId == -1 )
		{
			ps.setNull( ++count, java.sql.Types.NUMERIC );
		}
		else
		{
			ps.setInt( ++count, this.typeId );
		}
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This deletes the ........
	 */
	public void delete( Connection con ) throws SQLException
	{
		String str = "DELETE FROM VEHICLE_BRAND WHERE "
				+ "BRAND_ID = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setInt( ++count, this.brandId );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This updates the .......
	 */
	public void update( Connection con ) throws SQLException
	{
		String str = "UPDATE VEHICLE_BRAND SET "
				+ "BRAND_NAME = ?, "
				+ "TYPE_ID = ? WHERE "
				+ "BRAND_ID = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setString( ++count, this.brandName );
		if ( this.typeId == -1 )
		{
			ps.setNull( ++count, java.sql.Types.NUMERIC );
		}
		else
		{
			ps.setInt( ++count, this.typeId );
		}
		ps.setInt( ++count, this.brandId );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This loads the .......
	 */
	public void load( ResultSet rs, Connection con, int level ) throws SQLException
	{
		this.status = Savable.UNCHANGED;
		if ( rs.getObject( "BRAND_ID" ) == null )
		{
			this.brandId = -1;
		}
		else
		{
			this.brandId = rs.getInt( "BRAND_ID" );
		}
		this.brandName = rs.getString( "BRAND_NAME" );
		if ( rs.getObject( "TYPE_ID" ) == null )
		{
			this.typeId = -1;
		}
		else
		{
			this.typeId = rs.getInt( "TYPE_ID" );
		}

		if ( level > 0)
		{
			loadModels( con, level );
		}

	}

	private void loadModels( Connection con, int level )
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			ps = con.prepareStatement( "SELECT * FROM VEHICLE_MODELX WHERE BRAND_ID = ?");
			ps.setInt( 1, this.brandId );
			rs = ps.executeQuery();
			while (rs.next())
			{
				VehicleModelx vehicleModelx = new VehicleModelx();
				vehicleModelx.load(rs, con, level);
				this.vehicleModelxes.add(vehicleModelx);
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
	public int getBrandId()
	{
		return this.brandId;
	}

	public void setBrandId( int brandId )
	{
		this.brandId = brandId;
	}

	public String getBrandName()
	{
		return this.brandName;
	}

	public void setBrandName( String brandName )
	{
		this.brandName = brandName;
	}

	public int getTypeId()
	{
		return this.typeId;
	}

	public void setTypeId( int typeId )
	{
		this.typeId = typeId;
	}

	public int getStatus()
	{
		return this.status;
	}

	public void setStatus( int status )
	{
		this.status = status;
	}

	public List<VehicleModelx> getVehicleModelxes()
	{
		return vehicleModelxes;
	}

	public void setVehicleModelxes( List<VehicleModelx> vehicleModelxes )
	{
		this.vehicleModelxes = vehicleModelxes;
	}
}
