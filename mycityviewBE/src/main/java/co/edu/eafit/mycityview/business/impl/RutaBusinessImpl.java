package co.edu.eafit.mycityview.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.eafit.mycityview.business.RutaBusiness;
import co.edu.eafit.mycityview.dao.RutaDao;
import co.edu.eafit.mycityview.model.Location;
import co.edu.eafit.mycityview.model.RutaDTO;

@Service("RutaBusiness")
public class RutaBusinessImpl implements RutaBusiness {

	@Autowired
	private RutaDao rutaDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.eafit.mycityview.business.RutaBusiness#findRutaByLocation(co.edu
	 * .eafit.mycityview.model.Location)
	 */
	@Override
	public List<RutaDTO> findRutaByLocation(Location location) {
		List<RutaDTO> listRutas = new ArrayList<RutaDTO>();
		try {
			listRutas = rutaDao.findRuta(location);
		} catch (Exception e) {

		}
		return listRutas;
	}
}
