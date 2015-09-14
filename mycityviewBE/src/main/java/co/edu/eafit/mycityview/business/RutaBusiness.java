package co.edu.eafit.mycityview.business;

import co.edu.eafit.mycityview.model.Location;
import co.edu.eafit.mycityview.model.RutaDTO;

import com.google.gson.JsonArray;

public interface RutaBusiness {

	/**
	 * Realiza la consulta de los puntos de las rutas por id de ruta
	 * 
	 * @param identificadorRuta
	 * @return
	 */
	RutaDTO findRutaById(Long identificadorRuta) throws Exception;

	/**
	 * Consulta las rutas que tienen un punto cercano con el location
	 * 
	 * @param location
	 * @return
	 * @throws Exception
	 */
	JsonArray findRutaByLocation(Location location) throws Exception;

}
