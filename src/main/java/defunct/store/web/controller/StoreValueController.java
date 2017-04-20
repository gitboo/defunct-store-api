package defunct.store.web.controller;

import java.util.concurrent.Callable;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import defunct.store.core.error.RestError;
import defunct.store.core.service.StoreValueService;
import defunct.store.core.service.dto.SimpleResult;
import defunct.store.core.service.dto.StoreValuePolicySection;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Api(tags = { "storeValue" })
@RestController
public class StoreValueController {
	
	@Autowired
	private StoreValueService storeValueService;
	
	
	/**
	 * 가치 산정은 수행 시간이 오래 걸릴 수  있기 때문에 Callable를 사용하여 Async 하게 수행한다. 
	 */
	@ApiOperation(value = "상점 가치 산정을 요청한다.")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = SimpleResult.class),
            @ApiResponse(code = 500, message = "Failure", response = RestError.class)})
	@PutMapping(path = "/stores/value")
	public Callable<SimpleResult> esimateStoreValue() {
		log.info("Start EsimateStoreValue Servlet");
		Callable<SimpleResult> callable = storeValueService::estimateStoreValue;
		log.info("End EsimateStoreValue Servlet");
		return callable;
	}
	
	
	@ApiOperation(value = "상점 가치 산정 규칙을 변경 한다.")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = Page.class),
            @ApiResponse(code = 403, message = "The sum of all sections at the same level must be 100.", response = RestError.class)})
	@PutMapping(path = "/stores/value/policy")
	public StoreValuePolicySection updateStoreValuePolicy(
			@ApiParam(name = "body", value = "input parameters in json form") 
			@Valid @RequestBody StoreValuePolicySection storeValuePolicySection) throws JsonProcessingException {
		
		log.info("Update StoreValuePolicy:  {}", storeValuePolicySection.toJson());
		return storeValueService.updateStoreValuePolicy(storeValuePolicySection);
	}
}