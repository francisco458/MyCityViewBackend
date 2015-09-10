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

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.eafit.mycityview.dao.RutaDao#findRuta(co.edu.eafit.mycityview. model.Location)
	 */
	@Override
	public JsonArray findRuta(Location location) throws Exception {
		JsonArray jsonArray = new JsonArray();
		JsonObject jsonObject = null;
		Connection conn = dataSource.getConnection();
		String sql = "select * from mycityviewdb.maestroruta";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet resultSet = ps.executeQuery();

		if (resultSet != null) {
			while (resultSet.next()) {
				jsonObject = new JsonObject();
				jsonObject.addProperty("nombreRuta", resultSet.getString("descripcion"));
				jsonObject.addProperty("idRuta", resultSet.getString("idRuta"));
				jsonArray.add(jsonObject);
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

		Connection conn = dataSource.getConnection();
		String sql = "select IDCOORDENADA, IDRUTA, LONGITUD, LATITUD, PRIMERPUNTO, ULTIMOPUNTO from mycityviewdb.coordenada where idruta = ? order by IDCOORDENADA";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, identificadorRuta);
		ResultSet resultSet = ps.executeQuery();

		if (resultSet != null) {
			rutaDTO = new RutaDTO();
			listaLocations = new ArrayList<Location>();
			while (resultSet.next()) {
				location = new Location();
				location.setLatitud(resultSet.getLong("idRuta"));
				location.setLongitud(resultSet.getLong("idRuta"));

				rutaDTO.setIdRuta(resultSet.getLong("idRuta"));
				listaLocations.add(location);
			}
			rutaDTO.setListLocation(listaLocations);
		}
		return rutaDTO;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
