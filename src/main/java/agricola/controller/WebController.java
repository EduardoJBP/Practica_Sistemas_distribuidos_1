package agricola.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import agricola.repository.CultivosRepository;
import agricola.repository.FitosanitarioRepository;
import agricola.repository.TratamientosRepository;

@Controller
public class WebController {

	@Autowired
	CultivosRepository cultivosRepository;
	@Autowired
	FitosanitarioRepository fitosanitarioRepository;

	@Autowired
	TratamientosRepository tratamientosRepository;
//muestra el menú principal con los botones del inicio
	@GetMapping("/")
	public String index() {
		return "MenuBotones";
	

}}