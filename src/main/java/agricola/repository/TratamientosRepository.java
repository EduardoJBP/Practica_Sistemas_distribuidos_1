package agricola.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import agricola.model.Tratamiento;

public interface TratamientosRepository extends JpaRepository<Tratamiento, Long> {

	Tratamiento findById(long id);

	List<Tratamiento> findAllByOrderByFechareenAsc();

	List<Tratamiento> findAllByOrderByFecharecAsc();

}
