package agricola.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Cultivos {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String especie;
	private String variedad;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fecha;
	private String zona = null;

	// lo mapeamos con el campo cultivo de Tratamiento, un cultivo puede tener
	// varios tratamientos
	@OneToMany(mappedBy = "cultivo")
	private List<Tratamiento> lista_tratamientos;

	public Cultivos() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Cultivos(String esp, String var, List<Tratamiento> lista_tratamientos) {
		this.lista_tratamientos = lista_tratamientos;
		this.especie = esp;
		this.variedad = var;
		this.fecha = LocalDate.now();
	}

	public Cultivos(String esp, String var, Tratamiento tratamiento) {

		lista_tratamientos.add(tratamiento);
		this.especie = esp;
		this.variedad = var;
		this.fecha = LocalDate.now();

	}

	public Cultivos(String esp, String var, String zona, LocalDate fecha, List<Tratamiento> lista_tratamientos) {
		this.lista_tratamientos = lista_tratamientos;
		this.especie = esp;
		this.variedad = var;
		this.fecha = fecha;
		this.zona = zona;
	}

	public Cultivos(String esp, String var, String zona, LocalDate fecha, Tratamiento tratamiento) {
		this.zona = zona;
		lista_tratamientos.add(tratamiento);
		this.especie = esp;
		this.variedad = var;
		this.fecha = fecha;

	}

	public List<Tratamiento> getLista_tratamientos() {
		return lista_tratamientos;
	}

	public Cultivos setLista_tratamientos(List<Tratamiento> lista_tratamientos) {
		this.lista_tratamientos = lista_tratamientos;
		return this;
	}

	public void añadirtratamiento(Tratamiento tratamiento) {
		if (!lista_tratamientos.contains(tratamiento))
			lista_tratamientos.add(tratamiento);
	}

	public Cultivos(String esp, String var) {
		this.especie = esp;
		this.variedad = var;
		this.fecha = LocalDate.now();
	}

	public Cultivos(String esp, String var, String zone) {
		this.especie = esp;
		this.variedad = var;
		this.zona = zone;
		this.fecha = LocalDate.now();
	}

	public Cultivos(String esp, String var, String zone, LocalDate fecha) {
		this.especie = esp;
		this.variedad = var;
		this.zona = zone;
		this.fecha = fecha;

	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getVariedad() {
		return variedad;
	}

	public void setVariedad(String variedad) {
		this.variedad = variedad;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	@Override
	public String toString() {
		String aux = "Cultivos [especie=" + especie + ", variedad=" + variedad + ", fecha=" + fecha + ", zona=" + zona
				+ ", tratamientos=";
		aux.concat("]");
		return aux;
	}

}