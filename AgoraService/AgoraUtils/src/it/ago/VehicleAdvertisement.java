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

@XmlType(name = "VehicleAdvertisement", namespace = "http://lass")
public class VehicleAdvertisement extends Advertisement
{

	private String v_condition;
	private String v_transmission;
	private String v_bodyType;
	private String v_fuelType;
	private int v_engineCapacity;
	private long v_milage;
	private String v_description;
	private int _status;
	public VehicleAdvertisement()
	{
		this.setStatus( Savable.NEW );
		super.setStatus( Savable.NEW );
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
		super.save( con );
		try
		{
			if ( this._status == Savable.NEW )
			{
				action = "Inserting";
				checkValidity();
				insert( con );
			}
			else if ( this._status == Savable.MODIFIED )
			{
				action = "Updating";
				checkValidity();
				update( con );
			}
			else if ( this._status == Savable.DELETED )
			{
				action = "Deleting";
				checkValidity();
				delete( con );
			}
			else if ( this._status == Savable.UNCHANGED )
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
		String str = "INSERT INTO VEHICLE_ADVERTISEMENT ( "
				+ "ADV_ID, "
//				+ "TYPE_ID, "
//				+ "BRAND_ID, "
//				+ "MODEL_ID, "
//				+ "MODEL_YEAR, "
				+ "V_CONDITION, "
				+ "TRANSMISSION, "
				+ "BODY_TYPE, "
				+ "FUEL_TYPE, "
				+ "ENGINE_CAPACITY, "
				+ "MILAGE, "
				+ "DESCRIPTION )VALUES(?,?,?,?,?,?,?,? )";
		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setLong( ++count, super.getAdvId() );
//		ps.setInt( ++count, this.v_typeId );
//		ps.setInt( ++count, this.v_brandId );
//		ps.setInt( ++count, this.v_modelId );
//		if ( this.v_modelYear == -1 )
//		{
//			ps.setNull( ++count, java.sql.Types.NUMERIC );
//		}
//		else
//		{
//			ps.setInt( ++count, this.v_modelYear );
//		}
		if ( this.v_condition == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.v_condition );
		}
		if ( this.v_transmission == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.v_transmission );
		}
		if ( this.v_bodyType == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.v_bodyType );
		}
		if ( this.v_fuelType == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.v_fuelType );
		}
		if ( this.v_engineCapacity == -1 )
		{
			ps.setNull( ++count, java.sql.Types.NUMERIC );
		}
		else
		{
			ps.setInt( ++count, this.v_engineCapacity );
		}
		if ( this.v_milage == -1 )
		{
			ps.setNull( ++count, java.sql.Types.NUMERIC );
		}
		else
		{
			ps.setLong( ++count, this.v_milage );
		}
		if ( this.v_description == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.v_description );
		}
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This deletes the ........
	 */
	public void delete( Connection con ) throws SQLException
	{
		String str = "DELETE FROM VEHICLE_ADVERTISEMENT WHERE "
				+ "ADV_ID = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setLong( ++count, super.getAdvId() );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This updates the .......
	 */
	public void update( Connection con ) throws SQLException
	{
		String str = "UPDATE VEHICLE_ADVERTISEMENT SET "
//				+ "TYPE_ID = ?, "
//				+ "BRAND_ID = ?, "
//				+ "MODEL_ID = ?, "
//				+ "MODEL_YEAR = ?, "
				+ "V_CONDITION = ?, "
				+ "TRANSMISSION = ?, "
				+ "BODY_TYPE = ?, "
				+ "FUEL_TYPE = ?, "
				+ "ENGINE_CAPACITY = ?, "
				+ "MILAGE = ?, "
				+ "DESCRIPTION = ? WHERE "
				+ "ADV_ID = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
//		ps.setInt( ++count, this.v_typeId );
//		ps.setInt( ++count, this.v_brandId );
//		ps.setInt( ++count, this.v_modelId );
//		if ( this.v_modelYear == -1 )
//		{
//			ps.setNull( ++count, java.sql.Types.NUMERIC );
//		}
//		else
//		{
//			ps.setInt( ++count, this.v_modelYear );
//		}
		if ( this.v_condition == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.v_condition );
		}
		if ( this.v_transmission == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.v_transmission );
		}
		if ( this.v_bodyType == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.v_bodyType );
		}
		if ( this.v_fuelType == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.v_fuelType );
		}
		if ( this.v_engineCapacity == -1 )
		{
			ps.setNull( ++count, java.sql.Types.NUMERIC );
		}
		else
		{
			ps.setInt( ++count, this.v_engineCapacity );
		}
		if ( this.v_milage == -1 )
		{
			ps.setNull( ++count, java.sql.Types.NUMERIC );
		}
		else
		{
			ps.setLong( ++count, this.v_milage );
		}
		if ( this.v_description == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.v_description );
		}
		ps.setLong( ++count, super.getAdvId() );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This loads the .......
	 */
	public void load( ResultSet rs, Connection con, int level ) throws SQLException
	{
		super.load( rs, con, level );
		this._status = Savable.UNCHANGED;
		//this.advId = rs.getLong( "ADV_ID" );
//		this.v_typeId = rs.getInt( "TYPE_ID" );
//		this.v_brandId = rs.getInt( "BRAND_ID" );
//		this.v_modelId = rs.getInt( "MODEL_ID" );
//		if ( rs.getObject( "MODEL_YEAR" ) == null )
//		{
//			this.v_modelYear = -1;
//		}
//		else
//		{
//			this.v_modelYear = rs.getInt( "MODEL_YEAR" );
//		}
		if ( rs.getObject( "V_CONDITION" ) == null )
		{
			this.v_condition = null;
		}
		else
		{
			this.v_condition = rs.getString( "V_CONDITION" );
		}
		if ( rs.getObject( "TRANSMISSION" ) == null )
		{
			this.v_transmission = null;
		}
		else
		{
			this.v_transmission = rs.getString( "TRANSMISSION" );
		}
		if ( rs.getObject( "BODY_TYPE" ) == null )
		{
			this.v_bodyType = null;
		}
		else
		{
			this.v_bodyType = rs.getString( "BODY_TYPE" );
		}
		if ( rs.getObject( "FUEL_TYPE" ) == null )
		{
			this.v_fuelType = null;
		}
		else
		{
			this.v_fuelType = rs.getString( "FUEL_TYPE" );
		}
		if ( rs.getObject( "ENGINE_CAPACITY" ) == null )
		{
			this.v_engineCapacity = -1;
		}
		else
		{
			this.v_engineCapacity = rs.getInt( "ENGINE_CAPACITY" );
		}
		if ( rs.getObject( "MILAGE" ) == null )
		{
			this.v_milage = -1;
		}
		else
		{
			this.v_milage = rs.getLong( "MILAGE" );
		}
		if ( rs.getObject( "DESCRIPTION" ) == null )
		{
			this.v_description = null;
		}
		else
		{
			this.v_description = rs.getString( "DESCRIPTION" );
		}
		this.setStatus( Savable.UNCHANGED );
	}

	//public long getAdvId()
	//{
	//	return this.advId;
	//}

	//public void setAdvId( long advId )
	//{
	//	this.advId = advId;
	//}

