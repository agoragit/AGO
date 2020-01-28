package it.ago.adv;

import it.ago.Advertisement;
import it.ago.AgoError;
import it.ago.utils.DBConnection;
import org.json.JSONException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdvertisementSearchHandler
{
	public static Response getAdvertisementById( String sessionId, long id ) throws JSONException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs  = null;
		AgoError agoError = new AgoError( AgoError.SUCCESS, id+"" );
		try
		{
			con = DBConnection.getConnection( DBConnection.MYSQL_CONNECTION_TYPE );
			ps = con.prepareStatement( DBQuearies.Q_SEARCH_ADV_BY_ID );
			ps.setLong( 1, id );
			rs  = ps.executeQuery();
			while( rs.next() )
			{
				Advertisement advertisement = Advertisement.getInstance( rs, con );
				agoError.setResult( advertisement );
			}
		}
		catch ( Exception e )
		{
			agoError.setErrorMessage( AgoError.ERROR,e.getMessage(), false );
		}
		finally
		{
			DBConnection.close( con,ps,rs );
		}
		return agoError._getErrorResponse();
	}

	public static Response getAdvertisementByOwnerId( String sessionId, long ownerId ) throws JSONException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs  = null;
		List<Advertisement> advertisements = new ArrayList<>(  );
		AgoError agoError = new AgoError( AgoError.SUCCESS, ownerId+"" );
		try
		{
			con = DBConnection.getConnection( DBConnection.MYSQL_CONNECTION_TYPE );
			ps = con.prepareStatement( DBQuearies.Q_SEARCH_ADV_BY_OWNER_ID );
			ps.setLong( 1, ownerId );
			rs  = ps.executeQuery();
			while( rs.next() )
			{
				Advertisement advertisement = Advertisement.getInstance( rs, con );
				advertisements.add( advertisement );
			}
		}
		catch ( Exception e )
		{
			agoError.setErrorMessage( AgoError.ERROR,e.getMessage(), false );
		}
		finally
		{
			DBConnection.close( con,ps,rs );
		}
		agoError.setResult( advertisements );
		return agoError._getErrorResponse();
	}
	public static Response universalAdvSearch( String sessionId, UriInfo uriInfo)
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs  = null;
		List<Advertisement> advertisements = new ArrayList<>(  );
		AgoError agoError = new AgoError( AgoError.SUCCESS,""  );
		try
		{
			con = DBConnection.getConnection( DBConnection.MYSQL_CONNECTION_TYPE );
			ps = DBQuearies.getUniversalAdvSearchStatement(con,uriInfo);
			rs  = ps.executeQuery();
			while( rs.next() )
			{
				Advertisement advertisement = Advertisement.getInstance( rs, con );
				advertisements.add( advertisement );
			}
		}
		catch ( Exception e )
		{
			agoError.setErrorMessage( AgoError.ERROR,e.getMessage(), false );
		}
		finally
		{
			DBConnection.close( con,ps,rs );
		}
		agoError.setResult( advertisements );
		return agoError._getErrorResponse();
	}
}
