package co.edu.eafit.mycityview.dao.jdbc;

import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.edu.eafit.mycityview.dao.MyDataAcces;
import co.edu.eafit.mycityview.dao.RutaDao;
import co.edu.eafit.mycityview.model.Location;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Repository
public class RutaDaoJdbc implements RutaDao {

	@Autowired
	private MyDataAcces myDataAcces;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.eafit.mycityview.dao.RutaDao#findRuta(co.edu.eafit.mycityview. model.Location)
	 */
	@Override
	public JsonArray findRuta(Location location) throws Exception {
		JsonArray jsonArray = new JsonArray();
		JsonObject jsonObject = null;
		ResultSet resultSet = myDataAcces.getQuery("select user, host from user");
		if (resultSet != null) {
			while (resultSet.next()) {
				jsonObject = new JsonObject();
				jsonObject.addProperty("ruta", resultSet.getString("user") + " " + resultSet.getString("host"));
				jsonArray.add(jsonObject);
			}
		}
		return jsonArray;
	}

}