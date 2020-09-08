package it.ago;

import it.ago.utils.DBConnection;
import it.ago.utils.db.Savable;

import javax.xml.bind.annotation.XmlType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@XmlType(name = "VehicleModelx", namespace = "http://lass")
public class VehicleModelx extends Savable
{
	private int modelId;
	private int brandId;
	private String modelName;
	private int status;

	public VehicleModelx()
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
		String str = "INSERT INTO VEHICLE_MODELX ( "
				+ "MODEL_ID, "
				+ "BRAND_ID, "
				+ "MODEL_NAME )VALUES(?,?,? )";
		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setInt( ++count, this.modelId );
		ps.setInt( ++count, this.brandId );
		ps.setString( ++count, this.modelName );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This deletes the ........
	 */
	public void delete( Connection con ) throws SQLException
	{
		String str = "DELETE FROM VEHICLE_MODELX WHERE "
				+ "MODEL_ID = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setInt( ++count, this.modelId );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This updates the .......
	 */
	public void update( Connection con ) throws SQLException
	{
		String str = "UPDATE VEHICLE_MODELX SET "
				+ "BRAND_ID = ?, "
				+ "MODEL_NAME = ? WHERE "
				+ "MODEL_ID = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setInt( ++count, this.brandId );
		ps.setString( ++count, this.modelName );
		ps.setInt( ++count, this.modelId );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This loads the .......
	 */
	public void load( ResultSet rs, Connection con, int level ) throws SQLException
	{
		this.status = Savable.UNCHANGED;
		this.modelId = rs.getInt( "MODEL_ID" );
		this.brandId = rs.getInt( "BRAND_ID" );
		this.modelName = rs.getString( "MODEL_NAME" );

	}

	public int getModelId()
	{
		return this.modelId;
	}

	public void setModelId( int modelId )
	{
		this.modelId = modelId;
	}

	public int getBrandId()
	{
		return this.brandId;
	}

	public void setBrandId( int brandId )
	{
		this.brandId = brandId;
	}

	public String getModelName()
	{
		return this.modelName;
	}

	public void setModelName( String modelName )
	{
		this.modelName = modelName;
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
