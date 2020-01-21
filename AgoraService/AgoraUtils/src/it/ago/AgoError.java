package it.ago;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.Response;
import java.io.Serializable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AgoError implements Serializable
{
	public static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "ERROR";
	private String errorMessage = "";
	private String errorCode = "";
	private Object result = new Object();
	private static ObjectMapper mapper = new ObjectMapper();
	static
	{
		mapper.configure( SerializationFeature.INDENT_OUTPUT, true);
		mapper.configure( SerializationFeature.FAIL_ON_EMPTY_BEANS, false );
	}
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

		try
		{
			jsonObject.put( "RESULT",  new JSONArray( "["+ mapper.writeValueAsString( result )+ "]" ) );
		}
		catch ( JsonProcessingException e )
		{
			jsonObject.put( "RESULT",  e.getMessage());

		}

		return Response.status( 200 ).entity( jsonObject.toString() ).build();
	}
}
