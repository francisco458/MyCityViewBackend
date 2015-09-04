package co.edu.eafit.mycityview.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import co.edu.eafit.mycityview.model.Constantes.FormatoFecha;
import co.edu.eafit.mycityview.util.Util;


/**
 * Interceptor que se encarga de validar el usuario y clave de servicio rest y sus roles<br>
 * Creado el 21/07/2014 a las 13:53:43 <br>
 * 
 * @author <a href="http://www.quipux.com/">Quipux Software.</a></br>
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	private AuthenticationManager authenticationManager;
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);
	private final static String PASSPHRASE = "deismasintegracion";
	private final static int MINUTOS_DIFERENCIA_AUTENTICACION = 10;

//	@Autowired
//	private SeguridadManager seguridadManager;

	/*
	 * Decodifica la informacion de la autorizacion
	 */
	public String[] decodeHeader(String authorization) {
		EncryptClass encrypt = new EncryptClass(PASSPHRASE);
		String credentials = new String(encrypt.decrypt(authorization));
		return credentials.split(":");
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	/*
	 * Obtiene los recursos asignados
	 */
	private List<GrantedAuthority> getGrantedAuthorities(List<String> recursos) {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		for (String role : recursos) {
			authList.add(new SimpleGrantedAuthority(role));
		}
		return authList;
	}

	/**
	 * Se ejecuta antes de llegar al servicio consumido
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try {
			String authorization = request.getHeader("Authorization");
			if (authorization != null) {
				String[] credentials = decodeHeader(authorization);
				if (credentials.length == 3) {
					// validacion de la hora de ejecucion
					Date fechaCliente = Util.stringToDate(credentials[2], FormatoFecha.DMYHM_GUION);
					if (Util.getMinutesBetweenDates(fechaCliente, new Date()) < MINUTOS_DIFERENCIA_AUTENTICACION) {
						credentials[1] = Sha.hash256(credentials[1]);
						// validacion de usuario
						List<GrantedAuthority> userRoles = getGrantedAuthorities(getRecursos());
						Authentication authentication = new UsernamePasswordAuthenticationToken(credentials[0], credentials[1], userRoles);
						SecurityContextHolder.getContext().setAuthentication(authentication);
						return true;
					}
				}
			}
			((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			((HttpServletResponse) response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			((HttpServletResponse) response).getWriter().append(e.getMessage());
		}
		return false;
	}

	private List<String> getRecursos() {
		List<String> recursos = new ArrayList<String>();
		recursos.add("ROLE_MYCITYVIEW_REST_C");
		return recursos;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

}
