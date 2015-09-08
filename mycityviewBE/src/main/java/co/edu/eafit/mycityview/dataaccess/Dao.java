package co.edu.eafit.mycityview.dataaccess;

import java.sql.ResultSet;

public interface Dao {

	/**
	 * Retorna el resultSet con los datos del sql
	 * 
	 * @param sql
	 * @return
	 */
	ResultSet getQuery(String sql);

	/**
	 * Realiza operaciones insert, update y delete
	 * 
	 * @param sql
	 */
	void setQuery(String sql);

}
