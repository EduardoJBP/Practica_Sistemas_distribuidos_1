package agricola.controller;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import agricola.model.Cultivos;
import agricola.model.Fitosanitario;
import agricola.model.Tratamiento;
import agricola.repository.CultivosRepository;
import agricola.repository.FitosanitarioRepository;
import agricola.repository.TratamientosRepository;

@Controller
public class TratamientosController {

	@Autowired
	CultivosRepository cultivosRepository;
	@Autowired
	FitosanitarioRepository fitosanitarioRepository;

	@Autowired
	TratamientosRepository tratamientosRepository;

	@GetMapping("/tratamientos")
	public String tratamientosReturn(Model model) {
		model.addAttribute("Tratamientos", tratamientosRepository.findAll());
		return "Tratamiento_general";
	}

	@GetMapping("/editTratamiento/{id}")
	public String editTratamiento(Model model, @PathVariable long id) {
		model.addAttribute("Tratamiento", tratamientosRepository.findById(id));
		return "editTratamiento";
	}

	@GetMapping("/tratamientos/{id}")
	public String getTratamiento(Model model, @PathVariable long id) {

		model.addAttribute("Tratamiento", tratamientosRepository.findById(id));
		return "Tratamiento";
	}

	// al editar el tratamiento, si cambiamos el cultivo, creamos un tratamiento
	// nuevo, le ponemos la misma id y hacemos save, el repositorio lo reemplaza
	@PostMapping("/editTratamiento")
	public String formularioEditTratamiento(@RequestParam long id, @RequestParam String lote,
			@RequestParam String fecharealiza, @RequestParam long cultivo, @RequestParam long fitosanitario) {
		if (!cultivosRepository.existsById(cultivo) || !fitosanitarioRepository.existsById(fitosanitario)
				|| lote.equals(""))
			return "redirect:/tratamientos";
		LocalDate fecharealiza1 = LocalDate.parse(fecharealiza);
		Cultivos cultivo1 = cultivosRepository.findById(cultivo);
		Fitosanitario fitosanitario1 = fitosanitarioRepository.findById(fitosanitario);
		Tratamiento tratamiento1 = new Tratamiento(lote, fecharealiza1, fitosanitario1, cultivo1);
		tratamiento1.setId(id);
		tratamientosRepository.save(tratamiento1);
		return "redirect:/tratamientos";
	}

	@GetMapping("/newTratamiento")
	public String nuevoTratamiento() {
		return "newTratamiento";
	}

	// creamos un tratamiento nuevo
	@PostMapping("/newTratamiento/crear")
	public String crearTratamiento(@RequestParam String lote, @RequestParam String fecharealiza,
			@RequestParam long cultivo, @RequestParam long fitosanitario) {
		String vacio = "";
		if (lote.equals(vacio) || fecharealiza.equals(vacio) || !cultivosRepository.existsById(cultivo)
				|| !fitosanitarioRepository.existsById(fitosanitario))
			return "redirect:/tratamientos";

		Cultivos cultivo1 = cultivosRepository.findById(cultivo);
		Fitosanitario fitosanitario1 = fitosanitarioRepository.findById(fitosanitario);
		LocalDate fecharealiza1 = LocalDate.parse(fecharealiza);

		Tratamiento tratamiento1 = new Tratamiento(lote, fecharealiza1, fitosanitario1, cultivo1);
		tratamientosRepository.save(tratamiento1);
		return "redirect:/tratamientos";
	}

