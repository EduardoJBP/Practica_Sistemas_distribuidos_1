package agricola.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import agricola.model.Cultivos;

public interface CultivosRepository extends JpaRepository<Cultivos, Long> {

	Cultivos findById(long id);

	Cultivos findByVariedad(String variedad);

	Cultivos findByEspecie(String especie);

	List<Cultivos> findByIdAndVariedadAndEspecie(long id, String variedad, String especie);

}
