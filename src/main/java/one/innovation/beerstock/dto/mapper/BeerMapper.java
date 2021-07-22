package one.innovation.beerstock.dto.mapper;

import org.mapstruct.Mapper;

import one.innovation.beerstock.dto.BeerDTO;
import one.innovation.beerstock.entity.Beer;

@Mapper(componentModel = "spring")
public interface BeerMapper {

	Beer toModel(BeerDTO beerDTO);
	
	BeerDTO toDto(Beer beer);
}
