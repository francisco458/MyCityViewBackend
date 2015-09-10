package co.edu.eafit.mycityview.dao;

import co.edu.eafit.mycityview.model.Location;
import co.edu.eafit.mycityview.model.RutaDTO;

import com.google.gson.JsonArray;

public interface RutaDao {

	/**
	 * Consulta la rutas que tienen un punto cercano al punto del par�metro
	 * 
	 * @return
	 * @throws Exception
	 */
	JsonArray findRuta(Location location) throws Exception;

	/**
	 * Consulta el listado de puntos que conforman la ruta seleccionada
	 * 
	 * @param identificadorRuta
	 * @return
	 * @throws Exception
	 */
	RutaDTO findRutaById(Long identificadorRuta) throws Exception;
}
