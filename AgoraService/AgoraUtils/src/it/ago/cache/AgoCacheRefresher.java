package it.ago.cache;

import it.ago.AgoSession;
import it.ago.AgoSessionCache;
import it.ago.system.SystemConfig;
import it.ago.utils.DBConnection;
import it.ago.utils.db.Savable;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AgoCacheRefresher extends Thread
{
	public void run()
	{
		while ( true )
		{
			try
			{
				refreshAgoSessionCache();
				Thread.sleep( SystemConfig.AGO_CACHE_REFRESH_TIME * 1000 * 60 );
			}
			catch ( InterruptedException e )
			{
				e.printStackTrace();
			}
		}
	}

	private static void refreshAgoSessionCache()
	{
		Connection con = null;
		try
		{
			Timestamp sTime = new Timestamp( System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(SystemConfig.AGO_SESSION_TIME_OUT) );
			Iterator it = AgoSessionCache.getSessionCache().keySet().iterator();
			while ( it.hasNext() )
			{
				Map.Entry pair = ( Map.Entry ) it.next();
				AgoSession agoSession = ( AgoSession ) pair.getValue();
				if(agoSession.getCreateTime().before( sTime ) )
				{
					AgoSessionCache.removeSession( agoSession.getSessionId() );
					agoSession.setStatus( Savable.DELETED );
					agoSession.save( con );
				}
			}
		}
		catch ( Exception e )
		{

		}
		finally
		{
			DBConnection.close( con );
		}
	}

}
