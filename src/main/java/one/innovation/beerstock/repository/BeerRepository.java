package one.innovation.beerstock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import one.innovation.beerstock.entity.Beer;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long>{

	Optional<Beer> findByName(String name);
}
