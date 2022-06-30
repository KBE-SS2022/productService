package productservice.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import productservice.api.entity.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Ingredient save(Ingredient ingredient);
    Optional<Ingredient> findById(Long id);
    List<Ingredient> findAll();
    void delete(Ingredient ingredient);
}