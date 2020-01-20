package it.ago.adv;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/vehicleadv")
public class VehicleAdv
{
	@GET
	@Produces("application/json")
	public Response convertFtoC()
	{

		String jasonStringResult = null;
		return Response.status( 200 ).entity( jasonStringResult ).build();

	}
}
