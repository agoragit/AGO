package it.ago.user;

import it.ago.AgoError;
import it.ago.Constants;
import it.ago.utils.DBConnection;
import it.ago.utils.db.Savable;

import javax.xml.bind.annotation.XmlType;
import java.sql.*;

@XmlType(name = "Owner", namespace = "http://lass")
public class Owner extends Savable
{
	private long ownerId;
	private String username;
	private String password;
	private String telephone;
	private String email;
	private Timestamp lastLoginTime;
	private boolean active;
	private String address;
	private String name;
	private int status;
	private String type;

	public Owner()
	{
	}

	public void init()
	{
		ownerId = -1;
		username = null;
		password = null;
		telephone = null;
		email = null;
		lastLoginTime = null;
		active = false;
		address = null;
		status = Savable.NEW;
		name = null;
		type = null;
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
		String str = "INSERT INTO OWNER ( "
				+ "OWNER_ID, "
				+ "USERNAME, "
				+ "PASSWORD, "
				+ "TELEPHONE, "
				+ "EMAIL, "
				+ "LAST_LOGIN_TIME, "
				+ "ACTIVE, "
				+ "ADDRESS,NAME,TYPE )VALUES(?,?,?,?,?,?,?,?,?,? )";
		setNextUserId( con );
		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setLong( ++count, this.ownerId );
		ps.setString( ++count, this.username );
		ps.setString( ++count, this.password );
		if ( this.telephone == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.telephone );
		}
		if ( this.email == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.email );
		}
		if ( this.lastLoginTime == null )
		{
			ps.setNull( ++count, java.sql.Types.TIMESTAMP );
		}
		else
		{
			ps.setTimestamp( ++count, this.lastLoginTime );
		}
		if ( this.active == false )
		{
			ps.setNull( ++count, java.sql.Types.CHAR );
		}
		else
		{
			ps.setBoolean( ++count, this.active );
		}
		if ( this.address == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.address );
		}
		if ( this.name == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.name );
		}
		if ( this.type == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.type );
		}
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This deletes the ........
	 */
	public void delete( Connection con ) throws SQLException
	{
		String str = "DELETE FROM OWNER WHERE "
				+ "OWNER_ID = ? AND "
				+ "USERNAME = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setLong( ++count, this.ownerId );
		ps.setString( ++count, this.username );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This updates the .......
	 */
	public void update( Connection con ) throws SQLException
	{
		String str = "UPDATE OWNER SET "
				+ "PASSWORD = ?, "
				+ "TELEPHONE = ?, "
				+ "EMAIL = ?, "
				+ "LAST_LOGIN_TIME = ?, "
				+ "ACTIVE = ?, "
				+ "ADDRESS = ?, NAME = ?, TYPE=? WHERE "
				+ "OWNER_ID = ? AND "
				+ "USERNAME = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setString( ++count, this.password );
		if ( this.telephone == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.telephone );
		}
		if ( this.email == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.email );
		}
		if ( this.lastLoginTime == null )
		{
			ps.setNull( ++count, java.sql.Types.TIMESTAMP );
		}
		else
		{
			ps.setTimestamp( ++count, this.lastLoginTime );
		}
		if ( this.active == false )
		{
			ps.setNull( ++count, java.sql.Types.CHAR );
		}
		else
		{
			ps.setBoolean( ++count, this.active );
		}
		if ( this.address == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.address );
		}
		if ( this.name == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.name );
		}
		if ( this.type == null )
		{
			ps.setNull( ++count, java.sql.Types.VARCHAR );
		}
		else
		{
			ps.setString( ++count, this.type );
		}
		ps.setLong( ++count, this.ownerId );
		ps.setString( ++count, this.username );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This loads the .......
	 */
	public void load( ResultSet rs, Connection con, int level ) throws SQLException
	{
		this.status = Savable.UNCHANGED;
		this.ownerId = rs.getLong( "OWNER_ID" );
		this.username = rs.getString( "USERNAME" );
		this.password = "*****";
		if ( rs.getObject( "TELEPHONE" ) == null )
		{
			this.telephone = null;
		}
		else
		{
			this.telephone = rs.getString( "TELEPHONE" );
		}
		if ( rs.getObject( "EMAIL" ) == null )
		{
			this.email = null;
		}
		else
		{
			this.email = rs.getString( "EMAIL" );
		}
		Object obj = rs.getObject( "LAST_LOGIN_TIME" );
		if ( obj == null )
		{
			this.lastLoginTime = null;
		}
		else
		{
			this.lastLoginTime = ( java.sql.Timestamp ) obj;
		}
		if ( rs.getObject( "ACTIVE" ) == null )
		{
			this.active = false;
		}
		else
		{
			this.active = rs.getBoolean( "ACTIVE" );
		}
		if ( rs.getObject( "ADDRESS" ) == null )
		{
			this.address = null;
		}
		else
		{
			this.address = rs.getString( "ADDRESS" );
		}
		if ( rs.getObject( "NAME" ) == null )
		{
			this.name = null;
		}
		else
		{
			this.name = rs.getString( "NAME" );
		}
		if ( rs.getObject( "TYPE" ) == null )
		{
			this.type = null;
		}
		else
		{
			this.type = rs.getString( "TYPE" );
		}

	}

	public long getOwnerId()
	{
		return this.ownerId;
	}

	public void setOwnerId( long ownerId )
	{
		this.ownerId = ownerId;
	}

	public String getUsername()
	{
		return this.username;
	}

	public void setUsername( String username )
	{
		this.username = username;
	}

	public String getPassword()
	{
		return this.password;
	}

	public void setPassword( String password )
	{
		this.password = password;
	}

	public String getTelephone()
	{
		return this.telephone;
	}

	public void setTelephone( String telephone )
	{
		this.telephone = telephone;
	}

	public String getEmail()
	{
		return this.email;
	}

	public void setEmail( String email )
	{
		this.email = email;
	}

	public Timestamp getLastLoginTime()
	{
		return this.lastLoginTime;
	}

	public void setLastLoginTime( Timestamp lastLoginTime )
	{
		this.lastLoginTime = lastLoginTime;
	}

	public boolean isActive()
	{
		return this.active;
	}

	public void setActive( boolean active )
	{
		this.active = active;
	}

	public String getAddress()
	{
		return this.address;
	}

	public void setAddress( String address )
	{
		this.address = address;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public int getStatus()
	{
		return this.status;
	}

	public void setStatus( int status )
	{
		this.status = status;
	}

	public String getType()
	{
		return type;
	}

	public void setType( String type )
	{
		this.type = type;
	}

	public AgoError _validateUserForSave( Connection con )
	{
		AgoError error = new AgoError( AgoError.SUCCESS, "SUCCESS" );
		String queary = "";
		if ( this.status == Savable.NEW )
		{
			queary = "SELECT * FROM OWNER WHERE EMAIL = ? OR USERNAME = ? ";
			if ( _isUsernameAlreadyExsits( con ) )
			{
				error.setErrorMessage( AgoError.ERROR, "username exsists", true );
				return error;
			}
		}
		else if ( this.status == Savable.MODIFIED )
		{
			if ( this.ownerId <= 0 )
			{
				error.setErrorMessage( AgoError.ERROR, "invalid owner id", true );
				return error;
			}
			queary = "SELECT * FROM OWNER WHERE EMAIL = ? AND USERNAME != ? ";
		}
		else
		{
			return error;
		}
		if ( _isEmailTPAlradyTakenByOtherUser( con ) )
		{
			error.setErrorMessage( AgoError.ERROR, "contact detail belongs to another user", true );
			return error;
		}
		if( this.type == null || this.type.length()==0 || !( Constants.USER_TYPE_CUSTOMER.equals( this.type ) || Constants.USER_TYPE_STORE.equals( this.type ) ))
		{
			error.setErrorMessage( AgoError.ERROR, "Invalid user type", true );
			return error;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			ps = con.prepareStatement( queary );
			ps.setString( 1, this.email );
			ps.setString( 2, this.username );
			rs = ps.executeQuery();
			if ( rs.next() )
			{
				if ( this.status == Savable.NEW )
				{
					error = new AgoError( AgoError.ERROR, "Username or Email already exsists" );
				}
				else
				{
					error = new AgoError( AgoError.ERROR, "Email already exsists" );
				}
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
		return error;
	}

	public boolean _isEmailTPAlradyTakenByOtherUser( Connection con )
	{
		String queary = "SELECT * FROM OWNER WHERE ( EMAIL = ? || TELEPHONE = ? ) AND USERNAME != ? ";

		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			ps = con.prepareStatement( queary );
			ps.setString( 1, this.email );
			ps.setString( 2, this.telephone );
			ps.setString( 3, this.username );
			rs = ps.executeQuery();
			if ( rs.next() )
			{
				return true;
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

	public boolean _isUsernameAlreadyExsits( Connection con )
	{
		String queary = "SELECT * FROM OWNER WHERE USERNAME = ? ";

		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			ps = con.prepareStatement( queary );
			ps.setString( 1, this.username );
			rs = ps.executeQuery();
			if ( rs.next() )
			{
				return true;
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

	public boolean setNextUserId( Connection con )
	{
		String queary = "SELECT (MAX(OWNER_ID) + 1) AS ID FROM OWNER";

		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			ps = con.prepareStatement( queary );
			rs = ps.executeQuery();
			if ( rs.next() )
			{
				this.ownerId = rs.getLong( "ID" );
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

	public boolean _isActive( Connection con )
	{
		String queary = "SELECT ACTIVE FROM OWNER WHERE OWNER_ID = ?";

		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			ps = con.prepareStatement( queary );
			ps.setLong( 1, this.ownerId );
			rs = ps.executeQuery();
			if ( rs.next() )
			{
				return rs.getBoolean( "ACTIVE" );
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

	public void updateLoginTime( Connection con ) throws SQLException
	{
		String str = "UPDATE OWNER SET "
				+ "LAST_LOGIN_TIME = ? WHERE "
				+ "OWNER_ID = ? AND "
				+ "USERNAME = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		if ( this.lastLoginTime == null )
		{
			ps.setNull( ++count, java.sql.Types.TIMESTAMP );
		}
		else
		{
			ps.setTimestamp( ++count, this.lastLoginTime );
		}
		ps.setLong( ++count, this.ownerId );
		ps.setString( ++count, this.username );
		ps.execute();
		DBConnection.close( ps );
	}
}