//	public int getTypeId()
//	{
//		return this.v_typeId;
//	}

//	public void setTypeId( int typeId )
//	{
//		this.v_typeId = typeId;
//	}

//	public int getBrandId()
//	{
//		return this.v_brandId;
//	}

//	public void setBrandId( int brandId )
//	{
//		this.v_brandId = brandId;
//	}

//	public int getModelId()
//	{
//		return this.v_modelId;
//	}

//	public void setModelId( int modelId )
//	{
//		this.v_modelId = modelId;
//	}

//	public int getModelYear()
//	{
//		return this.v_modelYear;
//	}

//	public void setModelYear( int modelYear )
//	{
//		this.v_modelYear = modelYear;
//	}

	public String getCondition()
	{
		return this.v_condition;
	}

	public void setCondition( String condition )
	{
		this.v_condition = condition;
	}

	public String getTransmission()
	{
		return this.v_transmission;
	}

	public void setTransmission( String transmission )
	{
		this.v_transmission = transmission;
	}

	public String getBodyType()
	{
		return this.v_bodyType;
	}

	public void setBodyType( String bodyType )
	{
		this.v_bodyType = bodyType;
	}

	public String getFuelType()
	{
		return this.v_fuelType;
	}

	public void setFuelType( String fuelType )
	{
		this.v_fuelType = fuelType;
	}

	public int getEngineCapacity()
	{
		return this.v_engineCapacity;
	}

	public void setEngineCapacity( int engineCapacity )
	{
		this.v_engineCapacity = engineCapacity;
	}

	public long getMilage()
	{
		return this.v_milage;
	}

	public void setMilage( long milage )
	{
		this.v_milage = milage;
	}

	public String getDescription()
	{
		return this.v_description;
	}

	public void setDescription( String description )
	{
		this.v_description = description;
	}

	public int getStatus()
	{
		return this._status;
	}

	public void setStatus( int status )
	{
		this._status = status;
	}

	public void loadAll( ResultSet rs, ResultSet rsSuper, Connection con, int level ) throws SQLException
	{
		if ( rsSuper != null )
		{
			super.load( rsSuper,con,level );
		}
		if( rs != null )
		{
			this.load( rs, con, level );
		}
	}
}
