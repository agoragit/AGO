package it.ago;

import it.ago.utils.DBConnection;
import it.ago.utils.db.Savable;

import javax.xml.bind.annotation.XmlType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@XmlType(name = "AdvImage", namespace = "http://lass")
public class AdvImage extends Savable
{
	private long imageId;
	private long advId;
	private String imageUrl;
	private int status;

	public AdvImage()
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
		String str = "INSERT INTO ADV_IMAGE ( "
				+ "IMAGE_ID, "
				+ "ADV_ID, "
				+ "IMAGE_URL )VALUES(?,?,? )";
		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setLong( ++count, this.imageId );
		ps.setLong( ++count, this.advId );
		ps.setString( ++count, this.imageUrl );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This deletes the ........
	 */
	public void delete( Connection con ) throws SQLException
	{
		String str = "DELETE FROM ADV_IMAGE WHERE "
				+ "IMAGE_ID = ? AND "
				+ "ADV_ID = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setLong( ++count, this.imageId );
		ps.setLong( ++count, this.advId );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This updates the .......
	 */
	public void update( Connection con ) throws SQLException
	{
		String str = "UPDATE ADV_IMAGE SET "
				+ "IMAGE_URL = ? WHERE "
				+ "IMAGE_ID = ? AND "
				+ "ADV_ID = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setString( ++count, this.imageUrl );
		ps.setLong( ++count, this.imageId );
		ps.setLong( ++count, this.advId );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This loads the .......
	 */
	public void load( ResultSet rs, Connection con, int level ) throws SQLException
	{
		this.status = Savable.UNCHANGED;
		this.imageId = rs.getLong( "IMAGE_ID" );
		this.advId = rs.getLong( "ADV_ID" );
		this.imageUrl = rs.getString( "IMAGE_URL" );
		this.status  =Savable.UNCHANGED;

	}

	public long getImageId()
	{
		return this.imageId;
	}

	public void setImageId( long imageId )
	{
		this.imageId = imageId;
	}

	public long getAdvId()
	{
		return this.advId;
	}

	public void setAdvId( long advId )
	{
		this.advId = advId;
	}

	public String getImageUrl()
	{
		return this.imageUrl;
	}

	public void setImageUrl( String imageUrl )
	{
		this.imageUrl = imageUrl;
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
