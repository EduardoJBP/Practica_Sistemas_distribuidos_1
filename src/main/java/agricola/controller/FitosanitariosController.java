package agricola.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import agricola.model.Fitosanitario;
import agricola.repository.CultivosRepository;
import agricola.repository.FitosanitarioRepository;
import agricola.repository.TratamientosRepository;

@Controller
public class FitosanitariosController {
//hace las acciones tipicas de los productos fitosanitarios
	@Autowired
	CultivosRepository cultivosRepository;
	@Autowired
	FitosanitarioRepository fitosanitarioRepository;

	@Autowired
	TratamientosRepository tratamientosRepository;

	@GetMapping("/fitosanitarios")
	public String fitosanitarioReturn(Model model) {
		model.addAttribute("fitosanitarios", fitosanitarioRepository.findAll());
		return "Fitosanitario_general";
	}

	@GetMapping("/fitosanitarios/{id}")
	public String mostrarProducto(Model model, @PathVariable long id) {
		model.addAttribute("Fitosanitarios", fitosanitarioRepository.findById(id));
		return "Fitosanitario";
	}

	@GetMapping("/editproducto/{id}")
	public String editarProducto(Model model, @PathVariable long id) {
		model.addAttribute("Producto", fitosanitarioRepository.findById(id));
		return "editFitosanitarios";
	}

	@PostMapping("/editproducto")
	public String formularioEditProducto(Fitosanitario fitosanitario) {
		if (!fitosanitario.getNombre().equals(""))
			fitosanitarioRepository.save(fitosanitario);
		return "redirect:/fitosanitarios";
	}

	@GetMapping("/newProducto")
	public String nuevoProducto() {
		return "newFitosanitario";
	}

	@PostMapping("/newProducto/crear")
	public String crearProducto(Fitosanitario fitosanitario) {
		String vacio = "";
		if (fitosanitario.getNombre().equals(vacio))
			return "redirect:/fitosanitarios";
		fitosanitarioRepository.save(fitosanitario);
		return "redirect:/fitosanitarios";
	}

}
