package co.edu.eafit.mycityview.business;

import co.edu.eafit.mycityview.model.Location;

import com.google.gson.JsonArray;

public interface RutaBusiness {

	/**
	 * Consulta las rutas que tienen un punto cercano con el location
	 * 
	 * @param location
	 * @return
	 */
	JsonArray findRutaByLocation(Location location);

}
