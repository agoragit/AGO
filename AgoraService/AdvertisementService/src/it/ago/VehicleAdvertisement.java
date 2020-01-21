package it.ago;

import it.ago.utils.DBConnection;
import it.ago.utils.db.Savable;

import javax.xml.bind.annotation.XmlType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@XmlType(name = "VehicleAdvertisement", namespace = "http://lass")
public class VehicleAdvertisement extends Advertisement
{
	//private long advId;
	private int typeId;
	private int brandId;
	private int modelId;
	private int modelYear;
	private String condition;
	private String transmission;
	private String bodyType;
	private String fuelType;
	private int engineCapacity;
	private long milage;
	private String description;
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
				+ "TYPE_ID, "
				+ "BRAND_ID, "
				+ "MODEL_ID, "
				+ "MODEL_YEAR, "
				+ "V_CONDITION, "
				+ "TRANSMISSION, "
				+ "BODY_TYPE, "
				+ "FUEL_TYPE, "
				+ "ENGINE_CAPACITY, "
				+ "MILAGE, "
				+ "DESCRIPTION )VALUES(?,?,?,?,?,?,?,?,?,?,?,? )";
		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setLong( ++count, super.getAdvId() );
		ps.setInt( ++count, this.typeId );
		ps.setInt( ++count, this.brandId );
		ps.setInt( ++count, this.modelId );
		if ( this.modelYear == -1 )
		{
			ps.setNull( ++count, java.sql.Types.NUMERIC );
		}
		else
		{
			ps.setInt( ++count, this.modelYear );
		}
		if ( this.condition == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.condition );
		}
		if ( this.transmission == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.transmission );
		}
		if ( this.bodyType == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.bodyType );
		}
		if ( this.fuelType == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.fuelType );
		}
		if ( this.engineCapacity == -1 )
		{
			ps.setNull( ++count, java.sql.Types.NUMERIC );
		}
		else
		{
			ps.setInt( ++count, this.engineCapacity );
		}
		if ( this.milage == -1 )
		{
			ps.setNull( ++count, java.sql.Types.NUMERIC );
		}
		else
		{
			ps.setLong( ++count, this.milage );
		}
		if ( this.description == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.description );
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
				+ "TYPE_ID = ?, "
				+ "BRAND_ID = ?, "
				+ "MODEL_ID = ?, "
				+ "MODEL_YEAR = ?, "
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
		ps.setInt( ++count, this.typeId );
		ps.setInt( ++count, this.brandId );
		ps.setInt( ++count, this.modelId );
		if ( this.modelYear == -1 )
		{
			ps.setNull( ++count, java.sql.Types.NUMERIC );
		}
		else
		{
			ps.setInt( ++count, this.modelYear );
		}
		if ( this.condition == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.condition );
		}
		if ( this.transmission == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.transmission );
		}
		if ( this.bodyType == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.bodyType );
		}
		if ( this.fuelType == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.fuelType );
		}
		if ( this.engineCapacity == -1 )
		{
			ps.setNull( ++count, java.sql.Types.NUMERIC );
		}
		else
		{
			ps.setInt( ++count, this.engineCapacity );
		}
		if ( this.milage == -1 )
		{
			ps.setNull( ++count, java.sql.Types.NUMERIC );
		}
		else
		{
			ps.setLong( ++count, this.milage );
		}
		if ( this.description == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.description );
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
		this._status = Savable.UNCHANGED;
		//this.advId = rs.getLong( "ADV_ID" );
		this.typeId = rs.getInt( "TYPE_ID" );
		this.brandId = rs.getInt( "BRAND_ID" );
		this.modelId = rs.getInt( "MODEL_ID" );
		if ( rs.getObject( "MODEL_YEAR" ) == null )
		{
			this.modelYear = -1;
		}
		else
		{
			this.modelYear = rs.getInt( "MODEL_YEAR" );
		}
		if ( rs.getObject( "V_CONDITION" ) == null )
		{
			this.condition = null;
		}
		else
		{
			this.condition = rs.getString( "V_CONDITION" );
		}
		if ( rs.getObject( "TRANSMISSION" ) == null )
		{
			this.transmission = null;
		}
		else
		{
			this.transmission = rs.getString( "TRANSMISSION" );
		}
		if ( rs.getObject( "BODY_TYPE" ) == null )
		{
			this.bodyType = null;
		}
		else
		{
			this.bodyType = rs.getString( "BODY_TYPE" );
		}
		if ( rs.getObject( "FUEL_TYPE" ) == null )
		{
			this.fuelType = null;
		}
		else
		{
			this.fuelType = rs.getString( "FUEL_TYPE" );
		}
		if ( rs.getObject( "ENGINE_CAPACITY" ) == null )
		{
			this.engineCapacity = -1;
		}
		else
		{
			this.engineCapacity = rs.getInt( "ENGINE_CAPACITY" );
		}
		if ( rs.getObject( "MILAGE" ) == null )
		{
			this.milage = -1;
		}
		else
		{
			this.milage = rs.getLong( "MILAGE" );
		}
		if ( rs.getObject( "DESCRIPTION" ) == null )
		{
			this.description = null;
		}
		else
		{
			this.description = rs.getString( "DESCRIPTION" );
		}

	}

	//public long getAdvId()
	//{
	//	return this.advId;
	//}

	//public void setAdvId( long advId )
	//{
	//	this.advId = advId;
	//}

	public int getTypeId()
	{
		return this.typeId;
	}

	public void setTypeId( int typeId )
	{
		this.typeId = typeId;
	}

	public int getBrandId()
	{
		return this.brandId;
	}

	public void setBrandId( int brandId )
	{
		this.brandId = brandId;
	}

	public int getModelId()
	{
		return this.modelId;
	}

	public void setModelId( int modelId )
	{
		this.modelId = modelId;
	}

	public int getModelYear()
	{
		return this.modelYear;
	}

	public void setModelYear( int modelYear )
	{
		this.modelYear = modelYear;
	}

	public String getCondition()
	{
		return this.condition;
	}

	public void setCondition( String condition )
	{
		this.condition = condition;
	}

	public String getTransmission()
	{
		return this.transmission;
	}

	public void setTransmission( String transmission )
	{
		this.transmission = transmission;
	}

	public String getBodyType()
	{
		return this.bodyType;
	}

	public void setBodyType( String bodyType )
	{
		this.bodyType = bodyType;
	}

	public String getFuelType()
	{
		return this.fuelType;
	}

	public void setFuelType( String fuelType )
	{
		this.fuelType = fuelType;
	}

	public int getEngineCapacity()
	{
		return this.engineCapacity;
	}

	public void setEngineCapacity( int engineCapacity )
	{
		this.engineCapacity = engineCapacity;
	}

	public long getMilage()
	{
		return this.milage;
	}

	public void setMilage( long milage )
	{
		this.milage = milage;
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
