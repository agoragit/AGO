package it.ago.adv;

import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import it.ago.*;
import it.ago.cache.AgoCacheRefresher;
import it.ago.system.SystemConfig;
import it.ago.utils.DBConnection;
import it.ago.utils.ValidationUtils;
import it.ago.utils.db.Savable;
import org.json.JSONException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Path("/advController")
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
		SystemConfig.ADV_IMAGE_UPLOAD_PATH = SystemConfig.getString("ADV_IMAGE_UPLOAD_PATH"  );
		SystemConfig.ADV_MAX_UPLOAD_IMAGE_COUNT = SystemConfig.getInt( "ADV_MAX_UPLOAD_IMAGE_COUNT" );
		SystemConfig.ADV_ROOT_URL= SystemConfig.getString( "ADV_ROOT_URL" );
		new AgoCacheRefresher().start();
	}

	@Path("createAdv/{sessionId}/{type}")
	@POST
	@Produces("application/json")
	@Consumes(MediaType.MULTIPART_FORM_DATA)

	public Response createAdvertisement( @Context UriInfo uriInfo, @PathParam("type") String type, FormDataMultiPart uploadedInputStream ) throws JSONException
	{
		AgoError agoError = new AgoError( AgoError.ERROR, type );
		if( uriInfo == null || uriInfo.getQueryParameters() == null || uriInfo.getQueryParameters().isEmpty() )
		{
			agoError.setErrorMessage( AgoError.ERROR, "invalid request", false );
			return agoError._getErrorResponse();
		}
		String sessionId = UriInfoUtils.getStringValue( uriInfo, Constants.PARAM_SESSION_ID );
		if( !AgoSession._isValidSession( sessionId ))
		{
			agoError.setErrorMessage( AgoError.ERROR, "Session Expired", false );
			return agoError._getErrorResponse();
		}
		if( AgoSessionCache.getSession( sessionId ).getUserId() != UriInfoUtils.getLongValue( uriInfo, "ownerId" ) )
		{
			agoError.setErrorMessage( AgoError.ERROR, "invalid session/owner for advertisement created owner - mismatch", false );
			return agoError._getErrorResponse();
		}
		long status = UriInfoUtils.getIntValue( uriInfo, Constants.PARAM_ADV_SAVABLE_STATUS );
		if( !(status == Savable.NEW || status == Savable.MODIFIED ) )
		{
			agoError.setErrorMessage( AgoError.ERROR, "advertisement only support for update and create", false );
			return agoError._getErrorResponse();
		}
		Connection con = null;
		try
		{
			List<FormDataBodyPart> bodyPartList = uploadedInputStream.getFields( Constants.PARAM_ADV_IMAGE );
			List<AdvImage> advImages = new ArrayList<>(  );
			con = DBConnection.getConnection( DBConnection.MYSQL_CONNECTION_TYPE );
			con.setAutoCommit( false );

			if( !ValidationUtils.validateAdvertisementOwner( AgoSession.loadSession( sessionId ).getUserId(),  UriInfoUtils.getLongValue( uriInfo, Constants.PARAM_ADV_ID), con, UriInfoUtils.getIntValue( uriInfo, Constants.PARAM_ADV_SAVABLE_STATUS), UriInfoUtils.getStringValue( uriInfo, Constants.PARAM_ADV_PRODUCT_CODE) ) )
			{
				agoError.setErrorMessage( AgoError.ERROR, "wrong owner trying to change advertisement or incorrect advid or product", false );
				return agoError._getErrorResponse();
			}

			Advertisement advertisement = AdvertisementCreator.generateAdvertisementItem( type, uriInfo );
			if( Savable.NEW == status && bodyPartList != null )
			{
				int i = 0;
				for ( FormDataBodyPart formDataBodyPart : bodyPartList )
				{
					//generate advImages before save advertisement to get advId
					AdvImage advImage = new AdvImage();
					advImage.setImageId( i++ );
					advImage.setImageUrl( formDataBodyPart.getFormDataContentDisposition().getFileName().replace( " ","" ) );
					advImages.add( advImage );
				}
				advertisement.setAdvImages( advImages );
			}
			if( advertisement == null )
			{
				agoError.setErrorMessage( AgoError.ERROR,"Invalid type!", false );
				return agoError._getErrorResponse();
			}

			advertisement.save( con );

			if( Savable.NEW == status && bodyPartList!= null )
			{
				try
				{
					for ( FormDataBodyPart formDataBodyPart : bodyPartList )
					{
						//Image path should be change in both places -> inside the advrtersement and here
						InputStream instream = ( ( BodyPartEntity ) formDataBodyPart.getEntity() ).getInputStream();
						File targetFile = new File( System.getenv("CATALINA_HOME")+"\\webapps\\",SystemConfig.ADV_IMAGE_UPLOAD_PATH + advertisement.getProductCode() + "\\" + advertisement.getAdvId()  );
						if(!targetFile.exists())
						{
							targetFile.mkdirs();
						}
						targetFile = new File( System.getenv("CATALINA_HOME")+"\\webapps\\",SystemConfig.ADV_IMAGE_UPLOAD_PATH + advertisement.getProductCode() + "\\" + advertisement.getAdvId() +"\\"+formDataBodyPart.getFormDataContentDisposition().getFileName().replace( " ","" ) );
						java.nio.file.Files.copy(
								instream,
								targetFile.toPath(),
								StandardCopyOption.REPLACE_EXISTING );
						instream.close();
					}
				}
				catch ( IOException e )
				{
					agoError.setErrorMessage( AgoError.ERROR, "Error creating adv. Image upload failed", false );
					con.rollback();
					return agoError._getErrorResponse();
				}
			}
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

	@Path("getAdvById/{id}")
	@POST
	@Produces("application/json")
	public Response getAdvertisementById( @PathParam("sessionId") String sessionId, @PathParam("id") long id ) throws JSONException
	{
		AgoError agoError = new AgoError( AgoError.SUCCESS, id+"" );
		if( id < 1 )
		{
			return agoError._getErrorResponse();
		}
		return AdvertisementSearchHandler.getAdvertisementById( AgoSession._isValidSession( sessionId ), id );
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
		return AdvertisementSearchHandler.getAdvertisementByOwnerId( AgoSession._isValidSession( sessionId ), ownerId );
	}

	@Path("universalAdvSearch/")
	@POST
	@Produces("application/json")
	public Response universalAdvertisementSearch(  @Context UriInfo uriInfo ) throws JSONException
	{
		if( uriInfo == null || uriInfo.getQueryParameters() == null || uriInfo.getQueryParameters().isEmpty() )
		{
			return new AgoError( AgoError.ERROR, "invalid request" )._getErrorResponse();
		}
//		String sessionId = UriInfoUtils.getStringValue( uriInfo, Constants.PARAM_SESSION_ID );
//		boolean isVald = AgoSession._isValidSession( sessionId );
//		if( sessionId != null && !isVald)
//		{
//			return  new AgoError( AgoError.ERROR, "Session Expired" )._getErrorResponse();
//		}
		// invalid session restricted some sensitive data. Valid session can return sensitive data// currently it is true
		return  AdvertisementSearchHandler.universalAdvSearch( true,uriInfo );
	}
}

