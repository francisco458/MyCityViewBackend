package co.edu.eafit.mycityview.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.eafit.mycityview.business.RutaBusiness;
import co.edu.eafit.mycityview.dao.RutaDao;
import co.edu.eafit.mycityview.model.Location;

import com.google.gson.JsonArray;

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
	public JsonArray findRutaByLocation(Location location) {
		JsonArray jsonArray = null;
		try {
			jsonArray = rutaDao.findRuta(location);
		} catch (Exception e) {

		}
		return jsonArray;
	}
}
