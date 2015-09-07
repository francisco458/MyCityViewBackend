package co.edu.eafit.mycityview.business;

import java.util.List;

import co.edu.eafit.mycityview.model.Location;
import co.edu.eafit.mycityview.model.RutaDTO;

public interface RutaBusiness {

	/**
	 * Consulta las rutas que tienen un punto cercano con el location
	 * 
	 * @param location
	 * @return
	 */
	List<RutaDTO> findRutaByLocation(Location location);

}
