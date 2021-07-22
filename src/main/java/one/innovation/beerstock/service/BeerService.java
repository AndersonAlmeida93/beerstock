package one.innovation.beerstock.service;

import java.util.List;

import one.innovation.beerstock.dto.BeerDTO;
import one.innovation.beerstock.exception.BeerAlreadyRegisteredException;
import one.innovation.beerstock.exception.BeerNotFoundException;
import one.innovation.beerstock.exception.BeerStockExceededException;

public interface BeerService {

	BeerDTO create(BeerDTO beerDTO) throws BeerAlreadyRegisteredException;
	
	BeerDTO findByName(String name) throws BeerNotFoundException;
	
	List<BeerDTO> listAll();
	
	void deleteById(Long id) throws BeerNotFoundException;
	
	BeerDTO increment(Long id, int quantityToIncrement) throws BeerNotFoundException , BeerStockExceededException;
}
