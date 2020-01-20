package it.ago.adv;

import it.ago.*;
import it.ago.utils.DBConnection;
import org.json.JSONException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.Connection;
import java.sql.SQLException;

@Path("/advcontroller")
public class AdvertisementController
{
	@Path("createAdv/{sessionId}/{type}")
	@POST
	@Produces("application/json")
	public Response createAdvertisement( @Context UriInfo uriInfo, @PathParam("sessionId") String sessionId, @PathParam("type") String type ) throws JSONException
	{
		AgoError agoError = new AgoError( AgoError.ERROR, sessionId );
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
}
