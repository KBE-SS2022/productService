package productservice.api.repository;


import productservice.api.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {

    Pizza save(Pizza pizza);
    Optional<Pizza> findById(Long id);
    List<Pizza> findAll();
    void delete(Pizza pizza);
}