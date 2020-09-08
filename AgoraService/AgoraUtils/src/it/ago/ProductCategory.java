package it.ago;

import it.ago.utils.DBConnection;
import it.ago.utils.db.Savable;

import javax.xml.bind.annotation.XmlType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "ProductCategory", namespace = "http://lass")
public class ProductCategory extends Savable
{
    private String categoryName;
    private String productCode;
    private int categoryId;
    int status;
    List<VehicleType> vehicleTypes;
    public ProductCategory()
    {
    	vehicleTypes = new ArrayList<>(  );
    }

    public void checkValidity() throws SQLException
    {
    }

    /**
     * This insert/modify or update depending on the action
     */
    public void save( Connection con ) throws SQLException
    {
        String action = "";
        try
        {
            if ( this.status == Savable.NEW )
            {
                action = "Inserting";
                checkValidity();
                insert( con );
            }
            else if ( this.status == Savable.MODIFIED )
            {
                action = "Updating";
                checkValidity();
                update( con );
            }
            else if ( this.status == Savable.DELETED )
            {
                action = "Deleting";
                checkValidity();
                delete( con );
            }
            else if ( this.status == Savable.UNCHANGED )
            {
                //Do nothing
            }
            else
            {
                throw new SQLException( "Incorret setting of Status flag!" );
            }
        }
        catch ( SQLException se )
        {
            se.printStackTrace();
            throw new SQLException( "Error in " + action +
                    se.getMessage(),
                    se.getSQLState(),
                    se.getErrorCode() );
        }
    }

    /**
     * This inserts the .........
     */
    private void insert( Connection con ) throws SQLException
    {
        String str = "INSERT INTO PRODUCT_CATEGORY ( "
                + "PRODUCT_CODE, "
                + "CATEGORY_NAME )VALUES(?,? )";
        int count = 0;
        PreparedStatement ps = con.prepareStatement( str );
        ps.setString( ++count, this.productCode );
        ps.setString( ++count, this.categoryName );
        ps.execute();
        DBConnection.close( ps );
    }

    /**
     * This deletes the ........
     */
    public void delete( Connection con ) throws SQLException
    {
        String str = "DELETE FROM PRODUCT_CATEGORY WHERE "
                + "CATEGORY_ID = ? ";

        int count = 0;
        PreparedStatement ps = con.prepareStatement( str );
        ps.setInt( ++count, this.categoryId );
        ps.execute();
        DBConnection.close( ps );
    }

    /**
     * This updates the .......
     */
    public void update( Connection con ) throws SQLException
    {
        String str = "UPDATE PRODUCT_CATEGORY SET "
                + "PRODUCT_CODE = ?,CATEGORY_NAME = ? WHERE "
                + "CATEGORY_ID = ? ";

        int count = 0;
        PreparedStatement ps = con.prepareStatement( str );
        ps.setString( ++count, this.productCode );
        ps.setString( ++count, this.categoryName );
        ps.execute();
        DBConnection.close( ps );
    }

    /**
     * This loads the .......
     */
    public void load( Connection con, ResultSet rs, int level ) throws SQLException
    {
        this.status = Savable.UNCHANGED;
        this.productCode = rs.getString( "PRODUCT_CODE" );
        this.categoryName = rs.getString( "CATEGORY_NAME" );
        this.categoryId = rs.getInt( "CATEGORY_ID" );

        if( level > 0 )
		{
			if( Constants.ADV_PROD_VEHICLE.equals( this.productCode ))
			{
				loadVehicleTypes( con, level );
			}
		}

    }
	private void loadVehicleTypes( Connection con, int level )
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			ps = con.prepareStatement( "SELECT * FROM VEHICLE_TYPE WHERE TYPE_ID = ?");
			ps.setInt( 1, this.categoryId );
			rs = ps.executeQuery();
			while (rs.next())
			{
				VehicleType vehicleType = new VehicleType();
				vehicleType.load(rs, con, level);
				vehicleTypes.add(vehicleType);
			}

		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			DBConnection.close(rs);
			DBConnection.close(ps);
		}

	}
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }

	public List<VehicleType> getVehicleTypes()
	{
		return vehicleTypes;
	}

	public void setVehicleTypes( List<VehicleType> vehicleTypes )
	{
		this.vehicleTypes = vehicleTypes;
	}
}
