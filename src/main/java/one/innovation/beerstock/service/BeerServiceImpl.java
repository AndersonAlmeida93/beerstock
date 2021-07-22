package one.innovation.beerstock.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.innovation.beerstock.dto.BeerDTO;
import one.innovation.beerstock.dto.mapper.BeerMapper;
import one.innovation.beerstock.entity.Beer;
import one.innovation.beerstock.exception.BeerAlreadyRegisteredException;
import one.innovation.beerstock.exception.BeerNotFoundException;
import one.innovation.beerstock.exception.BeerStockExceededException;
import one.innovation.beerstock.repository.BeerRepository;

@Service
@Transactional
public class BeerServiceImpl implements BeerService {

	@Autowired
	private BeerRepository beerRepository;

	@Autowired
	private BeerMapper beerMapper;

	@Override
	public BeerDTO create(BeerDTO beerDTO) throws BeerAlreadyRegisteredException {

		verifyIfIsAlreadyRegistered(beerDTO.getName());
		Beer beer = beerMapper.toModel(beerDTO);
		Beer saveBeer = beerRepository.save(beer);
		return beerMapper.toDto(saveBeer);
	}

	@Override
	public BeerDTO findByName(String name) throws BeerNotFoundException {

		Beer foundBeer = beerRepository.findByName(name).orElseThrow(() -> new BeerNotFoundException(name));
		return beerMapper.toDto(foundBeer);
	}

	@Override
	public List<BeerDTO> listAll() {

		return beerRepository.findAll().stream().map(beerMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public void deleteById(Long id) throws BeerNotFoundException {

		verifyIfExists(id);
		beerRepository.deleteById(id);
	}

	@Override
	public BeerDTO increment(Long id, int quantityToIncrement)
			throws BeerNotFoundException, BeerStockExceededException {

		Beer beerToIncrementStock = verifyIfExists(id);
		int quantityAfterIncrement = quantityToIncrement + beerToIncrementStock.getQuantity();
		if (quantityAfterIncrement <= beerToIncrementStock.getMax()) {
			beerToIncrementStock.setQuantity(beerToIncrementStock.getQuantity() + quantityToIncrement);
			Beer incrementedBeerStock = beerRepository.save(beerToIncrementStock);
			return beerMapper.toDto(incrementedBeerStock);
		}
		throw new BeerStockExceededException(id, quantityToIncrement);
	}

	private void verifyIfIsAlreadyRegistered(String name) throws BeerAlreadyRegisteredException {
		Optional<Beer> beer = beerRepository.findByName(name);
		if (beer.isPresent()) {
			throw new BeerAlreadyRegisteredException(name);
		}
	}

	private Beer verifyIfExists(Long id) throws BeerNotFoundException {

		return beerRepository.findById(id).orElseThrow(() -> new BeerNotFoundException(id));
	}

}
