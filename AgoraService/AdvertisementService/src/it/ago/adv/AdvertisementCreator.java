package it.ago.adv;

import it.ago.Advertisement;
import it.ago.Constants;
import it.ago.UriInfoUtils;
import it.ago.VehicleAdvertisement;
import it.ago.utils.db.Savable;

import javax.ws.rs.core.UriInfo;
import java.sql.Timestamp;

public class AdvertisementCreator
{
	public static Advertisement createAdvertisement( String type )
	{
		Advertisement advertisement = null;
		if( Constants.ADV_TYPE_VEHICLE.equalsIgnoreCase( type ) )
		{
			advertisement  = new VehicleAdvertisement();
		}
		return advertisement;
	}

	public static Advertisement generateAdvertisementItem( String type, UriInfo uriInfo )
	{
		if( Constants.ADV_TYPE_VEHICLE.equalsIgnoreCase( type ))
		{
			return generateVehicleAdvertisement( uriInfo );
		}
		else
		{
			return null;
		}
	}
	private static void mapSuper( UriInfo uriInfo, VehicleAdvertisement advertisement )
	{
		advertisement.setActive( true );
		advertisement.setCityCode( UriInfoUtils.getStringValue( uriInfo, "cityCode" ) );
		advertisement.setCreatedDate( new Timestamp( System.currentTimeMillis() ) );
		advertisement.setLastModified( advertisement.getCreatedDate() );
		advertisement.setLatitude( UriInfoUtils.getStringValue( uriInfo, "latitude" ) );
		advertisement.setLongtute( UriInfoUtils.getStringValue( uriInfo, "longtute" ) );
		advertisement.setOwnerId( UriInfoUtils.getLongValue( uriInfo, "ownerId" ) );
		advertisement.setPrice( UriInfoUtils.getDoubleValue( uriInfo, "price" ) );
		advertisement.setProductCode( UriInfoUtils.getStringValue( uriInfo, "productCode" ) );
		advertisement.setValidFrom( UriInfoUtils.getTimestamp( uriInfo, "validFrom" ) );
		advertisement.setValidTo( UriInfoUtils.getTimestamp( uriInfo, "validTo" ) );
		advertisement.setStatus( Savable.NEW );
	}
	public static VehicleAdvertisement generateVehicleAdvertisement( UriInfo uriInfo )
	{
		VehicleAdvertisement vehicleAdv = ( VehicleAdvertisement ) AdvertisementCreator.createAdvertisement( Constants.ADV_TYPE_VEHICLE );
		mapSuper( uriInfo, vehicleAdv  );
		vehicleAdv.setBodyType( UriInfoUtils.getStringValue( uriInfo, "bodyType" ) );
		vehicleAdv.setBrandId( UriInfoUtils.getIntValue( uriInfo, "brandId" ));
		vehicleAdv.setBodyType( UriInfoUtils.getStringValue( uriInfo,"bodyType" ) );
		vehicleAdv.setCondition( UriInfoUtils.getStringValue( uriInfo,"condition" ) );
		vehicleAdv.setDescription( UriInfoUtils.getStringValue( uriInfo, "description" ) );
		vehicleAdv.setEngineCapacity( UriInfoUtils.getIntValue( uriInfo, "engineCapacity" ) );
		vehicleAdv.setFuelType( UriInfoUtils.getStringValue( uriInfo, "fuelType" ) );
		vehicleAdv.setMilage( UriInfoUtils.getLongValue( uriInfo, "millage" ) );
		vehicleAdv.setModelId( UriInfoUtils.getIntValue( uriInfo, "modelId" ) );
		vehicleAdv.setModelYear( UriInfoUtils.getIntValue( uriInfo,"modelYear" ) );
		vehicleAdv.setTransmission( UriInfoUtils.getStringValue( uriInfo, "transmission" ));
		vehicleAdv.setTypeId( UriInfoUtils.getIntValue( uriInfo, "type" ) );
		vehicleAdv.setStatus( Savable.NEW );

		return vehicleAdv;

	}
}
