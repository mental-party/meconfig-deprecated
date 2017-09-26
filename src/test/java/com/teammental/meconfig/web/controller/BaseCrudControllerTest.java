package com.teammental.meconfig.web.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.teammental.meconfig.config.TestControllerConfig;
import com.teammental.meconfig.config.TestUtil;
import com.teammental.meconfig.exception.entity.EntityDeleteException;
import com.teammental.meconfig.exception.entity.EntityInsertException;
import com.teammental.meconfig.exception.entity.EntityNotFoundException;
import com.teammental.meconfig.exception.entity.EntityUpdateException;
import com.teammental.meconfig.testapp.TestCrudController;
import com.teammental.meconfig.testapp.TestCrudService;
import com.teammental.meconfig.testapp.TestDto;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

@SuppressWarnings("PMD.TooManyStaticImports")
@RunWith(SpringJUnit4ClassRunner.class)
@Import(TestControllerConfig.class)
public class BaseCrudControllerTest {

  @MockBean
  private TestCrudService testCrudService;

  @InjectMocks
  private TestCrudController testCrudController;

  @Autowired
  private ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver;

  private MockMvc mockMvc;

  /**
   * Setup configuration.
   */
  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    this.mockMvc = MockMvcBuilders.standaloneSetup(testCrudController)
        .setHandlerExceptionResolvers(exceptionHandlerExceptionResolver).build();
  }

  // region getAll

  @Test
  public void getAll_shouldReturn200AndDtos_whenFound() throws Exception {
    final TestDto testDto1 = TestDto.buildRandom();
    final TestDto testDto2 = TestDto.buildRandom();
    final List<TestDto> expectedDtos = Arrays.asList(testDto1, testDto2);

    when(testCrudService.findAll())
        .thenReturn(expectedDtos);

    mockMvc.perform(get(TestControllerConfig.URL))
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

  @Test
  public void getAll_shouldReturn404_whenNotFound() throws Exception {

    when(testCrudService.findAll())
        .thenThrow(new EntityNotFoundException());

    mockMvc.perform(get(TestControllerConfig.URL))
        .andExpect(status().isNotFound());

    verify(testCrudService, times(1))
        .findAll();

    verifyNoMoreInteractions(testCrudService);
  }

  // endregion getAll


  // region insert

  @Test
  public void insert_shouldReturn201AndLocation_whenInsert() throws Exception {

    final Integer expectedId = 9843;
    when(testCrudService.insert(anyObject()))
        .thenReturn(expectedId);

    final TestDto testDto = TestDto.buildRandom();

    mockMvc.perform(post(TestControllerConfig.URL)
        .content(TestUtil.convertObjectToJsonBytes(testDto))
        .contentType(TestUtil.APPLICATION_JSON_UTF8))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location",
            TestControllerConfig.URL + "/" + expectedId));

    verify(testCrudService, times(1))
        .insert(anyObject());

    verifyNoMoreInteractions(testCrudService);
  }

  @Test
  @Ignore
  public void insert_shouldReturn400_whenValidationFails() throws Exception {
    final TestDto testDto = new TestDto();
    mockMvc.perform(post(TestControllerConfig.URL)
        .contentType(TestUtil.APPLICATION_JSON_UTF8)
        .content(TestUtil.convertObjectToJsonBytes(testDto)))
        .andExpect(status().isBadRequest());

    verify(testCrudService, times(0))
        .insert(anyObject());
  }

  @Test
  public void insert_shouldReturn403_whenInsertException() throws Exception {

    TestDto testDto = TestDto.buildRandom();
    when(testCrudService.insert(anyObject()))
        .thenThrow(new EntityInsertException());

    mockMvc.perform(post(TestControllerConfig.URL)
        .content(TestUtil.convertObjectToJsonBytes(testDto))
        .contentType(TestUtil.APPLICATION_JSON_UTF8))
        .andExpect(status().isForbidden());

    verify(testCrudService, times(1))
        .insert(anyObject());

    verifyNoMoreInteractions(testCrudService);
  }

  // endregion insert

  // region update

  @Test
  public void update_shouldReturn200_whenSuccess() throws Exception {
    final TestDto testDto = TestDto.buildRandom();

    when(testCrudService.update(anyObject()))
        .thenReturn(testDto);

    mockMvc.perform(put(TestControllerConfig.URL)
        .contentType(TestUtil.APPLICATION_JSON_UTF8)
        .content(TestUtil.convertObjectToJsonBytes(testDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(testDto.getId())))
        .andExpect(jsonPath("$.name", is(testDto.getName())));

    verify(testCrudService, times(1))
        .update(anyObject());

    verifyNoMoreInteractions(testCrudService);
  }

  @Test
  public void update_shouldReturn404_whenNotFound() throws Exception {
    final TestDto testDto = TestDto.buildRandom();

    when(testCrudService.update(anyObject()))
        .thenThrow(new EntityNotFoundException());

    mockMvc.perform(put(TestControllerConfig.URL)
        .content(TestUtil.convertObjectToJsonBytes(testDto))
        .contentType(TestUtil.APPLICATION_JSON_UTF8))
        .andExpect(status().isNotFound());

    verify(testCrudService, times(1))
        .update(anyObject());

    verifyNoMoreInteractions(testCrudService);
  }

  @Test
  @Ignore
  public void update_shouldReturn400_whenValidationFails() throws Exception {
    final TestDto testDto = new TestDto();

    mockMvc.perform(put(TestControllerConfig.URL)
        .contentType(TestUtil.APPLICATION_JSON_UTF8)
        .content(TestUtil.convertObjectToJsonBytes(testDto)))
        .andExpect(status().isBadRequest());

    verifyZeroInteractions(testCrudService);
  }

  @Test
  public void update_shouldReturn403_whenUpdateException() throws Exception {
    final TestDto testDto = TestDto.buildRandom();
    when(testCrudService.update(anyObject()))
        .thenThrow(new EntityUpdateException());

    mockMvc.perform(put(TestControllerConfig.URL)
        .content(TestUtil.convertObjectToJsonBytes(testDto))
        .contentType(TestUtil.APPLICATION_JSON_UTF8))
        .andExpect(status().isForbidden());

    verify(testCrudService, times(1))
        .update(anyObject());

    verifyNoMoreInteractions(testCrudService);
  }

  // endregion update


  // region delete

  @Test
  public void delete_shouldReturn204_whenSuccess() throws Exception {
    final Integer id = 3982;
    when(testCrudService.delete(id))
        .thenReturn(true);

    mockMvc.perform(delete(TestControllerConfig.URL + "/{id}", id))
        .andExpect(status().isNoContent());

    verify(testCrudService, times(1))
        .delete(id);

    verifyNoMoreInteractions(testCrudService);
  }


  @Test
  public void delete_shouldReturn404_whenNotFound() throws Exception {
    final Integer id = 8932;
    when(testCrudService.delete(id))
        .thenThrow(new EntityNotFoundException());

    mockMvc.perform(delete(TestControllerConfig.URL + "/{id}", id))
        .andExpect(status().isNotFound());

    verify(testCrudService, times(1))
        .delete(id);

    verifyNoMoreInteractions(testCrudService);
  }

  @Test
  public void delete_shouldReturn403_whenDeleteException() throws Exception {
    final Integer id = 9218;
    when(testCrudService.delete(id))
        .thenThrow(new EntityDeleteException());

    mockMvc.perform(delete(TestControllerConfig.URL + "/{id}", id))
        .andExpect(status().isForbidden());

    verify(testCrudService, times(1))
        .delete(id);

    verifyNoMoreInteractions(testCrudService);
  }


  // endregion delete
}