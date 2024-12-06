package pe.edu.cibertec.crud_I202313380.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.cibertec.crud_I202313380.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
