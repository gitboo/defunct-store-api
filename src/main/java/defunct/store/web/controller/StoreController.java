package defunct.store.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import defunct.store.core.error.RestError;
import defunct.store.core.service.StoreService;
import defunct.store.core.service.dto.RangeResult;
import defunct.store.core.service.dto.SimpleStoreDto;
import defunct.store.core.service.dto.StoreDto;
import defunct.store.core.service.dto.StorePhotoDto;
import defunct.store.core.service.dto.UpdateStoreForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Api(tags = { "store" })
@RestController
public class StoreController {
	
	@Autowired
	private StoreService storeService;
	
	
	@ApiOperation(value = "상점 상세 정보를 반환한다.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "storeId", value = "상점 ID", required = true, defaultValue = "1", dataType = "integer", paramType = "path") })
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success", response = StoreDto.class),
            @ApiResponse(code = 404, message = "Not Found Store", response = RestError.class),
            @ApiResponse(code = 500, message = "Failure", response = RestError.class)})
	@GetMapping(path = "/stores/{storeId}")
	public StoreDto showStore(@PathVariable(value = "storeId", required = true) Long storeId) {
		log.info("Show store: storeId[{}]", storeId);
		
		return storeService.findStore(storeId);
	}
	
	
	@ApiOperation(value = "상점 목록을 반환한다.")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "page", value = "page", required = false, defaultValue="0", dataType = "Integer", paramType = "query"),
        @ApiImplicitParam(name = "size", value = "size", required = false, defaultValue="10", dataType = "Integer", paramType = "query"),
        @ApiImplicitParam(name = "sort", value = "sort", required = false, defaultValue="value", dataType = "String", paramType = "query"),
    	@ApiImplicitParam(name = "order", value = "order", required = false, defaultValue="DESC", dataType = "String", paramType = "query")
      })
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = Page.class),
            @ApiResponse(code = 500, message = "Failure", response = RestError.class)})
	@GetMapping(path = "/stores")
	public RangeResult<SimpleStoreDto> showStores(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "3") int size,
			@RequestParam(value = "sort", defaultValue = "value") String sort,
			@RequestParam(value = "order", defaultValue = "DESC") Direction direction) {
		
		log.info("Show store list: page[{}], size[{}], sort[{}], direction[{}]", page, size, sort, direction);
		
		/**
		 * 상품 정보로 sorting 을 할경우 모델 mapping 에 따라 실제 동작 할 수 있는 값으로 변경 한다.  
		 */
		if ("value".equals(sort) == false) {
			String worikingValue = "store.";
			sort = worikingValue + sort;
		}
		
		Pageable pageable = new PageRequest(page, size, new Sort(direction, sort));
		return storeService.findStores(pageable);
	}
	
	
	@ApiOperation(value = "상점 정보를 갱신한다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "storeId", value = "상점 ID", required = true, defaultValue = "1", dataType = "integer", paramType = "path") })
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = StoreDto.class),
            @ApiResponse(code = 500, message = "Failure", response = RestError.class)})
	@PutMapping(path = "/stores/{storeId}")
	public StoreDto updateStore(
			@PathVariable(value = "storeId", required = true) Long storeId,
			@ApiParam(name = "body", value = "input parameters in json form") @Valid @RequestBody UpdateStoreForm storeForm) throws JsonProcessingException {
		log.info("Update store[id={}][{}]", storeId, storeForm.toJson());
		return storeService.updateStore(storeId,storeForm);
	}
	
	
	@ApiOperation(value = "상점 사진목록을 반환 한다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "storeId", value = "상점 ID", required = true, defaultValue = "1", dataType = "integer", paramType = "path"),
		@ApiImplicitParam(name = "page", value = "page", required = true, defaultValue = "0", dataType = "integer", paramType = "query"),
		@ApiImplicitParam(name = "size", value = "size", required = true, defaultValue = "3", dataType = "integer", paramType = "query"),
		@ApiImplicitParam(name = "sort", value = "sort", required = true, defaultValue = "uploadDate", dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "order", value = "order", required = true, defaultValue = "DESC", dataType = "String", paramType = "query")})
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = RangeResult.class),
            @ApiResponse(code = 404, message = "Not Found Store", response = RestError.class),
            @ApiResponse(code = 500, message = "Failure", response = RestError.class)})
	@GetMapping(path = "/stores/{storeId}/photos")
	public RangeResult<StorePhotoDto> showStorePhotos(
			@PathVariable(value = "storeId", required = true) Long storeId,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "3") int size,
			@RequestParam(value = "sort", defaultValue = "value") String sort,
			@RequestParam(value = "order", defaultValue = "DESC") Direction direction) {
		log.info("Show store photo list: storeId[{}], page[{}], size[{}], sort[{}], direction[{}]",storeId, page, size, sort, direction);
		
		Pageable pageable = new PageRequest(page, size, new Sort(direction, sort));
		return storeService.findStorePhotos(storeId, pageable);
	}
}