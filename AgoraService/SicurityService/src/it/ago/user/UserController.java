package it.ago.user;

import it.ago.AgoError;
import it.ago.AgoSession;
import it.ago.AgoSessionCache;
import it.ago.utils.DBConnection;
import org.json.JSONException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.Connection;
import java.sql.SQLException;

@Path("/user")
public class UserController
{
	@POST
	@Produces("application/json")
	@Path("/saveUser/{s}")
	public Response createUser( @Context UriInfo userInfo, @PathParam("s") int status )
	{
		AgoError agoError = new AgoError( AgoError.ERROR, "unknown error" );
		Connection con = null;
		try
		{
			Owner owner = UserUtils.getOwner( userInfo, status );
			con = DBConnection.getConnection( DBConnection.MYSQL_CONNECTION_TYPE );
			agoError = owner._validateUserForSave( con );
			if ( agoError._isSuccess() )
			{
				owner.save( con );
			}
		}
		catch ( SQLException e )
		{
			agoError.setErrorMessage( AgoError.ERROR, e.getMessage(), false );
		}
		finally
		{
			DBConnection.close( con );
		}
		return agoError._getErrorResponse();
	}

	@POST
	@Produces("application/json")
	@Path("/userAuthorize/{u}")
	public Response userAuthorize( @Context UriInfo userInfo, @PathParam("u") String username )
	{
		AgoError agoError = new AgoError( AgoError.ERROR, "unknown error" );
		Connection con = null;
		try
		{
			con = DBConnection.getConnection( DBConnection.MYSQL_CONNECTION_TYPE );
			agoError = UserUtils.userAuthorizeEx( userInfo, username, con );
		}
		catch ( SQLException e )
		{
			agoError.setErrorMessage( AgoError.ERROR, e.getMessage(), false );
		}
		finally
		{
			DBConnection.close( con );
		}
		return agoError._getErrorResponse();
	}

	@POST
	@Produces("application/json")
	@Path("/userUnAuthorize/{s}")
	public Response userUnAuthorize( @Context UriInfo userInfo, @PathParam("s") String sessionId )
	{
		AgoError agoError = new AgoError( AgoError.SUCCESS, "session removed-(logout)" );
		AgoSessionCache.removeSession( sessionId );
		return agoError._getErrorResponse();
	}
	@Path("activeOwner/{sessionId}/{id}/{active}")
	@POST
	@Produces("application/json")
	public Response activeAdvertisement( @PathParam("sessionId") String sessionId, @PathParam("id") long id,  @PathParam("active") boolean active ) throws JSONException
	{
		AgoError agoError = new AgoError( AgoError.ERROR, id+" owner id not valid" );
		if(  !AgoSession._isValidSession( sessionId ) )
		{
			agoError.setErrorMessage( AgoError.ERROR, "Session expired", true );
			return agoError._getErrorResponse();
		}
		if( id < 1 )
		{
			return agoError._getErrorResponse();
		}
		return UserUtils.activeOwner( active,id );
	}
}
