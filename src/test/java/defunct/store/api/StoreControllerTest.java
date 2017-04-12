package defunct.store.api;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.common.collect.Lists;

import defunct.store.core.model.type.StoreType;
import defunct.store.core.service.StoreService;
import defunct.store.core.service.dto.RangeResult;
import defunct.store.core.service.dto.SimpleStoreDto;
import defunct.store.core.service.dto.StoreDto;
import defunct.store.core.service.dto.UpdateStoreForm;
import defunct.store.web.controller.StoreController;



@RunWith(SpringRunner.class)
@WebMvcTest(StoreController.class)
public class StoreControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private StoreService storeService;
	
	
	@Test
	@Ignore
	public void updateStoreTest() throws Exception {
		Long storeId = 1L;
		
		UpdateStoreForm storeForm = new UpdateStoreForm();
		storeForm.setDescription("New description2222");
		storeForm.setPhoneNumber("02-4444-5555");
		storeForm.setStoreType(StoreType.S);
		storeForm.setStoreName("updateTest");
		
		given(storeService.findStore(storeId)).willReturn(StoreDto.builder().storeId(storeId).storeName("updateTest").build());
		mvc.perform(put("/stores/{id}", storeId)
				.content(storeForm.toJson())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				;
	}
	
	
	@Test
	@Ignore
	public void showStoreTest() throws Exception {
		Long storeId = 1L;
		
		given(storeService.findStore(storeId)).willReturn(StoreDto.builder().storeId(storeId).build());
		mvc.perform(get("/stores/{id}", storeId)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.storeId").value(1))
				;
	}
	
	@Test
	@Ignore
	public void showStoreSTest() throws Exception {
		
		Pageable pageRequest = new PageRequest(0, 3, new Sort(Sort.Direction.DESC, "value"));
		given(storeService.findStores(pageRequest))
				.willReturn(RangeResult.create(Lists.newArrayList(
						SimpleStoreDto.builder().storeId(1L).build(),
						SimpleStoreDto.builder().storeId(2L).build()
						)));
		
		mvc.perform(get("/stores")
				.param("page", "0")
				.param("size", "3")
				.param("sort", "value")
				.param("direction", "DESC")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	
	  
	  @Test
	  @Ignore
	  public void showStoreExceptionTest() throws Exception {
		mvc.perform(get("/stores/{id}", "A").accept(MediaType.APPLICATION_JSON))
	      .andDo(print())
	      .andExpect(status().isBadRequest())
	      .andExpect(jsonPath("$.code").value("S0102"));
	  }
}