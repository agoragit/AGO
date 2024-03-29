package it.ago;

public class Constants
{

//---------------------PRODUCT TYPES
	public static String ADV_PROD_VEHICLE = "VEHI";
	public static String ADV_PROD_PROPERTY = "PROP";
	public static final String ADV_PRODUCT_ANY = "ANY";
	public static final String PARAM_SESSION_ID = "sessionId";


	public static final String PARAM_PRODUCT_TYPES = "productTypes";

	//---------------------URI PARAMS
	//Advertisement uri params
	public static final String PARAM_ADV_ID = "advId";
	public static final String PARAM_ADV_VALID_FROM = "validFrom";
	public static final String PARAM_ADV_VALID_TO = "validTo";
	public static final String PARAM_ADV_ACTIVE = "active";
	public static final String PARAM_ADV_CREATED_DATE = "createdDate";
	public static final String PARAM_ADV_LAST_MODIFIED = "lastModified";
	public static final String PARAM_ADV_PRODUCT_CODE  = "productCode";
	public static final String PARAM_ADV_OWNER_ID  ="ownerId";
	public static final String PARAM_ADV_CITY_CODE = "cityCode";
	public static final String PARAM_ADV_PRICE  ="price";
	public static final String PARAM_ADV_LATITUDE = "latitude";
	public static final String PARAM_ADV_LONGTUDE = "longtude";
	public static final String PARAM_ADV_PRICE_FROM  ="priceFrom";
	public static final String PARAM_ADV_PRICE_TO  ="priceTo";
	public static final String PARAM_ADV_CREATED_DATE_FROM = "createdDateFrom";
	public static final String PARAM_ADV_CREATED_DATE_TO = "createdDateTo";
	public static final String PARAM_ADV_IMAGE = "image";
	public static final String PARAM_ADV_ADDRESS = "address";
	public static final String PARAM_ADV_RENT = "rent";
	public static final String PARAM_ADV_WANTED_TO_BUY = "wantedToBuy";
	public static final String PARAM_ADV_SAVABLE_STATUS = "status";
	public static final String PARAM_ADV_KEYWORDS = "keywords";
	public static final String PARAM_ADV_DESCRIPTION = "advDescription";
	public static final String PARAM_OWNER_TYPE = "ownerType";

	public static final String PARAM_ADV_QUARY_OFFSET = "offset";
	public static final String PARAM_ADV_QUARY_ROW_COUNT= "rowCount";
	public static final String PARAM_ADV_TYPE_LEVEL_1 = "typeLevel1";
	public static final String PARAM_ADV_TYPE_LEVEL_2 = "typeLevel2";
	public static final String PARAM_ADV_TYPE_LEVEL_3 = "typeLevel3";
//	public static final String PARAM_ADV_TYPE_ID = "advTypeId";



	//Vehicle uri params
	public static final String PARAM_VEHI_CONDITION = "vCondition";
	public static final String PARAM_VEHI_TRANSMISSION = "vTransmission";
	public static final String PARAM_VEHI_BODY_TYPE = "vBodyType";
	public static final String PARAM_VEHI_FUEL_TYPE = "vFuelType";
	public static final String PARAM_VEHI_ENGINE_CAPACITY = "vEngineCapacity";
	public static final String PARAM_VEHI_MILLAGE = "vMillage";
	public static final String PARAM_VEHI_DESCRIPTION = "vDescription";
	public static final String PARAM_VEHI_BRAND_ID  = "vBrandId";
	public static final String PARAM_VEHI_MODEL_ID = "vModelId";
	public static final String PARAM_VEHI_MODEL_YEAR = "vModelYear";
	public static final String PARAM_VEHI_TYPE_ID = "vTypeId";
	public static final String PARAM_VEHI_MODEL_YEAR_TO = "vModelYearTo";
	public static final String PARAM_VEHI_MODEL_YEAR_FROM = "vModelYearFrom";
	public static final String PARAM_VEHI_ENGINE_CAPACITY_FROM = "vEngineCapacityFrom";
	public static final String PARAM_VEHI_ENGINE_CAPACITY_TO = "vEngineCapacityTo";
	public static final String PARAM_VEHI_MILLAGE_FROM = "vMillageFrom";
	public static final String PARAM_VEHI_MILLAGE_TO = "vMillageTo";

	public static final String PARAM_ADV_IMAGE_ID = "imageId";
	public static final String PARAM_ADV_IMAGE_URL = "imageUrl";
	public static final String PARAM_ADV_IMAGE_ADV_ID = "advId";
	public static final String PARAM_ADV_IMAGE_STATUS = "status";

	public static final String PARAM_PROP_TYPE_ID = "pTypeId";
	public static final String PARAM_PROP_BEDS = "pBeds";
	public static final String PARAM_PROP_BEDS_FROM = "pBedsFrom";
	public static final String PARAM_PROP_BEDS_TO = "pBedsTo";
	public static final String PARAM_PROP_BATH = "pBath";
	public static final String PARAM_PROP_HOUSE_SIZE = "pHouseSize";
	public static final String PARAM_PROP_HOUSE_SIZE_FROM = "pHouseSizeFrom";
	public static final String PARAM_PROP_HOUSE_SIZE_TO = "pHouseSizeTo";
	public static final String PARAM_PROP_LAND_SIZE = "pLandSize";
	public static final String PARAM_PROP_LAND_SIZE_FROM = "pLandSizeFrom";
	public static final String PARAM_PROP_LAND_SIZE_TO = "pLandSizeTo";
	public static final String PARAM_PROP_PARKING= "pParking";
	public static final String PARAM_PROP_DISTANCE_TO_MAIN_RD= "pDistanceToMainRd";


	//---------------VEHICLE PROPERTIES-----------------------
	public static String VEHI_TRANSMISION_TYPE_MANUAL = "MANUAL";
	public static String VEHI_TRANSMISION_TYPE_AUTO = "AUTO";
	public static String VEHI_TRANSMISION_TYPE_TRIPONIC = "TRIPONIC";
	public static String VEHI_TRANSMISION_TYPE_OTHER = "OTHER";

	public static String VEHI_BODY_TYPE_HATCHBACK = "HATCHBACK";
	public static String VEHI_BODY_TYPE_SEDAN = "SEDAN";
	public static String VEHI_BODY_TYPE_MVP = "MVP";
	public static String VEHI_BODY_TYPE_SUV = "SUV";
	public static String VEHI_BODY_TYPE_CROSSOVER = "CROSSOVER";
	public static String VEHI_BODY_TYPE_COUPE = "COUPE";
	public static String VEHI_BODY_TYPE_CONVERTIABLE = "CONVERTIABLE";
	public static String VEHI_BODY_TYPE_OTHER= "OTHER";


	public static String VEHI_FUEL_TYPE_PETROL = "PERTOL";
	public static String VEHI_FUEL_TYPE_ELECTRIC = "ELECTRIC";
	public static String VEHI_FUEL_TYPE_HYBRID= "ELECTRIC";
	public static String VEHI_FUEL_TYPE_OTHER= "OTHER";

	public static String VEHI_CONDITION_NEW= "NEW";
	public static String VEHI_CONDITION_USED ="USED";
	public static String VEHI_CONDITION_RECONDITION ="RECONDITION";
	public static String VEHI_CONDITION_OTHER ="OTHER";
	//-----------------------------------------------------------------



	public static String USER_TYPE_STORE= "STORE";
	public static String USER_TYPE_CUSTOMER= "CUSTOMER";





}
