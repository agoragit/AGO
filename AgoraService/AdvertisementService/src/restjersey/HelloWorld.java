package restjersey;

import it.ago.utils.DBConnection;
import it.ago.utils.SystemPropertyCache;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.sql.SQLException;

// The Java class will be hosted at the URI path "/helloworld"
@Path("/helloworld")
public class HelloWorld
{
	// The Java method will process HTTP GET requests
	@GET
	// The Java method will produce content identified by the MIME Media type "text/plain"
	@Produces("text/plain")
	public String getClichedMessage()
	{
		// Return some cliched textual content
		SystemPropertyCache.init();
		try
		{
			DBConnection.getConnection( DBConnection.MYSQL_CONNECTION_TYPE );
		}
		catch ( SQLException e )
		{
			e.printStackTrace();
		}
		return "Hello World";
	}

	@Produces("text/plain")
	@Path("/helloworlds")
	public String getClichedMessagess()
	{
		// Return some cliched textual content
		return "Hello World";
	}
}