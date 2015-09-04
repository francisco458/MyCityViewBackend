package co.edu.eafit.mycityview.model;

public class Constantes {

	public static class FormatoFecha {
		public static final String DMYHM_GUION = "dd-MM-yyyy-HH-mm";
	}

	
	public static class CodigoEstadoComucacion {
		/** Constante cuando el resultado es exitoso desde el WS. */
		public static final int RESULTADO_EXITOSO = 1;
		/** Constante cuando el proceso es exitoso pero no genera resultados desde el WS. */
		public static final int EXITOSO_SIN_RESULTADO = 2;
		/** Constante cuando sucede un error en la la ejecucion del caso de uso desde el WS. */
		public static final int ERROR_COMUNICACION = 3;
		/** Constante cuando sucede un error en la la ejecucion del caso de uso, por datos desde el WS. */
		public static final int ERROR_DATOS = 4;
		/** Constante cuando sucede un error de autenticacion. */
		public static final int ERROR_AUTENTICACION = 5;
		/** Constante cuando sucede un error tecnico */
		public static final int ERROR_TECNICO = 6;
	}
}
