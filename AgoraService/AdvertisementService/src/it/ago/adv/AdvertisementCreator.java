package it.ago.adv;

import it.ago.*;
import it.ago.utils.db.Savable;

import javax.ws.rs.core.UriInfo;
import java.sql.PreparedStatement;
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
		if( Constants.ADV_PROD_PROPERTY.equalsIgnoreCase( type ) )
		{
			advertisement  = new PropertyAvertisement();
		}
		return advertisement;
	}

	public static Advertisement generateAdvertisementItem( String type, UriInfo uriInfo )
	{
		if( Constants.ADV_PROD_VEHICLE.equalsIgnoreCase( type ))
		{
			return generateVehicleAdvertisement( uriInfo );
		}
		if( Constants.ADV_PROD_PROPERTY.equalsIgnoreCase( type ))
		{
			return generatePropertyAdvertisement( uriInfo );
		}
		else
		{
			return null;
		}
	}
	private static void mapSuper( UriInfo uriInfo, Advertisement advertisement )
	{
		if( UriInfoUtils.getIntValue( uriInfo, Constants.PARAM_ADV_SAVABLE_STATUS ) == Savable.NEW )
		{
			advertisement.setActive( false );
		}
		// manually active after review
		advertisement.setCityCode( UriInfoUtils.getStringValue( uriInfo, Constants.PARAM_ADV_CITY_CODE) );
		advertisement.setAdvId( UriInfoUtils.getLongValue( uriInfo, Constants.PARAM_ADV_ID) );
		advertisement.setCreatedDate( new Timestamp( System.currentTimeMillis() ) );
		advertisement.setLastModified( advertisement.getCreatedDate() );
		advertisement.setLatitude( UriInfoUtils.getStringValue( uriInfo, Constants.PARAM_ADV_LATITUDE ) );
		advertisement.setLongtute( UriInfoUtils.getStringValue( uriInfo, Constants.PARAM_ADV_LONGTUDE ) );
		advertisement.setOwnerId( UriInfoUtils.getLongValue( uriInfo, Constants.PARAM_ADV_OWNER_ID ) );
		advertisement.setPrice( UriInfoUtils.getDoubleValue( uriInfo, Constants.PARAM_ADV_PRICE ) );
		advertisement.setProductCode( UriInfoUtils.getStringValue( uriInfo, Constants.PARAM_ADV_PRODUCT_CODE ) );
		advertisement.setValidFrom( UriInfoUtils.getTimestamp( uriInfo, Constants.PARAM_ADV_VALID_FROM ) );
		advertisement.setValidTo( UriInfoUtils.getTimestamp( uriInfo, Constants.PARAM_ADV_VALID_TO ) );
		advertisement.setAddress( UriInfoUtils.getStringValue( uriInfo, Constants.PARAM_ADV_ADDRESS) );
		advertisement.setStatus( UriInfoUtils.getIntValue( uriInfo, Constants.PARAM_ADV_SAVABLE_STATUS ) );
		advertisement.setRent( UriInfoUtils.getBooleanValue( uriInfo, Constants.PARAM_ADV_RENT ) );
		advertisement.setWantedToBuy( UriInfoUtils.getBooleanValue( uriInfo, Constants.PARAM_ADV_WANTED_TO_BUY ) );
		advertisement.setKeyWords( UriInfoUtils.getStringValue( uriInfo, Constants.PARAM_ADV_KEYWORDS) );
		advertisement.setType_level_1( UriInfoUtils.getIntValue( uriInfo, Constants.PARAM_ADV_TYPE_LEVEL_1) );
		advertisement.setType_level_2( UriInfoUtils.getIntValue( uriInfo, Constants.PARAM_ADV_TYPE_LEVEL_2) );
		advertisement.setType_level_3( UriInfoUtils.getIntValue( uriInfo, Constants.PARAM_ADV_TYPE_LEVEL_3) );
		advertisement.setAdvDescription( UriInfoUtils.getStringValue(uriInfo,Constants.PARAM_ADV_DESCRIPTION));

	}
	public static VehicleAdvertisement generateVehicleAdvertisement( UriInfo uriInfo )
	{
		VehicleAdvertisement vehicleAdv = ( VehicleAdvertisement ) AdvertisementCreator.createAdvertisement( Constants.ADV_PROD_VEHICLE );
		mapSuper( uriInfo, vehicleAdv  );
		vehicleAdv.setBodyType( UriInfoUtils.getStringValue( uriInfo, Constants.PARAM_VEHI_BODY_TYPE ) );
		vehicleAdv.setCondition( UriInfoUtils.getStringValue( uriInfo,Constants.PARAM_VEHI_CONDITION ) );
		vehicleAdv.setDescription( UriInfoUtils.getStringValue( uriInfo, Constants.PARAM_VEHI_DESCRIPTION ) );
		vehicleAdv.setEngineCapacity( UriInfoUtils.getIntValue( uriInfo, Constants.PARAM_VEHI_ENGINE_CAPACITY) );
		vehicleAdv.setFuelType( UriInfoUtils.getStringValue( uriInfo, Constants.PARAM_VEHI_FUEL_TYPE ) );
		vehicleAdv.setMilage( UriInfoUtils.getLongValue( uriInfo, Constants.PARAM_VEHI_MILLAGE ) );
		vehicleAdv.setTransmission( UriInfoUtils.getStringValue( uriInfo, Constants.PARAM_VEHI_TRANSMISSION ));
		vehicleAdv.setStatus( UriInfoUtils.getIntValue( uriInfo, Constants.PARAM_ADV_SAVABLE_STATUS ) );
		vehicleAdv.setModelId( UriInfoUtils.getIntValue( uriInfo, Constants.PARAM_VEHI_MODEL_ID ) );
		vehicleAdv.setModelYear( UriInfoUtils.getIntValue( uriInfo,Constants.PARAM_VEHI_MODEL_YEAR ) );
		vehicleAdv.setBrandId( UriInfoUtils.getIntValue( uriInfo, Constants.PARAM_VEHI_BRAND_ID ));
		vehicleAdv.setTypeId( UriInfoUtils.getIntValue( uriInfo, Constants.PARAM_VEHI_TYPE_ID) );
		return vehicleAdv;

	}
	public static PropertyAvertisement generatePropertyAdvertisement( UriInfo uriInfo )
	{
		PropertyAvertisement propertyAdv = ( PropertyAvertisement ) AdvertisementCreator.createAdvertisement( Constants.ADV_PROD_PROPERTY );
		mapSuper( uriInfo, propertyAdv  );
		propertyAdv.setBeds( UriInfoUtils.getIntValue( uriInfo, Constants.PARAM_PROP_BEDS ) );
		propertyAdv.setBath( UriInfoUtils.getIntValue( uriInfo, Constants.PARAM_PROP_BATH ) );
		propertyAdv.setHouseSize( UriInfoUtils.getIntValue( uriInfo, Constants.PARAM_PROP_HOUSE_SIZE ) );
		propertyAdv.setLandSize( UriInfoUtils.getIntValue( uriInfo, Constants.PARAM_PROP_LAND_SIZE) );
		propertyAdv.setParking( UriInfoUtils.getBooleanValue( uriInfo, Constants.PARAM_PROP_PARKING) );
		propertyAdv.setDistanceToMainRd( UriInfoUtils.getIntValue( uriInfo, Constants.PARAM_PROP_DISTANCE_TO_MAIN_RD) );
		propertyAdv.setStatus( UriInfoUtils.getIntValue( uriInfo, Constants.PARAM_ADV_SAVABLE_STATUS ) );

		return propertyAdv;

	}
}
