package co.edu.eafit.mycityview.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

@Repository

public class MyDataAcces {

	private String _usuario = "root";
	private String _pwd = "root";
	private static String _bd = "mysql";
	static String _url = "jdbc:mysql://localhost/" + _bd;
	private Connection conn = null;

	public MyDataAcces() {

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

	public ResultSet getQuery(String _query) {
		Statement state = null;
		ResultSet resultado = null;
		try {
			if (conn != null) {
				state = (Statement) conn.createStatement();
				resultado = state.executeQuery(_query);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return resultado;
	}

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

	public static void main(String[] args) {
		MyDataAcces conexion = new MyDataAcces();
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
}