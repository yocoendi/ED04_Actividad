package clases;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;

public class Persona {

	private String nombre;
	private String apellidos;
	private String dni;
	private Date fecha_nacimiento;
	private String calle;

	private static final Pattern REGEXP = Pattern.compile("[0-9]{8}[A-Z]");
	private static final String DIGITO_CONTROL = "TRWAGMYFPDXBNJZSQVHLCKE";
	private static final String[] INVALIDOS = new String[] { "00000000T", "00000001R", "99999999R" };

//	(1) Lo primero que hacemos es asegurarnos de que el
// 	string no es ninguno de los DNI no válidos que fija
// 	el Ministerio de Interior.
//	  
// 	(2)Después, mediante una expresión regular, validamos
// 	que el string tenga 8 números y una letra al final
//	(en este punto, solo estamos validando que la cadena
//	de texto está bien formada).
//	  
//   (3)Para terminar, no basta con que el último dígito
//   sea una letra, tiene que cumplir que el último
//   carácter se corresponda con el dígito de control de
//   la tabla después de aplicar la operación módulo 23.
	

	public Persona(String nombre, String apellidos, String dni, Date fecha_nacimiento, String calle) {
		super();
		this.setNombre(nombre);
		this.setApellidos(apellidos);

		extracted(dni);

		this.setFecha_nacimiento(fecha_nacimiento);
		this.setCalle(calle);

	}

	private void extracted(String dni) {
		if (Arrays.binarySearch(INVALIDOS, dni) < 0 // (1)
				&& REGEXP.matcher(dni).matches() // (2)
				&& dni.charAt(8) == DIGITO_CONTROL.charAt(Integer.parseInt(dni.substring(0, 8)) % 23) // (3)
		) {
			this.setDni(dni);

		} else {
			throw new IllegalArgumentException("El DNI es incorrecto");
		}
	}

//Método que realiza la diferencia entre la fecha actual y la fecha de	nacimiento de la persona para obtener su edad

	public Integer obtenerEdad() {
		int year = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
		int year2 = getFecha_nacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
		return year- year2;
				
	}

	@Override
	public int hashCode() {
		return Objects.hash(getDni());
	}

	// Dos personas son iguales si tienen el mismo DNI
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return Objects.equals(getDni(), other.getDni());
	}

	// Método que muestra la información de una persona
	public String toString() {
		return "Persona [nombre=" + getNombre() + ", apellidos=" + getApellidos() + ", dni=" + getDni() + ", fecha_nacimiento="
				+ getFecha_nacimiento() + ", calle=" + getCalle() + "]";
	}

	private String getNombre() {
		return nombre;
	}

	private void setNombre(String nombre) {
		this.nombre = nombre;
	}

	private String getApellidos() {
		return apellidos;
	}

	private void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	private String getDni() {
		return dni;
	}

	private void setDni(String dni) {
		this.dni = dni;
	}

	private Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	private void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	private String getCalle() {
		return calle;
	}

	private void setCalle(String calle) {
		this.calle = calle;
	}

}
