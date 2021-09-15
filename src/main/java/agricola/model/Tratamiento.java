package agricola.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Tratamiento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	// la relacion es one to one porque un tratamiento tiene un producto
	// fitosanitario
	@OneToOne
	private Fitosanitario fitosanitario;

	// un cultivo puede tener varios tratamientos
	@ManyToOne
	private Cultivos cultivo;

	private String lote;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fecharealiza;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechareen;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fecharec;

	public Tratamiento() {
	}

	public long getId() {
		return id;
	}

	public Tratamiento(Tratamiento tratamiento) {

		this.lote = tratamiento.getLote();

	}

	public void setId(int id) {
		this.id = id;
	}

	public Fitosanitario getFitosanitario() {
		return fitosanitario;
	}

	public void setFitosanitario(Fitosanitario fitosanitario) {
		this.fitosanitario = fitosanitario;
	}

	public Cultivos getCultivo() {
		return cultivo;
	}

	public void setCultivo(Cultivos cultivo) {
		this.cultivo = cultivo;
	}

	public Tratamiento(String lote, LocalDate fecharealiza, Fitosanitario fitosanitario, Cultivos cultivo) {
		super();
		this.cultivo = cultivo;
		this.fecharealiza = fecharealiza;
		this.fitosanitario = fitosanitario;
		LocalDate fechaReentrada_Final = fecharealiza.plusDays((long) fitosanitario.getPlreentrada());
		LocalDate fechaRecoleccion_Final = fecharealiza.plusDays((long) fitosanitario.getPlrecoleccion());
		this.fechareen = fechaReentrada_Final;
		this.fecharec = fechaRecoleccion_Final;
		this.lote = lote;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public LocalDate getFecharealiza() {
		return fecharealiza;
	}

	public void setFecharealiza(LocalDate fecharealiza) {
		this.fecharealiza = fecharealiza;
	}

	public LocalDate getFechareen() {
		return fechareen;
	}

	public void setFechareen(LocalDate fechareen) {
		this.fechareen = fechareen;
	}

	public LocalDate getFecharec() {
		return fecharec;
	}

	public void setFecharec(LocalDate fecharec) {
		this.fecharec = fecharec;
	}

}