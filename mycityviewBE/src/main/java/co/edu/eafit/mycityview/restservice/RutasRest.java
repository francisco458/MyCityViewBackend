package co.edu.eafit.mycityview.restservice;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import co.edu.eafit.mycityview.business.RutaBusiness;
import co.edu.eafit.mycityview.model.Location;
import co.edu.eafit.mycityview.model.RutaDTO;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("ruta")
public class RutasRest {

	@Autowired
	private RutaBusiness rutaBusiness;

	@RequestMapping(value = "/consultar", method = RequestMethod.GET)
	@Secured("ROLE_MYCITYVIEW_REST_C")
	public ResponseEntity<String> consultarRutas(@RequestParam(value = "latitud", required = true) double latitud,
			@RequestParam(value = "longitud", required = true) double longitud) {
		ResponseEntity<String> responseEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {
			Location location = new Location();
			location.setLatitud(latitud);
			location.setLongitud(latitud);
			
			JsonArray jsonArray = new JsonArray();
//			jsonArray = rutaBusiness.findRutaByLocation(location);
			jsonArray = getJsonArrayExample();
			if(jsonArray.size() != 0){
				responseEntity = new ResponseEntity<String>(jsonArray.toString(), responseHeaders, HttpStatus.OK);
			}else{
				responseEntity = new ResponseEntity<String>(null, responseHeaders, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<String>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}
	
	
	@RequestMapping(value = "/{identificador}", method = RequestMethod.GET)
	@Secured("ROLE_MYCITYVIEW_REST_C")
	public ResponseEntity<RutaDTO> consultarRutaById(@PathVariable int identificador) {
		ResponseEntity<RutaDTO> responseEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {
			RutaDTO ruta = getRutaEjemplo();
			if(ruta != null){
				responseEntity = new ResponseEntity<RutaDTO>(ruta, responseHeaders, HttpStatus.OK);
			}else{
				responseEntity = new ResponseEntity<RutaDTO>(null, responseHeaders, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<RutaDTO>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	
	private RutaDTO getRutaEjemplo() {
		RutaDTO ruta = new RutaDTO();
		ruta.setIdRuta(1);
		ruta.setNombreRuta("Circular sur 303");
		List<Location> lista = new ArrayList<Location>();
		
		Location loc = new Location();
		loc.setLatitud(6.1569809);
		loc.setLongitud(-75.6038274);
		lista.add(loc);
		
		loc = new Location();
		loc.setLatitud(6.160111199999999);
		loc.setLongitud(-75.6021118);
		lista.add(loc);
		
		loc = new Location();
		loc.setLatitud(6.1580352);
		loc.setLongitud(-75.6043071);
		lista.add(loc);
		ruta.setListLocation(lista);
		return ruta;
	}


	private JsonArray getJsonArrayExample(){
		JsonArray jsonArray = new JsonArray();
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("nombreRuta", "Circular sur 303");
		jsonObject.addProperty("idRuta", 1);
		jsonArray.add(jsonObject);
		
		jsonObject = new JsonObject();
		jsonObject.addProperty("nombreRuta", "Coonatra 150");
		jsonObject.addProperty("idRuta", 2);
		jsonArray.add(jsonObject);
		
		jsonObject = new JsonObject();
		jsonObject.addProperty("nombreRuta", "Prado-Medellin");
		jsonObject.addProperty("idRuta", 3);
		jsonArray.add(jsonObject);
		return jsonArray;
	}
	

	@ExceptionHandler(AccessDeniedException.class)
	protected @ResponseBody
	ResponseEntity<String> handleAccessDeniedException(AccessDeniedException accessDeniedException, HttpServletRequest request,
			HttpServletResponse response) {
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}
}