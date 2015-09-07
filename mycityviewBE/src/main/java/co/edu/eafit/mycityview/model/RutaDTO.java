package co.edu.eafit.mycityview.model;

import java.util.ArrayList;
import java.util.List;

public class RutaDTO {
	private String nombreRuta;
	private List<Location> listLocation;

	public List<Location> getListLocation() {
		if (listLocation == null) {
			listLocation = new ArrayList<Location>();
		}
		return listLocation;
	}

	public String getNombreRuta() {
		return nombreRuta;
	}

	public void setListLocation(List<Location> listLocation) {
		this.listLocation = listLocation;
	}

	public void setNombreRuta(String nombreRuta) {
		this.nombreRuta = nombreRuta;
	}
}
