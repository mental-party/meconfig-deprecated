package com.teammental.meconfig.web.controller;

import com.teammental.meconfig.config.TestControllerConfig;
import com.teammental.meconfig.config.TestUtil;
import com.teammental.meconfig.testapp.TestCrudController;
import com.teammental.meconfig.testapp.TestCrudService;
import com.teammental.meconfig.testapp.TestDto;
import com.teammental.meconfig.testapp.TestEntity;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestControllerConfig.class})
@WebAppConfiguration
@Import(TestCrudController.class)
public class BaseCrudControllerTest {

  @Autowired
  private TestCrudService testCrudService;

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  // region getAll

  @Test
  public void getAll_shouldReturn200AndDtos_whenFound() throws Exception {
    final TestDto testDto1 = TestDto.buildRandom();
    final TestDto testDto2 = TestDto.buildRandom();
    final List<TestDto> expectedDtos = Arrays.asList(testDto1, testDto2);

    when(testCrudService.findAll())
        .thenReturn(expectedDtos);

    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id", is(testDto1.getId())))
        .andExpect(jsonPath("$[0].name", is(testDto1.getName())))
        .andExpect(jsonPath("$[1].id", is(testDto2.getId())))
        .andExpect(jsonPath("$[1].name", is(testDto2.getName())));

    verify(testCrudService, times(1))
        .findAll();

    verifyNoMoreInteractions(testCrudService);

  }

  // endregion getAll

}