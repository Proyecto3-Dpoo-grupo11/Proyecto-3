package logica;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;

import persistencia.LectorArchivo;

/**
 * <!-- ACA VA DOCUMENTACION -->
 *  Se usa la interfaz Serializable para que los objetos pueden ser convertidos en una secuencia de bytes 
 *  y luego reconstruidos nuevamente a partir de esa secuencia de bytes, para la persistencia. -->
 */

public class EmpresaAlquiler implements Serializable
{
	private static final long serialVersionUID = 1L; //este es el contrato que cumple del implement
	public static HashMap<String, Usuario> mapaUsuarios;
	static ArrayList<Sede> listaSedes;
	
	/**
	 * <!-- CONSTRUCTOR -->
	 */
	
	public EmpresaAlquiler() {
		EmpresaAlquiler.listaSedes = new  ArrayList<Sede>();
		EmpresaAlquiler.mapaUsuarios = new HashMap<String, Usuario>();
	}
	
	/**
	 * <!-- METODO PARA LA CONSOLA -->
	 */

	public void leerArchivos() {
		
		Usuario u= new AdminGeneral("admin","1234",Roles.ADMINISTRADORGENERAL);
		EmpresaAlquiler.mapaUsuarios.put("admin",u);
		
		//TODO IMPLEMENTAR SEDES
		ArrayList<String> lineas;
		lineas = LectorArchivo.leer("sedes.dat");
		for(String linea : lineas) {
			String []datos = linea.split(";");
			ArrayList<Vehiculo> listaVehiculos = new ArrayList<Vehiculo>();
			HashMap<String,Reserva> listaReservas= new HashMap<String,Reserva>();
			Sede sede = new Sede(datos[0], datos[1], datos[2],listaVehiculos,listaReservas); 
			listaSedes.add(sede);
		}
		
		
		lineas = LectorArchivo.leer("empleados.dat");
		for(String linea : lineas) {
			String []datos = linea.split(";");
			
			String codigoSedeBuscada = datos[2]; // Código de la sede que buscamos
			Sede sedeEspecifica = null;
			
			//algoritmo para buscar la sede
			for (Sede sede : listaSedes) {
			    if (sede.getCodigoSede().equals(codigoSedeBuscada)) {
			        sedeEspecifica = sede;
			    }
			}
			
			Usuario user = new Empleado((datos[0]), datos[1], (datos[2]), Roles.EMPLEADO, sedeEspecifica);
			Empleado userEmpleado = new Empleado((datos[0]), datos[1], (datos[2]), Roles.EMPLEADO, sedeEspecifica);//SE LLAMA AL CONSTRUCTOR DE EMPLEADO 		
			EmpresaAlquiler.mapaUsuarios.put(datos[0], user);
			for(Sede sedes : EmpresaAlquiler.listaSedes) {
				if (sedes.codigoSede==datos[2]) {
					sedes.mapaEmpleados.put(datos[0], userEmpleado);
				}
			}
				//SE GUARDA EN EL MAPA EL USERNAME Y EL USUARIO 		
		}//ACA HAY QUE GUARDARLL EN LA SEDE QUE PERTENECE.


		lineas = LectorArchivo.leer("administradores.dat"); 
		for(String linea : lineas) {
			String []datos = linea.split(";");
			Usuario user = new AdminSede(datos[0], datos[1], (datos[2]), Roles.ADMINISTRADORSEDE); // Polimorfismo porque creamos un usuario U pero como un adminsitrador Sede
			EmpresaAlquiler.mapaUsuarios.put(datos[0], user);			
		}
	
//valuesMapa.Usuarios.Cliente.nombre
		
		//TODO IMPLEMENTAR
		lineas = LectorArchivo.leer("clientes.dat");
		for(String linea : lineas) {
			String []datos = linea.split(";");
			Usuario user = new Cliente(datos[0], datos[1], Roles.CLIENTE, datos[2], datos[3], datos[4], datos[5], datos[6], datos[7], datos[8], datos[9], datos[10], datos[11]); 
			EmpresaAlquiler.mapaUsuarios.put(datos[0], user);		
		}
		
		
			
		
	}
		
	public String infoCarroEspecifico(String placa) {
		String retorno="";
		for (Sede sedes : EmpresaAlquiler.listaSedes) {
			for (Vehiculo vehiculo : sedes.listaVehiculos) {
				if (vehiculo.placa == placa) {
					retorno=(String.format("el carro de placa %s, esta en la sede%s.", placa, vehiculo.getCodigoSede()));
				} else {
					retorno="no existe este vehiculo";
				}
			}
		}
		// Busquedas, guardan variables
		return retorno;

	}
		
		public void crearCliente(String username, String password, Roles cargo, String nombreCliente, String email, String telefono, String fechaNacimiento, String nacionalidad, String imagenCedula,String imagenLicencia, String metodoDePago, String numeroTarjeta, String fechaVencimiento) {
			Usuario u = new Cliente(username,password,cargo,nombreCliente,email,telefono,fechaNacimiento,nacionalidad,imagenCedula,imagenCedula,metodoDePago,numeroTarjeta,fechaVencimiento);
			EmpresaAlquiler.mapaUsuarios.put(username, u);
			// TODO implement me
				
		}

		
	/**
	 * <!-- GETTERS SETTERS -->
	 */

	public ArrayList<Sede> getListaSedes() {
		return listaSedes;
	}

	public void setListaSedes(ArrayList<Sede> listaSedes) {
		EmpresaAlquiler.listaSedes = listaSedes;
	}

	public HashMap<String, Usuario> getMapaUsuarios() {
		return mapaUsuarios;
	}

	public static void setMapaUsuarios(HashMap<String, Usuario> mapaUsuarios) {
		EmpresaAlquiler.mapaUsuarios = mapaUsuarios;
	}

	
	
}
	


