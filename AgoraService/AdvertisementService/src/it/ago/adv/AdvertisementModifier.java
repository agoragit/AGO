package it.ago.adv;

import it.ago.Advertisement;
import it.ago.AgoError;
import it.ago.utils.DBConnection;
import org.json.JSONException;

import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class AdvertisementModifier
{

	public static Response activeAdvertisement( boolean active, long advId ) throws JSONException
	{
		Connection con = null;
		PreparedStatement ps = null;
		List<Advertisement> advertisements = new ArrayList<>(  );
		AgoError agoError = new AgoError( AgoError.SUCCESS, advId+" activated" );
		try
		{
			con = DBConnection.getConnection( DBConnection.MYSQL_CONNECTION_TYPE );
			ps = con.prepareStatement( DBQuearies.Q_ACTIVE_ADV_BY_ID );
			ps.setBoolean( 1, active );
			ps.setLong( 2, advId );
			ps.executeUpdate();
		}
		catch ( Exception e )
		{
			agoError.setErrorMessage( AgoError.ERROR,e.getMessage(), false );
		}
		finally
		{
			DBConnection.close( con,ps,null );
		}
		agoError.setResult( advertisements );
		return agoError._getErrorResponse();
	}
}
