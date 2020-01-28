package it.ago.adv;

import it.ago.*;
import it.ago.cache.AgoCacheRefresher;
import it.ago.system.SystemConfig;
import it.ago.utils.DBConnection;
import org.json.JSONException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Path("/advcontroller")
public class AdvertisementController
{
	static
	{
		init();
	}

	private static void init()
	{
		SystemConfig.loadConfigurations( SystemConfig.SERVICE_ADVERTISEMENT );
		SystemConfig.AGO_SESSION_TIME_OUT = SystemConfig.getInt( "AGO_SESSION_TIME_OUT" );
		SystemConfig.AGO_CACHE_REFRESH_TIME= SystemConfig.getInt( "AGO_CACHE_REFRESH_TIME" );
		new AgoCacheRefresher().start();
	}
	@Path("createAdv/{sessionId}/{type}")
	@POST
	@Produces("application/json")
	public Response createAdvertisement( @Context UriInfo uriInfo, @PathParam("sessionId") String sessionId, @PathParam("type") String type ) throws JSONException
	{
		AgoError agoError = new AgoError( AgoError.ERROR, type );
		if( !AgoSession._isValidSession( sessionId ) || uriInfo == null || uriInfo.getQueryParameters() == null || uriInfo.getQueryParameters().isEmpty() )
		{
			agoError.setErrorMessage( AgoError.ERROR, "invalid session or request", false );
			return agoError._getErrorResponse();
		}
		if( AgoSessionCache.getSession( sessionId ).getUserId() != UriInfoUtils.getLongValue( uriInfo, "ownerId" ) )
		{
			agoError.setErrorMessage( AgoError.ERROR, "invalid session/owner for advertisement created owner - mismatch", false );
			return agoError._getErrorResponse();
		}
		Connection con = null;
		try
		{
			con = DBConnection.getConnection( DBConnection.MYSQL_CONNECTION_TYPE );
			con.setAutoCommit( false );
			Advertisement advertisement = AdvertisementCreator.generateAdvertisementItem( type, uriInfo );
			if( advertisement == null )
			{
				agoError.setErrorMessage( AgoError.ERROR,"Invalid type!", false );
			}
			advertisement.save( con );
			con.commit();
			agoError.setErrorMessage( AgoError.SUCCESS,"Advertisement created!", true );
			agoError.setResult( advertisement );
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
	@Path("getAdvById/{sessionId}/{id}")
	@POST
	@Produces("application/json")
	public Response getAdvertisementById( @PathParam("sessionId") String sessionId, @PathParam("id") long id ) throws JSONException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs  = null;
		AgoError agoError = new AgoError( AgoError.SUCCESS, id+"" );
		if(!AgoSession._isValidSession( sessionId ))
		{
			agoError.setErrorMessage( AgoError.ERROR, "invalid session", false );
			return agoError._getErrorResponse();
		}
		if( id < 1 )
		{
			return agoError._getErrorResponse();
		}
		return AdvertisementSearchHandler.getAdvertisementById( sessionId, id );
	}
	@Path("getAdvByOwnerId/{sessionId}/{ownerId}")
	@POST
	@Produces("application/json")
	public Response getAdvertisementByOwnerId( @PathParam("sessionId") String sessionId, @PathParam("ownerId") long ownerId ) throws JSONException
	{
		AgoError agoError = new AgoError( AgoError.SUCCESS, ownerId+"" );
		if( ownerId < 1 )
		{
			return agoError._getErrorResponse();
		}
		if( !AgoSession._isValidSession( sessionId ) )
		{
			agoError.setErrorMessage( AgoError.ERROR, "invalid session", true );
			return agoError._getErrorResponse();
		}
		return AdvertisementSearchHandler.getAdvertisementByOwnerId( sessionId, ownerId );
	}

	@Path("universalAdvSearch/{sessionId}")
	@POST
	@Produces("application/json")
	public Response universalAdvertisementSearch( @PathParam("sessionId") String sessionId, @Context UriInfo uriInfo ) throws JSONException
	{
		AgoError agoError = new AgoError( AgoError.SUCCESS, "not implemented" );
		return  AdvertisementSearchHandler.universalAdvSearch( sessionId,uriInfo );
	}
}

