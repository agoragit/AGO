package it.ago;

import it.ago.adv.DBQuearies;
import it.ago.system.SystemConfig;
import it.ago.utils.DBConnection;
import it.ago.utils.db.Savable;

import javax.xml.bind.annotation.XmlType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "Advertisement", namespace = "http://lass")
public abstract class Advertisement extends Savable
{
	private long advId;
	private Timestamp validFrom;
	private Timestamp validTo;
	private boolean active;
	private Timestamp createdDate;
	private Timestamp lastModified;
	private String productCode;
	private long ownerId;
	private String longtute;
	private String latitude;
	private String cityCode;
	private double price;
	private int status;
	private List<AdvImage> advImages;
	private String _ownerName;
	private String _ownerEmail;
	private String _ownerTelephone;
	private String _address;



	public Advertisement()
	{
		advImages = new ArrayList<>(  );
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

		if( this.status == Savable.NEW )
		{
			for( AdvImage advImage : this.advImages )
			{
				advImage.setStatus( Savable.NEW );
				advImage.setAdvId( advId );
				//Image path should be change in both places -> inside the advrtersement and main saving method
				advImage.setImageUrl( this.productCode+"/"+this.advId+"/"+advImage.getImageUrl() );
				advImage.save( con );
				advImage.setImageUrl( SystemConfig.ADV_ROOT_URL+SystemConfig.ADV_IMAGE_UPLOAD_PATH.replace( "\\", "/" )+advImage.getImageUrl() );

			}
		}
	}

