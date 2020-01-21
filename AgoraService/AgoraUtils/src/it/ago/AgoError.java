package it.ago;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AgoError implements Serializable
{
	public static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "ERROR";
	private String errorMessage = "";
	private String errorCode = "";
	private Object result = new Object();

	public AgoError( String errorCode, String errorMessage )
	{
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public AgoError( String errorCode )
	{
		this.errorCode = errorCode;
	}

	public boolean _isError()
	{
		return this.errorCode == AgoError.ERROR;
	}

	public boolean _isSuccess()
	{
		return this.errorCode == AgoError.SUCCESS;
	}

	public void setErrorMessage( String errorCode, String errorMessage, boolean replace )
	{
		this.errorCode = errorCode;
		if ( replace )
		{
			this.errorMessage = errorMessage;
		}
		else
		{
			this.errorMessage = this.errorMessage + " | " + errorMessage;
		}
	}

	public void setErrorCode( String errorCode )
	{
		this.errorCode = errorCode;
	}

	public Object getResult()
	{
		return result;
	}

	public void setResult( Object result )
	{
		this.result = result;
	}

	public Response _getErrorResponse()
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put( "STATUS", this.errorCode );
		jsonObject.put( "MESSAGE", this.errorMessage );
		if( result instanceof List )
		{
			JSONArray jArray = new JSONArray( ( ( ArrayList ) result ).toArray() );
			jsonObject.put( "RESULT",  jArray );
		}
		else
		{
			jsonObject.put( "RESULT", new JSONObject( result ) );
		}
		return Response.status( 200 ).entity( jsonObject.toString() ).build();
	}
}
