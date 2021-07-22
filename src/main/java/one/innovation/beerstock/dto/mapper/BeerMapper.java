package one.innovation.beerstock.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import one.innovation.beerstock.dto.BeerDTO;
import one.innovation.beerstock.entity.Beer;

@Mapper(componentModel = "spring")
public interface BeerMapper {

	BeerMapper INSTANCE = Mappers.getMapper(BeerMapper.class);
	  
	Beer toModel(BeerDTO beerDTO);
	
	BeerDTO toDto(Beer beer);
}
