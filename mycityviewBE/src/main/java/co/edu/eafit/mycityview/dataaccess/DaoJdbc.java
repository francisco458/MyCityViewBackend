package co.edu.eafit.mycityview.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

@Repository
public class DaoJdbc implements Dao {

	private String _usuario = "root";
	private String _pwd = "root";
	private static String _bd = "mysql";
	static String _url = "jdbc:mysql://localhost/" + _bd;
	
	public static void main(String[] args) {
		DaoJdbc conexion = new DaoJdbc();
		ResultSet resultado;
		String nombres;

		resultado = conexion.getQuery("select user, host from user");

		try {
			if (resultado != null) {
				while (resultado.next()) {
					nombres = resultado.getString("user");
					System.out.println("nombre: " + nombres);
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
			conn = (Connection) DriverManager.getConnection(_url, _usuario, _pwd);
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

	/*
	 * (non-Javadoc)
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