package com.rakuten.training.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakuten.training.model.Tutorial;
import com.rakuten.training.repository.TutorialRepository;
//import com.rakuten.training.security.Profiles;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers =TutorialController.class)
class SpringBootDataJpaApplicationTests {

	@MockBean
	TutorialRepository testRepo;
	
	@Autowired
	MockMvc mockMvc;

	@Test
	void TestgetAllTutorialsWhenTitleNotProvided() throws Exception{
		
		
		Tutorial t1=new Tutorial(1,"xyz","XYZ","true");
		Tutorial t2=new Tutorial(2,"abc","ABC","false");
		
		List<Tutorial> list=new ArrayList<Tutorial>();
		list.add(t1);
		list.add(t2);
		
		Mockito.when(testRepo.findAll()).thenReturn(list);
		mockMvc.
	      perform(MockMvcRequestBuilders.get("/api/v1/tutorialss").contentType(MediaType.APPLICATION_JSON)
	    		  .accept(MediaType.APPLICATION_JSON)).
	    andExpect(MockMvcResultMatchers.status().isOk());
               }
	@Test
	void TestgetAllTutorialsWhenTitleProvided() throws Exception{
		
		
		Tutorial t1=new Tutorial(1,"xyz","XYZ","true");
		Tutorial t2=new Tutorial(2,"abc","ABC","false");
		
		List<Tutorial> list=new ArrayList<Tutorial>();
		list.add(t1);
		list.add(t2);
		
		Mockito.when(testRepo.findByTitleContaining("XYZ")).thenReturn(list);
		mockMvc.
	      perform(MockMvcRequestBuilders.get("/api/v1/tutorialss?title=XYZ").contentType(MediaType.APPLICATION_JSON)
	    		  .accept(MediaType.APPLICATION_JSON)).
	    andExpect(MockMvcResultMatchers.status().isOk());
               }
 
	@Test
	void TestgetAllTutorialsWithWrongTitle()throws Exception{
		
		
		Mockito.when(testRepo.findByTitleContaining("xyz")).thenReturn(null);
		
		mockMvc.
		      perform(MockMvcRequestBuilders.get("/api/v1/tutorialss?title=xyz")
		    		  .contentType(MediaType.APPLICATION_JSON)
		    		  .accept(MediaType.APPLICATION_JSON)).
		    andExpect(MockMvcResultMatchers.status().isInternalServerError());
	}
	
	@Test
	void TestCreateTutorialWhenBodyProvided() throws Exception{
		Tutorial t1=new Tutorial(1,"xyz","XYZ","true");
		
		Mockito.when(testRepo.save(t1)).thenReturn(t1);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/tutorials").content(asJsonString(t1))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated());
	}
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	@Test
	void TestDeleteTutorialById() throws Exception{
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/tutorials/{id}",1)).andExpect(status().isNoContent()
				);
	}
	
	@Test
	public void TestupdateTutorial() throws Exception 
	{
	 mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/tutorials/{id}",1).content(asJsonString(new Tutorial(2, "ayush", "kumar", "singh"))).contentType(MediaType.APPLICATION_JSON))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("kumar"))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("ayush"))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("singh"));
	
	}
	
	

}
