package it.ago.utils;

import it.ago.AgoError;
import it.ago.UriInfoUtils;
import it.ago.utils.db.Savable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ValidationUtils
{
	public static boolean validateAdvertisementOwner( long userId, long advId, Connection con, int savableStatus, String product )
	{
		if( Savable.NEW != savableStatus )
		{
			if( advId < -1 || userId <-1 || product == null || product.isEmpty() )
			{
				return false;
			}
			ResultSet rs = null;
			PreparedStatement ps = null;
			try
			{
				String q = " SELECT 1 FROM AGO_ADVERTISEMENT WHERE ADV_ID = ? AND OWNER_ID = ? AND PRODUCT_CODE = ? ";
				ps = con.prepareStatement( q );
				ps.setLong( 1, advId );
				ps.setLong( 2, userId );
				ps.setString( 3, product );
				rs = ps.executeQuery();
				if( rs.next() )
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			catch ( Exception e )
			{
				return false;
			}
			finally
			{
				DBConnection.close( null, ps, rs);
			}
		}
		return true;
	}
}
