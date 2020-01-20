package it.ago.utils.db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class Savable<T> implements Serializable
{
	public static final int MODIFIED = 10000;
	public static final int NEW = 20000;
	public static final int DELETED = 30000;
	public static final int UNCHANGED = 40000;
	private static final long serialVersionUID = 1168475449064921169L;
	protected int saveStatus;

	public Savable()
	{
	}

	public int getStatus()
	{
		return this.saveStatus;
	}

	public void setStatus( int status )
	{
		this.saveStatus = status;
	}

	public abstract void save( Connection con ) throws SQLException;

	public abstract void update( Connection con ) throws SQLException;

	public abstract void delete( Connection con ) throws SQLException;

}
