package co.edu.eafit.mycityview.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

@Repository
public class DaoJdbc implements Dao {

	private String _usuario = "app_user";
	private String _pwd = "mypass";
	private static String _bd = "mycityviewdb";
	static String _url = "jdbc:mysql://localhost/" + _bd;

	public static void main(String[] args) {
		DaoJdbc conexion = new DaoJdbc();
		ResultSet resultado;
		String latitud;
		String longitud;

		// resultado = conexion.getQuery("select user, host from user");
		resultado = conexion.getQuery("select * from mycityviewdb.coordenada where idruta = ? order by IDCOORDENADA", 159l);

		try {
			if (resultado != null) {
				int index = 1;
				while (resultado.next()) {
					latitud = resultado.getString("latitud");
					longitud = resultado.getString("longitud");

					System.out.println(index++ + "Coordenadas: " + latitud + ", " + longitud);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Connection conn = null;

	public DaoJdbc() {

		try {
			Class.forName("com.mysql.jdbc.Connection");
			conn = DriverManager.getConnection(_url, _usuario, _pwd);
			if (conn != null) {
				System.out.println("Conexion a base de datos " + _url + " . . . Ok");
			}
		} catch (SQLException ex) {
			System.out.println("Hubo un problema al intentar conectarse a la base de datos" + _url);
			System.out.println(ex.getMessage());
		} catch (ClassNotFoundException ex) {
			System.out.println(ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.eafit.mycityview.dataaccess.Dao#getQuery(java.lang.String)
	 */
	@Override
	public ResultSet getQuery(String sql) {
		Statement state = null;
		ResultSet resultado = null;
		try {
			if (conn != null) {
				state = (Statement) conn.createStatement();
				resultado = state.executeQuery(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return resultado;
	}
	public ResultSet getQuery(String sql, Long va) {
		PreparedStatement preparedStatement = null;
		ResultSet resultado = null;
		try {
			if (conn != null) {
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setLong(1, va);
				resultado = preparedStatement.executeQuery();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.eafit.mycityview.dataaccess.Dao#setQuery(java.lang.String)
	 */
	@Override
	public void setQuery(String _query) {
		Statement state = null;
		try {
			if (conn != null) {
				state = (Statement) conn.createStatement();
				state.execute(_query);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}