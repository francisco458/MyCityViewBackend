package co.edu.eafit.mycityview.restservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import co.edu.eafit.mycityview.model.RestResponse;
import co.edu.eafit.mycityview.model.Constantes.CodigoEstadoComucacion;

@Controller
@RequestMapping("ruta")
public class RutasRest {

	@RequestMapping(value = "/{latitud}/{longitud}", method = RequestMethod.GET)
	@Secured("ROLE_MYCITYVIEW_REST_C")
	public @ResponseBody RestResponse consultarRutas(@PathVariable String latitud, @PathVariable String longitud){
		RestResponse response = new RestResponse();
		response.setCode(CodigoEstadoComucacion.RESULTADO_EXITOSO);
		return response;
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	protected @ResponseBody
	ResponseEntity<String> handleAccessDeniedException(AccessDeniedException accessDeniedException, HttpServletRequest request,
			HttpServletResponse response) {
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}
}