package it.ago;

import it.ago.system.SystemConfig;
import it.ago.utils.DBConnection;
import it.ago.utils.db.Savable;

import java.sql.*;
import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class AgoSession
{
	public static final String SESSION_TYPE_USER = "USR";
	private static final String sessionLoadQ = "SELECT * FROM AGO_SESSION WHERE SESSION_ID = ? AND CREATE_TIME >= ? AND VALID = '1' ";
	private String sessionId;
	private long userId;
	private boolean valid;
	private Timestamp createTime;
	private String type;
	private int status;

	private AgoSession()
	{
		this.valid = false;
		this.sessionId = "invalid";
	}

	public static AgoSession createSession( long userId, String type )
	{
		AgoSession agoSession = new AgoSession();
		agoSession.setSessionId( UUID.nameUUIDFromBytes( Calendar.getInstance().toString().getBytes() ).toString() );
		agoSession.setUserId( userId );
		agoSession.setValid( true );
		agoSession.setCreateTime( new Timestamp( System.currentTimeMillis() ) );
		agoSession.setType( type );
		AgoSessionCache.putSession( agoSession );
		agoSession.status = Savable.NEW;
		Connection con = null;
		try
		{
			con = DBConnection.getConnection( DBConnection.MYSQL_CONNECTION_TYPE );
			agoSession.save( con );
		}
		catch ( SQLException e )
		{
			agoSession.setValid( false );
			return agoSession;
		}
		finally
		{
			DBConnection.close( con );
		}
		return agoSession;
	}

	public static AgoSession loadSession( String sessionId )
	{
		AgoSession agoSession = AgoSessionCache.getSession( sessionId );
		if ( agoSession == null )
		{
			agoSession = getSessionFromDb( sessionId );
			if( agoSession.isValid() )
			{
				AgoSessionCache.putSession( agoSession );
			}
		}
		return agoSession;
	}

	public static AgoSession getSessionFromDb( String sessionId )
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		AgoSession agoSession = new AgoSession();
		try
		{
			con = DBConnection.getConnection( DBConnection.MYSQL_CONNECTION_TYPE );
			ps = con.prepareStatement( sessionLoadQ );
			ps.setString( 1, sessionId );
			ps.setTimestamp( 2, new Timestamp( System.currentTimeMillis() - TimeUnit.MINUTES.toMillis( SystemConfig.AGO_SESSION_TIME_OUT )) );
			rs = ps.executeQuery();
			if ( rs.next() )
			{
				agoSession.load( rs );
				return agoSession;
			}
		}
		catch ( SQLException e )
		{

		}
		finally
		{
			DBConnection.close( con, ps, rs );
		}
		return agoSession;
	}

	public static boolean _isValidSession( String sessionId )
	{
		return loadSession( sessionId ).isValid();
	}
	public String getSessionId()
	{
		return sessionId;
	}

	public void setSessionId( String sessionId )
	{
		this.sessionId = sessionId;
	}

	public long getUserId()
	{
		return userId;
	}

	public void setUserId( long userId )
	{
		this.userId = userId;
	}

	public boolean isValid()
	{
		return valid;
	}

	public void setValid( boolean valid )
	{
		this.valid = valid;
	}

	public Timestamp getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime( Timestamp createTime )
	{
		this.createTime = createTime;
	}

	public int getStatus()
	{
		return status;
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
				insert( con );
			}
			else if ( this.status == Savable.MODIFIED )
			{
				action = "Updating";
				update( con );
			}
			else if ( this.status == Savable.DELETED )
			{
				action = "Deleting";
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
		String str = "INSERT INTO AGO_SESSION ( "
				+ "USER_ID, "
				+ "VALID, "
				+ "TYPE, "
				+ "SESSION_ID, "
				+ "CREATE_TIME )VALUES(?,?,?,?,? )";
		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setLong( ++count, this.userId );
		if ( this.valid == false )
		{
			ps.setNull( ++count, java.sql.Types.CHAR );
		}
		else
		{
			ps.setBoolean( ++count, this.valid );
		}
		ps.setString( ++count, this.type );
		ps.setString( ++count, this.sessionId );
		ps.setTimestamp( ++count, this.createTime );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This deletes the ........
	 */
	private void delete( Connection con ) throws SQLException
	{
		String str = "DELETE FROM AGO_SESSION WHERE "
				+ "SESSION_ID = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setString( ++count, this.sessionId );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This updates the .......
	 */
	private void update( Connection con ) throws SQLException
	{
		String str = "UPDATE AGO_SESSION SET "
				+ "USER_ID = ?, "
				+ "VALID = ?, "
				+ "TYPE = ?, "
				+ "CREATE_TIME = ? WHERE "
				+ "SESSION_ID = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setLong( ++count, this.userId );
		if ( this.valid == false )
		{
			ps.setNull( ++count, java.sql.Types.CHAR );
		}
		else
		{
			ps.setBoolean( ++count, this.valid );
		}
		ps.setString( ++count, this.type );
		ps.setTimestamp( ++count, this.createTime );
		ps.setString( ++count, this.sessionId );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This loads the .......
	 */
	public void load( ResultSet rs ) throws SQLException
	{
		this.status = Savable.UNCHANGED;
		this.userId = rs.getLong( "USER_ID" );
		if ( rs.getObject( "VALID" ) == null )
		{
			this.valid = false;
		}
		else
		{
			this.valid = rs.getBoolean( "VALID" );
		}
		this.type = rs.getString( "TYPE" );
		this.sessionId = rs.getString( "SESSION_ID" );
		this.createTime = rs.getTimestamp( "CREATE_TIME" );

	}

}
