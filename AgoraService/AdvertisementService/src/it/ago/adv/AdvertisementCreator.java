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
		if( Constants.ADV_PROD_VEHICLE.equalsIgnoreCase( type ) )
		{
			advertisement  = new VehicleAdvertisement();
		}
		return advertisement;
	}

	public static Advertisement generateAdvertisementItem( String type, UriInfo uriInfo )
	{
		if( Constants.ADV_PROD_VEHICLE.equalsIgnoreCase( type ))
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
		advertisement.setCityCode( UriInfoUtils.getStringValue( uriInfo, Constants.PARAM_ADV_CITY_CODE) );
		advertisement.setCreatedDate( new Timestamp( System.currentTimeMillis() ) );
		advertisement.setLastModified( advertisement.getCreatedDate() );
		advertisement.setLatitude( UriInfoUtils.getStringValue( uriInfo, Constants.PARAM_ADV_LATITUDE ) );
		advertisement.setLongtute( UriInfoUtils.getStringValue( uriInfo, Constants.PARAM_ADV_LONGTUDE ) );
		advertisement.setOwnerId( UriInfoUtils.getLongValue( uriInfo, Constants.PARAM_ADV_OWNER_ID ) );
		advertisement.setPrice( UriInfoUtils.getDoubleValue( uriInfo, Constants.PARAM_ADV_PRICE ) );
		advertisement.setProductCode( UriInfoUtils.getStringValue( uriInfo, Constants.PARAM_ADV_PRODUCT_CODE ) );
		advertisement.setValidFrom( UriInfoUtils.getTimestamp( uriInfo, Constants.PARAM_ADV_VALID_FROM ) );
		advertisement.setValidTo( UriInfoUtils.getTimestamp( uriInfo, Constants.PARAM_ADV_VALID_TO ) );
		advertisement.setStatus( Savable.NEW );
	}
	public static VehicleAdvertisement generateVehicleAdvertisement( UriInfo uriInfo )
	{
		VehicleAdvertisement vehicleAdv = ( VehicleAdvertisement ) AdvertisementCreator.createAdvertisement( Constants.ADV_PROD_VEHICLE );
		mapSuper( uriInfo, vehicleAdv  );
		vehicleAdv.setBodyType( UriInfoUtils.getStringValue( uriInfo, Constants.PARAM_VEHI_BODY_TYPE ) );
		vehicleAdv.setBrandId( UriInfoUtils.getIntValue( uriInfo, Constants.PARAM_VEHI_BRAND_ID ));
		vehicleAdv.setCondition( UriInfoUtils.getStringValue( uriInfo,Constants.PARAM_VEHI_CONDITION ) );
		vehicleAdv.setDescription( UriInfoUtils.getStringValue( uriInfo, Constants.PARAM_VEHI_DESCRIPTION ) );
		vehicleAdv.setEngineCapacity( UriInfoUtils.getIntValue( uriInfo, Constants.PARAM_VEHI_ENGINE_CAPACITY) );
		vehicleAdv.setFuelType( UriInfoUtils.getStringValue( uriInfo, Constants.PARAM_VEHI_FUEL_TYPE ) );
		vehicleAdv.setMilage( UriInfoUtils.getLongValue( uriInfo, Constants.PARAM_VEHI_MILLAGE ) );
		vehicleAdv.setModelId( UriInfoUtils.getIntValue( uriInfo, Constants.PARAM_VEHI_MODEL_ID ) );
		vehicleAdv.setModelYear( UriInfoUtils.getIntValue( uriInfo,Constants.PARAM_VEHI_MODEL_YEAR ) );
		vehicleAdv.setTransmission( UriInfoUtils.getStringValue( uriInfo, Constants.PARAM_VEHI_TRANSMISSION ));
		vehicleAdv.setTypeId( UriInfoUtils.getIntValue( uriInfo, Constants.PARAM_VEHI_TYPE_ID) );
		vehicleAdv.setStatus( Savable.NEW );

		return vehicleAdv;

	}
}