	// recibimos la fecha y empezamos a ver tratamiento a tratamiento cual está
	// vigente.
	// dada la fecha vemos si está entre la fecha limite y la fecha de inicio
	// si el tratamiento es vigente se añade al array de tratamientos2
	@PostMapping("/tratamientosEnVigor")
	public String mostrarTratamientosVigor(Model model, String fecha) {

		String vacio = "";
		if (fecha.equals(vacio))
			return "redirect:/tratamientos";
		LocalDate fecha_dada = LocalDate.parse(fecha);
		ArrayList<Tratamiento> tratamientos = new ArrayList<Tratamiento>(tratamientosRepository.findAll());
		ArrayList<Tratamiento> tratamientos2 = new ArrayList<Tratamiento>();
		for (int i = 0; i < tratamientos.size(); i++) {
			LocalDate finalizaRecoleccion = tratamientos.get(i).getFecharec();
			LocalDate finalizaReentrada = tratamientos.get(i).getFechareen();
			LocalDate empiezaTratamiento = tratamientos.get(i).getFecharealiza();

			if (fecha_dada.isAfter(empiezaTratamiento)) {
				if ((fecha_dada.isBefore(finalizaRecoleccion)) && (fecha_dada.isBefore(finalizaReentrada))) {
					tratamientos2.add(tratamientos.get(i));
				} else {
					if ((fecha_dada.isBefore(finalizaRecoleccion)))
						tratamientos2.add(tratamientos.get(i));

					if (fecha_dada.isBefore(finalizaReentrada))
						tratamientos2.add(tratamientos.get(i));

				}
			}
		}
		model.addAttribute("Tratamientos", tratamientos2);
		return "TratamientosVigentes";
	}

	@PostMapping("/tratamientosEnVigor/ordenadosporFechaReentrada")
	public String OrdenadoPorFechaReentrada(Model model, String fecha) {

		String vacio = "";
		if (fecha.equals(vacio))
			return "redirect:/tratamientos";
		LocalDate fecha_dada = LocalDate.parse(fecha);
		ArrayList<Tratamiento> tratamientos = new ArrayList<Tratamiento>(
				tratamientosRepository.findAllByOrderByFechareenAsc());// así lo ordenamos por fecha de reentrada
		ArrayList<Tratamiento> tratamientos2 = new ArrayList<Tratamiento>();
		for (int i = 0; i < tratamientos.size(); i++) {
			LocalDate finalizaRecoleccion = tratamientos.get(i).getFecharec();
			LocalDate finalizaReentrada = tratamientos.get(i).getFechareen();
			LocalDate empiezaTratamiento = tratamientos.get(i).getFecharealiza();

			if (fecha_dada.isAfter(empiezaTratamiento)) {
				if ((fecha_dada.isBefore(finalizaRecoleccion)) && (fecha_dada.isBefore(finalizaReentrada))) {
					tratamientos2.add(tratamientos.get(i));
				} else {
					if ((fecha_dada.isBefore(finalizaRecoleccion)))
						tratamientos2.add(tratamientos.get(i));
					if (fecha_dada.isBefore(finalizaReentrada))
						tratamientos2.add(tratamientos.get(i));
				}
			}
		}
		model.addAttribute("Tratamientos", tratamientos2);
		return "TratamientosVigentes";
	}

	@PostMapping("/tratamientosEnVigor/ordenadosporFechaRecoleccion")
	public String mostrarTratamientosVigorasdsad(Model model, String fecha) {

		String vacio = "";
		if (fecha.equals(vacio))
			return "redirect:/tratamientos";
		LocalDate fecha_dada = LocalDate.parse(fecha);
		ArrayList<Tratamiento> tratamientos = new ArrayList<Tratamiento>(
				tratamientosRepository.findAllByOrderByFecharecAsc());// así lo ordenamos por fecha de recoleccion
		ArrayList<Tratamiento> tratamientos2 = new ArrayList<Tratamiento>();
		for (int i = 0; i < tratamientos.size(); i++) {
			LocalDate finalizaRecoleccion = tratamientos.get(i).getFecharec();
			LocalDate finalizaReentrada = tratamientos.get(i).getFechareen();
			LocalDate empiezaTratamiento = tratamientos.get(i).getFecharealiza();

			if (fecha_dada.isAfter(empiezaTratamiento)) {
				if ((fecha_dada.isBefore(finalizaRecoleccion)) && (fecha_dada.isBefore(finalizaReentrada))) {
					tratamientos2.add(tratamientos.get(i));
				} else {
					if ((fecha_dada.isBefore(finalizaRecoleccion)))
						tratamientos2.add(tratamientos.get(i));
					if (fecha_dada.isBefore(finalizaReentrada))
						tratamientos2.add(tratamientos.get(i));
				}
			}
		}
		model.addAttribute("Tratamientos", tratamientos2);
		return "TratamientosVigentes";
	}

	@GetMapping("/tratamientos_vigentes/Ordenar_Freentrada")
	public String Ordenar_freentrada(Model model) {
		return "TratamientosVigentes";
	}

}
