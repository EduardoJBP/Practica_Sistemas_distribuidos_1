package agricola.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Fitosanitario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String nombre;
	private String descripcion = null;
	private int plreentrada;
	private int plrecoleccion;

	public Fitosanitario() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Fitosanitario(String nom, int plreen, int plreec) {
		this.nombre = nom;
		this.plreentrada = plreen;
		this.plrecoleccion = plreec;
		this.descripcion = "";
	}

	public Fitosanitario(String nom, String desc, int plreen, int plreec) {
		this.nombre = nom;
		this.descripcion = desc;
		this.plreentrada = plreen;
		this.plrecoleccion = plreec;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getPlreentrada() {
		return plreentrada;
	}

	public void setPlreentrada(int plreentrada) {
		this.plreentrada = plreentrada;
	}

	public int getPlrecoleccion() {
		return plrecoleccion;
	}

	public void setPlrecoleccion(int plrecoleccion) {
		this.plrecoleccion = plrecoleccion;
	}

	@Override
	public String toString() {
		return "Fitosanitario [nombre=" + nombre + ", descripcion=" + descripcion + ", plreentrada=" + plreentrada
				+ ", plrecoleccion=" + plrecoleccion + "]";
	}
}