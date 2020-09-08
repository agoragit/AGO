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

@XmlType(name = "Product", namespace = "http://lass")
public class Product extends Savable
{
	private String code;
	private String name;
	private int status;
	private List<ProductCategory> productCategoryList;

	public Product()
	{
		productCategoryList = new ArrayList<>();
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
		String str = "INSERT INTO PRODUCT ( "
				+ "PRODUCT_CCODE, "
				+ "PRODUCT_CNAME )VALUES(?,? )";
		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setString( ++count, this.code );
		ps.setString( ++count, this.name );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This deletes the ........
	 */
	public void delete( Connection con ) throws SQLException
	{
		String str = "DELETE FROM PRODUCT WHERE "
				+ "PRODUCT_CCODE = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setString( ++count, this.code );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This updates the .......
	 */
	public void update( Connection con ) throws SQLException
	{
		String str = "UPDATE PRODUCT SET "
				+ "PRODUCT_CNAME = ? WHERE "
				+ "PRODUCT_CCODE = ? ";

		int count = 0;
		PreparedStatement ps = con.prepareStatement( str );
		ps.setString( ++count, this.name );
		ps.setString( ++count, this.code );
		ps.execute();
		DBConnection.close( ps );
	}

	/**
	 * This loads the .......
	 */
	public void load( ResultSet rs, Connection con, int level ) throws SQLException
	{
		this.status = Savable.UNCHANGED;
		this.code = rs.getString( "PRODUCT_CODE" );
		this.name = rs.getString( "PRODUCT_NAME" );

		if(level > 0 )
		{
			loadProductCategories(con);
		}
	}

	public String getCode()
	{
		return this.code;
	}

	public void setCode( String code )
	{
		this.code = code;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public int getStatus()
	{
		return this.status;
	}

	public void setStatus( int status )
	{
		this.status = status;
	}

	public List<ProductCategory> getProductCategoryList()
	{
		return productCategoryList;
	}

	public void setProductCategoryList( List<ProductCategory> productCategoryList )
	{
		this.productCategoryList = productCategoryList;
	}

	private void loadProductCategories(Connection con)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			ps = con.prepareStatement( "SELECT * FROM PRODUCT_CATEGORY WHERE PRODUCT_CODE=?" );
			ps.setString( 1, this.code );
			rs = ps.executeQuery();
			while (rs.next())
			{
				ProductCategory productCategory = new ProductCategory();
				productCategory.load(rs);
				productCategoryList.add(productCategory);
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

}
