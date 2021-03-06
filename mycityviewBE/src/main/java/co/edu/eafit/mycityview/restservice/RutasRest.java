package co.edu.eafit.mycityview.restservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

@Controller
@RequestMapping("ruta")
public class RutasRest {

	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private RutaBusiness rutaBusiness;

	/**
	 * Realiza la consulta de las rutas cercanas al punto seleccionado
	 * 
	 * @param latitud
	 * @param longitud
	 * @return
	 */
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
			location.setLongitud(longitud);

			JsonArray jsonArray = new JsonArray();
			jsonArray = rutaBusiness.findRutaByLocation(location);
			if (jsonArray.size() != 0) {
				responseEntity = new ResponseEntity<String>(jsonArray.toString(), responseHeaders, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<String>(null, responseHeaders, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<String>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
			logger.error(e.getMessage(), e);
		}

		return responseEntity;
	}

	/**
	 * Realiza la consulta de los puntos que conforman la ruta del identificador
	 * 
	 * @param identificador
	 * @return
	 */
	@RequestMapping(value = "/{identificador}", method = RequestMethod.GET)
	@Secured("ROLE_MYCITYVIEW_REST_C")
	public ResponseEntity<RutaDTO> consultarRutaById(@PathVariable long identificador) {
		ResponseEntity<RutaDTO> responseEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {
			RutaDTO ruta = rutaBusiness.findRutaById(identificador);
			if (ruta != null) {
				responseEntity = new ResponseEntity<RutaDTO>(ruta, responseHeaders, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<RutaDTO>(null, responseHeaders, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<RutaDTO>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
			logger.error(e.getMessage(), e);
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