	/**
	 * This inserts the .........
	 */
	private void insert( Connection con ) throws SQLException
	{
		String str = "INSERT INTO AGO_ADVERTISEMENT ( "
				+ "ADV_ID, "
				+ "VALID_FROM, "
				+ "VALID_TO, "
				+ "ACTIVE, "
				+ "CREATED_DATE, "
				+ "LAST_MODIFIED, "
				+ "PRODUCT_CODE, "
				+ "OWNER_ID, "
				+ "LONGTUTE, "
				+ "LATITUDE, "
				+ "CITY_CODE, "
				+ "PRICE )VALUES(?,?,?,?,?,?,?,?,?,?,?,? )";
		int count = 0;
		setNextAdvId( con );
		PreparedStatement ps = con.prepareStatement( str );
		ps.setLong( ++count, this.advId );
		ps.setTimestamp( ++count, this.validFrom );
		ps.setTimestamp( ++count, this.validTo );
		ps.setBoolean( ++count, this.active );
		ps.setTimestamp( ++count, this.createdDate );
		ps.setTimestamp( ++count, this.lastModified );
		ps.setString( ++count, this.productCode );
		ps.setLong( ++count, this.ownerId );
		if ( this.longtute == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.longtute );
		}
		if ( this.latitude == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.latitude );
		}
		ps.setString( ++count, this.cityCode );
		ps.setDouble( ++count, this.price );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This deletes the ........
	 */
	public void delete( Connection con ) throws SQLException
	{
		String str = "DELETE FROM AGO_ADVERTISEMENT WHERE "
				+ "ADV_ID = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setLong( ++count, this.advId );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This updates the .......
	 */
	public void update( Connection con ) throws SQLException
	{
		String str = "UPDATE AGO_ADVERTISEMENT SET "
				+ "VALID_FROM = ?, "
				+ "VALID_TO = ?, "
				+ "ACTIVE = ?, "
				+ "CREATED_DATE = ?, "
				+ "LAST_MODIFIED = ?, "
				+ "PRODUCT_CODE = ?, "
				+ "OWNER_ID = ?, "
				+ "LONGTUTE = ?, "
				+ "LATITUDE = ?, "
				+ "CITY_CODE = ?, "
				+ "PRICE = ? WHERE "
				+ "ADV_ID = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setTimestamp( ++count, this.validFrom );
		ps.setTimestamp( ++count, this.validTo );
		ps.setBoolean( ++count, this.active );
		ps.setTimestamp( ++count, this.createdDate );
		ps.setTimestamp( ++count, this.lastModified );
		ps.setString( ++count, this.productCode );
		ps.setLong( ++count, this.ownerId );
		if ( this.longtute == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.longtute );
		}
		if ( this.latitude == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.latitude );
		}
		ps.setString( ++count, this.cityCode );
		ps.setDouble( ++count, this.price );
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
		this.advId = rs.getLong( "ADV_ID" );
		this.validFrom = rs.getTimestamp( "VALID_FROM" );
		this.validTo = rs.getTimestamp( "VALID_TO" );
		this.active = rs.getBoolean( "ACTIVE" );
		this.createdDate = rs.getTimestamp( "CREATED_DATE" );
		this.lastModified = rs.getTimestamp( "LAST_MODIFIED" );
		this.productCode = rs.getString( "PRODUCT_CODE" );
		this.ownerId = rs.getLong( "OWNER_ID" );
		if ( rs.getObject( "LONGTUTE" ) == null )
		{
			this.longtute = null;
		}
		else
		{
			this.longtute = rs.getString( "LONGTUTE" );
		}
		if ( rs.getObject( "LATITUDE" ) == null )
		{
			this.latitude = null;
		}
		else
		{
			this.latitude = rs.getString( "LATITUDE" );
		}
		this.cityCode = rs.getString( "CITY_CODE" );
		this.price = rs.getDouble( "PRICE" );
		this.status  =Savable.UNCHANGED;
		loadImages(con);
		if( level > 0 )
		{
			loadOwnerDetails( rs );
		}
	}

	public long getAdvId()
	{
		return this.advId;
	}

	public void setAdvId( long advId )
	{
		this.advId = advId;
	}

	public Timestamp getValidFrom()
	{
		return this.validFrom;
	}

	public void setValidFrom( Timestamp validFrom )
	{
		this.validFrom = validFrom;
	}

	public Timestamp getValidTo()
	{
		return this.validTo;
	}

	public void setValidTo( Timestamp validTo )
	{
		this.validTo = validTo;
	}

	public boolean isActive()
	{
		return this.active;
	}

	public void setActive( boolean active )
	{
		this.active = active;
	}

	public Timestamp getCreatedDate()
	{
		return this.createdDate;
	}

	public void setCreatedDate( Timestamp createdDate )
	{
		this.createdDate = createdDate;
	}

	public Timestamp getLastModified()
	{
		return this.lastModified;
	}

	public void setLastModified( Timestamp lastModified )
	{
		this.lastModified = lastModified;
	}

	public String getProductCode()
	{
		return this.productCode;
	}

	public void setProductCode( String productCode )
	{
		this.productCode = productCode;
	}

	public long getOwnerId()
	{
		return this.ownerId;
	}

	public void setOwnerId( long ownerId )
	{
		this.ownerId = ownerId;
	}

	public String getLongtute()
	{
		return this.longtute;
	}

	public void setLongtute( String longtute )
	{
		this.longtute = longtute;
	}

	public String getLatitude()
	{
		return this.latitude;
	}

	public void setLatitude( String latitude )
	{
		this.latitude = latitude;
	}

	public String getCityCode()
	{
		return this.cityCode;
	}

	public void setCityCode( String cityCode )
	{
		this.cityCode = cityCode;
	}

	public double getPrice()
	{
		return this.price;
	}

	public void setPrice( double price )
	{
		this.price = price;
	}
	public List<AdvImage> getAdvImages()
	{
		return advImages;
	}

	public void setAdvImages( List<AdvImage> advImages )
	{
		this.advImages = advImages;
	}

	public int getStatus()
	{
		return this.status;
	}

	public void setStatus( int status )
	{
		this.status = status;
	}

	public String get_ownerName()
	{
		return _ownerName;
	}

	public void set_ownerName( String _ownerName )
	{
		this._ownerName = _ownerName;
	}

	public String get_ownerEmail()
	{
		return _ownerEmail;
	}

	public void set_ownerEmail( String _ownerEmail )
	{
		this._ownerEmail = _ownerEmail;
	}

	public String get_ownerTelephone()
	{
		return _ownerTelephone;
	}

	public void set_ownerTelephone( String _ownerTelephone )
	{
		this._ownerTelephone = _ownerTelephone;
	}

	public String get_address()
	{
		return _address;
	}

	public void set_address( String _address )
	{
		this._address = _address;
	}

	public void loadAll( ResultSet rs, ResultSet rsSuper, Connection con, int level ) throws SQLException
	{
		this.load( rsSuper,con,level );
	}
	public boolean setNextAdvId( Connection con )
	{
		String queary = "SELECT (MAX(ADV_ID) + 1) AS ID FROM AGO_ADVERTISEMENT";

		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			ps = con.prepareStatement( queary );
			rs = ps.executeQuery();
			if ( rs.next() )
			{
				this.advId = rs.getLong( "ID" );
			}
		}
		catch ( SQLException e )
		{
			e.printStackTrace();
		}
		finally
		{
			DBConnection.close( rs );
			DBConnection.close( ps );
		}
		return false;
	}

	public static Advertisement getInstance( ResultSet rsSuper, Connection con, int level ) throws SQLException
	{
		String product = rsSuper.getString( "PRODUCT_CODE" );
		Advertisement advertisement = null;
		if( Constants.ADV_PROD_VEHICLE.equalsIgnoreCase( product ) )
		{
			advertisement = new VehicleAdvertisement();
			advertisement.load( rsSuper, con, level );
		}
		return advertisement;
	}
	private static ResultSet getResultSet( String product, Connection con, long AdvId )
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			if( Constants.ADV_PROD_VEHICLE.equalsIgnoreCase( product ))
			{
				ps = con.prepareStatement( DBQuearies.Q_SEARCH_VEHI_ADV_BY_ADV_ID );
				ps.setLong( 1, AdvId );
				rs = ps.executeQuery();
			}
		}
		catch ( Exception e )
		{
		}
		return rs;
	}
	private void loadImages( Connection con ) throws SQLException
	{
		PreparedStatement ps = null;
		ResultSet rs  = null;
		ps = con.prepareStatement( "SELECT * FROM ADV_IMAGE WHERE ADV_ID = ?" );
		ps.setLong( 1, this.getAdvId() );
		rs = ps.executeQuery();
		while ( rs.next() )
		{
			AdvImage advImage = new AdvImage();
			advImage.load( rs, con, 1 );
			this.advImages.add( advImage );
		}
	}
	private void loadOwnerDetails( ResultSet rs ) throws SQLException
	{
			this._ownerName = rs.getString( "NAME" );
			this._ownerTelephone = rs.getString( "TELEPHONE" );
			this._ownerEmail = rs.getString( "EMAIL" );
			this._address = rs.getString( "ADDRESS" );
	}
}
