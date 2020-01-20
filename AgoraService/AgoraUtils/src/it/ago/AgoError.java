package it.ago;

import com.google.gson.Gson;
import org.json.JSONObject;

import javax.ws.rs.core.Response;
import java.io.Serializable;

public class AgoError implements Serializable
{
	public static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "ERROR";
	private String errorMessage = "";
	private String errorCode = "";
	private Object result = null;

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
		jsonObject.put( "DATA", new Gson().toJson( result ) );
		return Response.status( 200 ).entity( jsonObject.toString() ).build();
	}
}
