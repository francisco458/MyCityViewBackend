package co.edu.eafit.mycityview.dao;

import co.edu.eafit.mycityview.model.Location;

import com.google.gson.JsonArray;

public interface RutaDao {

	/**
	 * Consulta la rutas que tienen un punto cercano al punto del parámetro
	 * 
	 * @return
	 * @throws Exception
	 */
	JsonArray findRuta(Location location) throws Exception;

}
