package agricola.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import agricola.model.Fitosanitario;

public interface FitosanitarioRepository extends JpaRepository<Fitosanitario, Long> {

	Fitosanitario findById(long id);

	Fitosanitario findByNombre(String nombre);

	Fitosanitario findByDescripcion(String descripcion);

	List<Fitosanitario> findByNombreAndDescripcion(String nombre, String descripcion);
}
