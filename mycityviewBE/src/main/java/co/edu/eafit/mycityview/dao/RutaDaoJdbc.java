package co.edu.eafit.mycityview.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.edu.eafit.mycityview.model.RutaDTO;

@Repository
public class RutaDaoJdbc {

	@Autowired
	private MyDataAcces myDataAcces;

	public List<RutaDTO> findRuta() throws Exception {
		ArrayList<RutaDTO> listRutas = new ArrayList<RutaDTO>();
		ResultSet resultSet = myDataAcces.getQuery("");
		if (resultSet != null) {
			while (resultSet.next()) {
				RutaDTO ruta = new RutaDTO();
				ruta.setNombreRuta(resultSet.getString(""));
				
				listRutas.add(ruta);
			}
		}
		return listRutas;

	}
}