package agricola;

import java.time.LocalDate;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import agricola.model.Cultivos;
import agricola.model.Fitosanitario;
import agricola.model.Tratamiento;
import agricola.repository.CultivosRepository;
import agricola.repository.FitosanitarioRepository;
import agricola.repository.TratamientosRepository;

@Component
public class BaseDatos {
	@Autowired
	FitosanitarioRepository fitosanitarioRepository;

	@Autowired
	CultivosRepository cultivosRepository;

	@Autowired
	TratamientosRepository tratamientosRepository;

	@PostConstruct
	public void init() {

		// PRODUCTOS

		// 3 de ellos con plazos de reentrada y recoleccion
		Fitosanitario producto1 = new Fitosanitario("primero", "AA", 1, 15);
		Fitosanitario producto2 = new Fitosanitario("segundo", "BB", 2, 12);
		Fitosanitario producto3 = new Fitosanitario("tercero", "CC", 3, 18);
		fitosanitarioRepository.save(producto1);
		fitosanitarioRepository.save(producto2);
		fitosanitarioRepository.save(producto3);

		// 1 de ellos con plazo de recoleccion y no de reentrada
		Fitosanitario producto4 = new Fitosanitario("numero4", "D", 0, 20);
		fitosanitarioRepository.save(producto4);
		Fitosanitario producto5 = new Fitosanitario("numero5", "E", 5, 0);
		fitosanitarioRepository.save(producto5);

		// 1 de ellos sin plazo de recoleccion ni de reentrada
		Fitosanitario productoSinPlazos = new Fitosanitario("numero6", "F", 0, 0);
		fitosanitarioRepository.save(productoSinPlazos);

		// CULTIVOS y TRATAMIENTOS

		// 1 Cultivo sin ningun tratamiento.
		Cultivos cultivo1 = new Cultivos("Especie1", "variedad1", "Sur", LocalDate.now());
		cultivosRepository.save(cultivo1);
		// 1 Cultivo un unico producto fitosanitario sin plazo de recoleccion ni de
		// reentrada
		Cultivos cultivo2 = new Cultivos("Especie2", "variedad2", "Norte", LocalDate.now());
		Tratamiento tratamiento1 = new Tratamiento("TratamientoA", LocalDate.of(2021, 04, 20), productoSinPlazos,
				cultivo2);
		cultivosRepository.save(cultivo2);
		// 1 Cultivo con un unico producto fitosanitario con plazo de recoleccion y no
		// de reentrada
		Cultivos cultivo3 = new Cultivos("Especie3", "variedad3", "Centro", LocalDate.now());
		Tratamiento tratamiento2 = new Tratamiento("Tratamiento2", LocalDate.of(2021, 04, 22), producto4, cultivo3);
		cultivosRepository.save(cultivo3);

		// 1 Cultivo con un tratamiento con un unico producto fitosanitario con fechas
		// de reentrada y recoleccion
		Cultivos cultivo4 = new Cultivos("Especie4", "variedad4", "Extremadura", LocalDate.now());
		Tratamiento tratamiento3 = new Tratamiento("Tratamiento3", LocalDate.of(2021, 04, 24), producto1, cultivo4);
		cultivosRepository.save(cultivo4);

		// 1 Cultivo con dos tratamientos realizados en distintas fechas con sendos
		// productos diferentes, ambos con tanto plazo de reentrada como de recoleccio.
		Cultivos cultivo5 = new Cultivos("Especie5", "variedad5", "Rusia", LocalDate.now());
		Tratamiento tratamiento4 = new Tratamiento("Tratamiento4", LocalDate.of(2021, 04, 26), producto1, cultivo5);
		Tratamiento tratamiento5 = new Tratamiento("Tratamiento5", LocalDate.of(2021, 04, 28), producto2, cultivo5);
		cultivosRepository.save(cultivo5);

		// 1 Cultivo con tres tratamientos con otros tantos productos diferentes
		// aplicados en
		// la misma fecha, uno de ellos con plazo de recoleccion pero no de reentrada y
		// dos con ambos.
		Cultivos cultivo6 = new Cultivos("Especie6", "variedad6", "Caribe", LocalDate.now());
		Tratamiento tratamiento6 = new Tratamiento("Tratamiento6", LocalDate.of(2021, 04, 30), producto4, cultivo6);
		Tratamiento tratamiento7 = new Tratamiento("Tratamiento7", LocalDate.of(2021, 04, 30), producto2, cultivo6);
		Tratamiento tratamiento8 = new Tratamiento("Tratamiento8", LocalDate.of(2021, 04, 30), producto3, cultivo6);
		cultivosRepository.save(cultivo6);

		tratamientosRepository.save(tratamiento1);
		tratamientosRepository.save(tratamiento2);
		tratamientosRepository.save(tratamiento3);
		tratamientosRepository.save(tratamiento4);
		tratamientosRepository.save(tratamiento5);
		tratamientosRepository.save(tratamiento6);
		tratamientosRepository.save(tratamiento7);
		tratamientosRepository.save(tratamiento8);

	}
}
