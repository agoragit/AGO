package it.ago;

import java.util.HashMap;
import java.util.Map;

public class AgoSessionCache
{
	private static Map<String, AgoSession> SESSION_CACHE = new HashMap<>();;


	public static Map<String, AgoSession> getSessionCache()
	{
		return SESSION_CACHE;
	}

	public static void setSessionCache( Map<String, AgoSession> sessionCache )
	{
		SESSION_CACHE = sessionCache;
	}

	public static void putSession( AgoSession agoSession )
	{
		SESSION_CACHE.put( agoSession.getSessionId(), agoSession );
	}

	public static boolean isValid( String sessionId )
	{
		AgoSession agoSession = SESSION_CACHE.get( sessionId );
		return agoSession != null && agoSession.isValid();
	}

	public static void invalidateSession( String sessionId )
	{
		if ( SESSION_CACHE.get( sessionId ) != null )
		{
			SESSION_CACHE.get( sessionId ).setValid( false );
		}
	}

	public static void removeSession( String sessionId )
	{
		if ( SESSION_CACHE.get( sessionId ) != null )
		{
			SESSION_CACHE.remove( sessionId );
		}
	}

	public static AgoSession getSession( String sessionId )
	{
		return SESSION_CACHE.get( sessionId );
	}
}
