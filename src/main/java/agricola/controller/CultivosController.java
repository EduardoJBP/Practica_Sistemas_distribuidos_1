package agricola.controller;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
public class CultivosController {
//Ponemos los repositorios
	@Autowired
	CultivosRepository cultivosRepository;
	@Autowired
	FitosanitarioRepository fitosanitarioRepository;

	@Autowired
	TratamientosRepository tratamientosRepository;

//Este controller mostrará todos los cultivos en Cultivos.html
	@GetMapping("/cultivos")
	public String cultivosGet(Model model) {
		model.addAttribute("Cultivos", cultivosRepository.findAll());
		return "Cultivos";
	}

	//muestra el cultivo elegido, con sus tratamientos
	@GetMapping("/cultivos/{id}")
	public String mostrarCultivo(Model model, @PathVariable long id) {
		model.addAttribute("Cultivos", cultivosRepository.findById(id));
		model.addAttribute("lista", cultivosRepository.findById(id).getLista_tratamientos());
		return "Cultivo";
	}

	//permite editar el cultivo
	@GetMapping("/editcultivos/{id}")
	public String editarCultivo(Model model, @PathVariable long id) {
		model.addAttribute("Cultivos", cultivosRepository.findById(id));
		model.addAttribute("lista", cultivosRepository.findById(id).getLista_tratamientos());
		return "editarCultivo";
	}

	//con esto editamos el cultivo, si tiene algun tratamiento distinto, en verdad hay que ir a ese tratamiento y cambiar el cultivo
	@PostMapping("/editcultivo")
	public String formularioEditCultivos(@RequestParam long id, @RequestParam String especie,
			@RequestParam String variedad, @RequestParam String zona, @RequestParam String fecha,
			@RequestParam String tratamientos) {
		String[] lista_tratamientos = tratamientos.split(",");// array de string con las id
		ArrayList<Long> tratamiento_ids = new ArrayList<Long>();// metemos las id en long type
		long id_tratamiento = 0;

		Cultivos cultivo_a_editar = cultivosRepository.findById(id);
		if (!especie.equals(""))
			cultivo_a_editar.setEspecie(especie);
		cultivo_a_editar.setFecha(LocalDate.parse(fecha));
		if (!variedad.equals(""))
			cultivo_a_editar.setVariedad(variedad);
		cultivo_a_editar.setZona(zona);
		cultivosRepository.save(cultivo_a_editar);

		// creamos un tratamiento igual, ponemos la id del anterior, cambiamos el
		// cultivo y hacemos save para que se actualice
		if (!tratamientos.equals("")) {
			//hay que editar para que se borren lso anteriores tratamientos
			for (int i = 0; i < lista_tratamientos.length; i++) {
				tratamiento_ids.add(Long.parseLong(lista_tratamientos[i]));
				id_tratamiento = tratamiento_ids.get(i);
				Tratamiento tratamiento1 = tratamientosRepository.findById(id_tratamiento);
				if (tratamiento1 != null) {
					tratamiento1.setCultivo(cultivo_a_editar);
					tratamientosRepository.save(tratamiento1);
				}
			}}
		return "redirect:/cultivos";
	}

	//crear un nuevo cultivo
	@GetMapping("/newcultivo")
	public String nuevoCultivo(Cultivos cultivo) {
		return "newCultivo";
	}

	//crea el cultivo
	@PostMapping("/newcultivo/crearCultivo")
	public String nuevoCultivosProceso(@RequestParam String especie, @RequestParam String variedad,
			@RequestParam String zona, @RequestParam String fecha, @RequestParam String tratamientos) {
		String vacio = "";
		if (especie.equals(vacio) || variedad.equals(vacio) || LocalDate.parse(fecha) == null
				|| tratamientos.equals(vacio))
			return "redirect:/cultivos";

		String[] lista_tratamientos = tratamientos.split(",");// array de string con las id
		ArrayList<Long> tratamiento_ids = new ArrayList<Long>();// metemos las id en long type
		long id_tratamiento = 0;

		Cultivos cultivo_a_editar = new Cultivos(especie, variedad, zona, LocalDate.parse(fecha));
		cultivosRepository.save(cultivo_a_editar);

		// creamos un tratamiento igual, ponemos la id del anterior, cambiamos el
		// cultivo y hacemos save para que se actualice
		for (int i = 0; i < lista_tratamientos.length; i++) {
			tratamiento_ids.add(Long.parseLong(lista_tratamientos[i]));
			id_tratamiento = tratamiento_ids.get(i);
			if (tratamientosRepository.existsById(id_tratamiento)) {

				Tratamiento tratamiento1 = tratamientosRepository.findById(id_tratamiento);
				LocalDate fecharealiza1 = tratamiento1.getFecharealiza();
				String lote = tratamiento1.getLote();
				Fitosanitario fitosanitario = tratamiento1.getFitosanitario();
				Tratamiento tratamiento_guardar = new Tratamiento(lote, fecharealiza1, fitosanitario, cultivo_a_editar);
				tratamiento_guardar.setId(id_tratamiento);
				tratamientosRepository.save(tratamiento_guardar);
			}
		}
		return "redirect:/cultivos";
	}

}
