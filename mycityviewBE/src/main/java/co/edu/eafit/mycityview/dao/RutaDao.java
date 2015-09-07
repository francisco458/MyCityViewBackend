package co.edu.eafit.mycityview.dao;

import java.util.List;

import co.edu.eafit.mycityview.model.Location;
import co.edu.eafit.mycityview.model.RutaDTO;

public interface RutaDao {

	/**
	 * Consulta la rutas que tienen un punto cercano al punto del par�metro
	 * 
	 * @return
	 * @throws Exception
	 */
	List<RutaDTO> findRuta(Location location) throws Exception;

}
