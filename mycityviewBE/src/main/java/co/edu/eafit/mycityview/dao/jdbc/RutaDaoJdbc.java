package co.edu.eafit.mycityview.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import co.edu.eafit.mycityview.dao.RutaDao;
import co.edu.eafit.mycityview.model.Location;
import co.edu.eafit.mycityview.model.RutaDTO;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Repository
public class RutaDaoJdbc implements RutaDao {

	/**
	 * Data source reference which will be provided by Spring.
	 */
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	private StringBuilder sqlFindRutasCercacas = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.eafit.mycityview.dao.RutaDao#findRuta(co.edu.eafit.mycityview. model.Location)
	 */
	@Override
	public JsonArray findRuta(Location location) throws Exception {
		JsonArray jsonArray = new JsonArray();
		JsonObject jsonObject = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		try {
			Connection conn = dataSource.getConnection();

			if (sqlFindRutasCercacas == null) {
				sqlFindRutasCercacas = new StringBuilder();
				sqlFindRutasCercacas.append("SELECT  ");
				sqlFindRutasCercacas.append("    DISTINCT * ");
				sqlFindRutasCercacas.append("FROM ");
				sqlFindRutasCercacas.append("    (SELECT  ");
				sqlFindRutasCercacas.append("        get_route_near(co.LATITUD, co.LONGITUD, ?, ?, co.idruta) idruta ");
				sqlFindRutasCercacas.append("    FROM ");
				sqlFindRutasCercacas.append("        coordenada co) rutas ");
				sqlFindRutasCercacas.append("        INNER JOIN ");
				sqlFindRutasCercacas.append("    maestroruta ma ON ma.IDRUTA = rutas.IDRUTA ");
			}
			ps = conn.prepareStatement(sqlFindRutasCercacas.toString());
			int index = 1;
			ps.setDouble(index++, location.getLatitud());
			ps.setDouble(index++, location.getLongitud());
			resultSet = ps.executeQuery();

			if (resultSet != null) {
				while (resultSet.next()) {
					jsonObject = new JsonObject();
					jsonObject.addProperty("nombreRuta", resultSet.getString("RUTAOT") + "-" + resultSet.getString("descripcion"));
					jsonObject.addProperty("idRuta", resultSet.getString("idRuta"));
					jsonArray.add(jsonObject);
				}
			}
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e) {
				}
			}
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (Exception e) {
				}
			}
		}
		return jsonArray;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.eafit.mycityview.dao.RutaDao#findRutaById(java.lang.Long)
	 */
	@Override
	public RutaDTO findRutaById(Long identificadorRuta) throws Exception {
		RutaDTO rutaDTO = null;
		List<Location> listaLocations = null;
		Location location = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;

		try {
			Connection conn = dataSource.getConnection();
			String sql = "select IDCOORDENADA, IDRUTA, LONGITUD, LATITUD, PRIMERPUNTO, ULTIMOPUNTO from coordenada where idruta = ? order by IDCOORDENADA";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, identificadorRuta);
			resultSet = ps.executeQuery();

			if (resultSet != null) {
				rutaDTO = new RutaDTO();
				listaLocations = new ArrayList<Location>();
				while (resultSet.next()) {
					location = new Location();
					location.setLatitud(resultSet.getDouble("LATITUD"));
					location.setLongitud(resultSet.getDouble("LONGITUD"));

					rutaDTO.setIdRuta(resultSet.getLong("idRuta"));
					listaLocations.add(location);
				}
				rutaDTO.setListLocation(listaLocations);
			}
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e) {
				}
			}
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (Exception e) {
				}
			}
		}
		return rutaDTO;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
