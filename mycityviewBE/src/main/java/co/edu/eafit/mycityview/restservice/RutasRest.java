package co.edu.eafit.mycityview.restservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.google.gson.JsonArray;

import co.edu.eafit.mycityview.model.Location;
import co.edu.eafit.mycityview.model.RutaDTO;

@Controller
@RequestMapping("ruta")
public class RutasRest {

	@RequestMapping(value = "/consultar", method = RequestMethod.GET)
	@Secured("ROLE_MYCITYVIEW_REST_C")
	public ResponseEntity<String> consultarRutas(@RequestParam(value = "latitud", required = true) String latitud, @RequestParam(value = "longitud", required = true) String longitud){
		ResponseEntity<String> responseEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		JsonArray jsonArray = new JsonArray();
		responseEntity = new ResponseEntity<String>(jsonArray.toString(), responseHeaders, HttpStatus.OK);
		return responseEntity;
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	protected @ResponseBody
	ResponseEntity<String> handleAccessDeniedException(AccessDeniedException accessDeniedException, HttpServletRequest request,
			HttpServletResponse response) {
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}
}