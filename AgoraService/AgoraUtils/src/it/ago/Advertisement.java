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
	private String _ownerAddress;
	private String address;
	private boolean isRent;
	private boolean isWantedToBuy;
	private String keyWords;
	private int type_level_1;
	private int type_level_2;
	private int type_level_3;
	private String advDescription;


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
				+ "PRICE, ADDRESS, RENT, WANTED_TO_BUY,KEYWORDS, "
				+ "TYPE_LEVEL_1, TYPE_LEVEL_2, TYPE_LEVEL_3, DESCRIPTION "
				+" )VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
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
		ps.setString( ++count, this.address );
		ps.setBoolean( ++count, this.isRent );
		ps.setBoolean( ++count, this.isWantedToBuy );
		if ( this.keyWords == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.keyWords );
		}
		ps.setInt( ++count, this.type_level_1 );
		ps.setInt( ++count, this.type_level_2 );
		ps.setInt( ++count, this.type_level_3 );
		ps.setString( ++count, this.advDescription );
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
				+ "PRICE = ?, ADDRESS = ?, RENT = ?, WANTED_TO_BUY= ?, KEYWORDS = ?, "
				+ " TYPE_LEVEL_1= ?, TYPE_LEVEL_2= ?, TYPE_LEVEL_3= ?, DESCRIPTION = ? "
				+ " WHERE "
				+ " ADV_ID = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setTimestamp( ++count, this.validFrom );
		ps.setTimestamp( ++count, this.validTo );
		ps.setBoolean( ++count, this.active );
		ps.setTimestamp( ++count, this.createdDate );
		ps.setTimestamp( ++count, this.lastModified );
		ps.setString( ++count, this.productCode );
		//ps.setLong( ++count, this.ownerId ); // owner cannot change
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
		ps.setString( ++count, this.address );
		ps.setBoolean( ++count, this.isRent );
		ps.setBoolean( ++count, this.isWantedToBuy );
		if ( this.keyWords == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.keyWords );
		}
		ps.setInt( ++count, this.type_level_1 );
		ps.setInt( ++count, this.type_level_2 );
		ps.setInt( ++count, this.type_level_3 );
		if ( this.advDescription == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.advDescription );
		}
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
		this.address = rs.getString( "ADDRESS" );
		this.isRent = rs.getBoolean( "RENT" );
		this.isWantedToBuy = rs.getBoolean( "WANTED_TO_BUY" );
		if ( rs.getObject( "KEYWORDS" ) == null )
		{
			this.keyWords = null;
		}
		else
		{
			this.keyWords = rs.getString( "KEYWORDS" );
		}
		this.type_level_1 =  rs.getInt( "TYPE_LEVEL_1" );
		this.type_level_2 =  rs.getInt( "TYPE_LEVEL_2" );
		this.type_level_3 =  rs.getInt( "TYPE_LEVEL_3" );
		this.advDescription = rs.getString( "DESCRIPTION" );

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

	public String getAddress()
	{
		return address;
	}

	public void setAddress( String address )
	{
		this.address = address;
	}

	public String get_ownerAddress()
	{
		return _ownerAddress;
	}

	public void set_ownerAddress( String _ownerAddress )
	{
		this._ownerAddress = _ownerAddress;
	}

	public boolean isRent()
	{
		return isRent;
	}

	public void setRent( boolean rent )
	{
		isRent = rent;
	}

	public boolean isWantedToBuy()
	{
		return isWantedToBuy;
	}

	public void setWantedToBuy( boolean wantedToBuy )
	{
		isWantedToBuy = wantedToBuy;
	}

	public String getKeyWords()
	{
		return keyWords;
	}

	public void setKeyWords( String keyWords )
	{
		this.keyWords = keyWords;
	}


	public int getType_level_1()
	{
		return type_level_1;
	}

	public void setType_level_1( int type_level_1 )
	{
		this.type_level_1 = type_level_1;
	}

	public int getType_level_2()
	{
		return type_level_2;
	}

	public void setType_level_2( int type_level_2 )
	{
		this.type_level_2 = type_level_2;
	}

	public int getType_level_3()
	{
		return type_level_3;
	}

	public void setType_level_3( int type_level_3 )
	{
		this.type_level_3 = type_level_3;
	}

	public String getAdvDescription()
	{
		return advDescription;
	}

	public void setAdvDescription( String advDescription )
	{
		this.advDescription = advDescription;
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
		if( Constants.ADV_PROD_PROPERTY.equalsIgnoreCase( product ) )
		{
			advertisement = new PropertyAvertisement();
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
		try
		{
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
		catch ( Exception e )
		{

		}
		finally
		{
			DBConnection.close( null, ps, rs );
		}
	}
	private void loadOwnerDetails( ResultSet rs ) throws SQLException
	{
			this._ownerName = rs.getString( "NAME" );
			this._ownerTelephone = rs.getString( "TELEPHONE" );
			this._ownerEmail = rs.getString( "EMAIL" );
			this._ownerAddress = rs.getString( "ADDRESS" );
	}
}
