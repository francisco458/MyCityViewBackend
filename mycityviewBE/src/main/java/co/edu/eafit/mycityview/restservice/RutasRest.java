package co.edu.eafit.mycityview.restservice;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import co.edu.eafit.mycityview.business.RutaBusiness;
import co.edu.eafit.mycityview.model.Location;

import com.google.gson.JsonArray;

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
			jsonArray = rutaBusiness.findRutaByLocation(location);
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

	@ExceptionHandler(AccessDeniedException.class)
	protected @ResponseBody
	ResponseEntity<String> handleAccessDeniedException(AccessDeniedException accessDeniedException, HttpServletRequest request,
			HttpServletResponse response) {
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}
}