package defunct.store.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import defunct.store.core.model.Store;
import defunct.store.core.service.dto.SimpleStoreDto;
import defunct.store.core.service.dto.StoreDto;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(storeDtoMapping());
		modelMapper.addMappings(storeToSimpleStoreDtoMapping());
		return modelMapper;
	}

	private PropertyMap<Store, StoreDto> storeDtoMapping() {

		return new PropertyMap<Store, StoreDto>() {
			protected void configure() {
				map(source.getStoreDescs(), destination.getDescription());
				map(source.getStoreValue(), destination.getStoreValue());
			}
		};
	}

	private PropertyMap<Store, SimpleStoreDto> storeToSimpleStoreDtoMapping() {
		return new PropertyMap<Store, SimpleStoreDto>() {
			protected void configure() {
				skip(source.getStoreValue(), destination.getStoreValue());
			}
		};
	}
}
