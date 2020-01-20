package it.ago.user;

import it.ago.AgoError;
import it.ago.AgoSession;
import it.ago.AgoSessionCache;
import it.ago.utils.DBConnection;
import it.ago.utils.db.Savable;

import javax.ws.rs.core.UriInfo;
import java.sql.*;

public class UserUtils
{
	private UserUtils()
	{

	}

	public static Owner getOwner( UriInfo userInfo, int status )
	{
		Owner owner = new Owner();
		owner.init();
		owner.setUsername( userInfo.getQueryParameters().getFirst( "username" ) );
		owner.setPassword( userInfo.getQueryParameters().getFirst( "password" ) );
		owner.setAddress( userInfo.getQueryParameters().getFirst( "address" ) );
		owner.setEmail( userInfo.getQueryParameters().getFirst( "email" ) );
		owner.setTelephone( userInfo.getQueryParameters().getFirst( "telephone" ) );
		if ( status != Savable.NEW )
		{
			String ownerId = userInfo.getQueryParameters().getFirst( "ownerid" );
			owner.setOwnerId( ( ownerId != null && ownerId.length() > 0 ) ? Long.parseLong( ownerId ) : -1 );
		}
		owner.setStatus( status );
		return owner;
	}

	public static AgoError userAuthorizeEx( UriInfo uriInfo, String username, Connection con )
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		AgoError agoError = new AgoError( AgoError.ERROR, "Login failed" );
		String pw = uriInfo.getQueryParameters().getFirst( "password" );
		String un = uriInfo.getQueryParameters().getFirst( "username" );
		if ( pw == null || pw.length() == 0 || un == null || un.length() == 0 || !un.equals( username ) )
		{
			agoError.setErrorMessage( AgoError.ERROR, "incorrect password/username", true );
			return agoError;
		}
		AgoSession agoSession = null;
		Owner owner = null;
		try
		{
			String queary = "SELECT * FROM OWNER WHERE ( USERNAME = ? OR EMAIL = ? OR TELEPHONE = ? ) AND PASSWORD = ? AND ACTIVE = '1'";
			ps = con.prepareStatement( queary );
			ps.setString( 1, un );
			ps.setString( 2, un );
			ps.setString( 3, un );
			ps.setString( 4, pw );
			rs = ps.executeQuery();
			if ( rs.next() )
			{
				owner = new Owner();
				owner.load( rs, con, 1 );
				agoSession = AgoSession.createSession( rs.getLong( "OWNER_ID" ), AgoSession.SESSION_TYPE_USER );
				if ( agoSession.isValid() )
				{
					agoError.setErrorMessage( AgoError.SUCCESS, agoSession.getSessionId(), true );
					agoError.setResult( owner );
				}
				else
				{
					AgoSessionCache.removeSession( agoSession.getSessionId() );
					agoError.setErrorMessage( AgoError.ERROR, "Invalid session error", true );
				}
				return agoError;
			}
		}
		catch ( Exception e )
		{
			agoError.setErrorMessage( AgoError.ERROR, e.getMessage(), true );
		}
		finally
		{
			try
			{
				if ( owner != null && agoSession != null && agoSession.isValid() )
				{
					owner.setLastLoginTime( new Timestamp( System.currentTimeMillis() ) );
					owner.setStatus( Savable.MODIFIED );
					owner.updateLoginTime( con );// update last login time;
				}
			}
			catch ( SQLException e )
			{
			}
			DBConnection.close( ps );
			DBConnection.close( rs );
		}
		return agoError;
	}

}
