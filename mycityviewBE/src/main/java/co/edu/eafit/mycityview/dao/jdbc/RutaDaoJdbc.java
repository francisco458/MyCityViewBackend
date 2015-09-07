package co.edu.eafit.mycityview.dao.jdbc;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.edu.eafit.mycityview.dao.MyDataAcces;
import co.edu.eafit.mycityview.dao.RutaDao;
import co.edu.eafit.mycityview.model.Location;
import co.edu.eafit.mycityview.model.RutaDTO;

@Repository
public class RutaDaoJdbc implements RutaDao {

	@Autowired
	private MyDataAcces myDataAcces;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.eafit.mycityview.dao.RutaDao#findRuta(co.edu.eafit.mycityview.
	 * model.Location)
	 */
	@Override
	public List<RutaDTO> findRuta(Location location) throws Exception {
		ArrayList<RutaDTO> listRutas = new ArrayList<RutaDTO>();
		ResultSet resultSet = myDataAcces.getQuery("select user, host from user");
		if (resultSet != null) {
			while (resultSet.next()) {
				RutaDTO ruta = new RutaDTO();
				ruta.setNombreRuta(resultSet.getString("user"));

				listRutas.add(ruta);
			}
		}
		return listRutas;
	}
	
}