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
		this.nombre = nombre;
		this.apellidos = apellidos;

		if (Arrays.binarySearch(INVALIDOS, dni) < 0 // (1)
				&& REGEXP.matcher(dni).matches() // (2)
				&& dni.charAt(8) == DIGITO_CONTROL.charAt(Integer.parseInt(dni.substring(0, 8)) % 23) // (3)
		) {
			this.dni = dni;

		} else {
			throw new IllegalArgumentException("El DNI es incorrecto");
		}

		this.fecha_nacimiento = fecha_nacimiento;
		this.calle = calle;

	}

//Método que realiza la diferencia entre la fecha actual y la fecha de	nacimiento de la persona para obtener su edad

	public Integer obtenerEdad() {
		return new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear()
				- fecha_nacimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni);
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
		return Objects.equals(dni, other.dni);
	}

	// Método que muestra la información de una persona
	public String toString() {
		return "Persona [nombre=" + nombre + ", apellidos=" + apellidos + ", dni=" + dni + ", fecha_nacimiento="
				+ fecha_nacimiento + ", calle=" + calle + "]";
	}

}
