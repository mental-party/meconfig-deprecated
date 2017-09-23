package com.teammental.meconfig.bll.service;

import com.teammental.meconfig.exception.entity.EntityDeleteException;
import com.teammental.meconfig.exception.entity.EntityInsertException;
import com.teammental.meconfig.exception.entity.EntityNotFoundException;
import com.teammental.meconfig.exception.entity.EntityUpdateException;
import com.teammental.meconfig.testapp.TestCrudService;
import com.teammental.meconfig.testapp.TestCrudServiceImpl;
import com.teammental.meconfig.testapp.TestDto;
import com.teammental.meconfig.testapp.TestEntity;
import com.teammental.meconfig.testapp.TestRepository;
import com.teammental.memapper.MeMapper;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class BaseCrudServiceImplTest {

  @MockBean
  private TestRepository testRepository;

  @InjectMocks
  private TestCrudService testCrudService = new TestCrudServiceImpl();

  @Before
  public void setUp() {

    MockitoAnnotations.initMocks(this);
  }

  // region findAll

  @Test
  public void findAll_shouldReturnAll_whenSuccess() throws EntityNotFoundException {

    final TestEntity testEntity1 = TestEntity.buildRandom();
    final TestEntity testEntity2 = TestEntity.buildRandom();
    final List<TestEntity> testEntities = Arrays.asList(testEntity1, testEntity2);
    final List<TestDto> expectedDtos = (List<TestDto>) MeMapper.getMapperFromList(testEntities)
        .mapToList(TestDto.class).get();
    final String expectedDtoString = expectedDtos.stream()
        .map(testDto -> testDto.toString())
        .reduce("", String::concat);


    when(testRepository.findAll())
        .thenReturn(testEntities);

    final List<TestDto> actualTestDtos = testCrudService.findAll();
    final String actualDtoString = actualTestDtos.stream()
        .map(testDto -> testDto.toString())
        .reduce("", String::concat);

    assertEquals(expectedDtoString, actualDtoString);

    verify(testRepository, times(1))
        .findAll();

    verifyNoMoreInteractions(testRepository);
  }

  @Test
  public void findAll_shouldThrowNotFoundException_whenNotFound() {

    when(testRepository.findAll())
        .thenReturn(null);

    try {
      testCrudService.findAll();
      fail();
    } catch (EntityNotFoundException e) {
      verify(testRepository, times(1))
          .findAll();
      verifyNoMoreInteractions(testRepository);
    }
  }

  // endregion findAll

  // region findOne

  @Test
  public void findOne_shouldReturnItem_whenFound() throws EntityNotFoundException {

    final TestEntity testEntity = TestEntity.buildRandom();
    final Integer id = testEntity.getId();
    final TestDto expectedDto = (TestDto) MeMapper.getMapperFrom(testEntity)
        .mapTo(TestDto.class).get();

    when(testRepository.findOne(id))
        .thenReturn(testEntity);

    final TestDto actualDto = testCrudService.findById(id);

    assertEquals(expectedDto.toString(), actualDto.toString());

    verify(testRepository, times(1))
        .findOne(id);
    verifyNoMoreInteractions(testRepository);
  }

  @Test
  public void findOne_shouldThrowNotFoundException_whenNotFound() {

    final Integer id = 58439;
    when(testRepository.findOne(id))
        .thenReturn(null);

    try {
      testCrudService.findById(id);
      fail();
    } catch (EntityNotFoundException e) {
      verify(testRepository, times(1))
          .findOne(id);
      verifyNoMoreInteractions(testRepository);
    }
  }

  // endregion findOne

  // region insert

  @Test
  public void insert_shouldThrowInsertException_whenFails() {

    final TestDto testDto = TestDto.buildRandom();

    when(testRepository.saveAndFlush(anyObject()))
        .thenThrow(new RuntimeException());

    try {
      testCrudService.insert(testDto);
      fail();
    } catch (EntityInsertException e) {
      verify(testRepository, times(1))
          .saveAndFlush(anyObject());
      verifyNoMoreInteractions(testRepository);
    }
  }

  @Test
  public void insert_shouldReturnId_whenSuccess() throws EntityInsertException {

    final TestEntity expectedEntity = TestEntity.buildRandom();
    final Integer expectedId = expectedEntity.getId();
    final TestDto expectedDto = (TestDto) MeMapper.getMapperFrom(expectedEntity)
        .mapTo(TestDto.class).get();

    when(testRepository.saveAndFlush(anyObject()))
        .thenReturn(expectedEntity);

    final Integer actualId = testCrudService.insert(expectedDto);

    assertEquals(expectedId, actualId);

    verify(testRepository, times(1))
        .saveAndFlush(anyObject());
  }

  // endregion insert

  // region update

  @Test
  public void update_shouldUpdateAndReturnUpdatedItem()
      throws EntityUpdateException, EntityNotFoundException {

    final TestEntity originalEntity = TestEntity.buildRandom();

    final TestEntity updatedEntity = TestEntity.buildRandom();
    updatedEntity.setId(originalEntity.getId());

    final TestDto updatedDto = (TestDto) MeMapper.getMapperFrom(updatedEntity)
        .mapTo(TestDto.class).get();

    when(testRepository.findOne(anyInt()))
        .thenReturn(originalEntity);

    when(testRepository.saveAndFlush(anyObject()))
        .thenReturn(updatedEntity);

    final TestDto actualDto = testCrudService.update(updatedDto);

    final String expectedDtoString = updatedDto.toString();
    final String actualDtoString = actualDto.toString();

    assertEquals(expectedDtoString, actualDtoString);

    verify(testRepository, times(1))
        .findOne(anyInt());

    verify(testRepository, times(1))
        .saveAndFlush(anyObject());

    verifyNoMoreInteractions(testRepository);
  }


  @Test
  public void update_shouldThrowUpdateException_whenFails() throws EntityNotFoundException {

    final TestEntity originalEntity = TestEntity.buildRandom();

    final TestDto updatedDto = TestDto.buildRandom();

    when(testRepository.findOne(anyInt()))
        .thenReturn(originalEntity);

    when(testRepository.saveAndFlush(anyObject()))
        .thenThrow(new RuntimeException());

    try {
      testCrudService.update(updatedDto);
      fail();
    } catch (EntityUpdateException e) {
      verify(testRepository, times(1))
          .findOne(anyInt());
      verify(testRepository, times(1))
          .saveAndFlush(anyObject());
      verifyNoMoreInteractions(testRepository);
    }
  }

  @Test
  public void update_shouldThrowNotFoundException_whenNotFound() throws EntityUpdateException {

    final TestEntity originalEntity = TestEntity.buildRandom();
    final TestDto updatedDto = TestDto.buildRandom();

    when(testRepository.findOne(anyInt()))
        .thenReturn(null);

    when(testRepository.saveAndFlush(anyObject()))
        .thenReturn(originalEntity);

    try {
      testCrudService.update(updatedDto);
      fail();
    } catch (EntityNotFoundException e) {
      verify(testRepository, times(1))
          .findOne(anyInt());

      verify(testRepository, times(0))
          .saveAndFlush(anyObject());

      verifyNoMoreInteractions(testRepository);
    }
  }

  // endregion update

  // region delete

  @Test
  public void delete_shouldThrowNotFoundException_whenNotFound() throws EntityDeleteException {

    final Integer id = 84329;
    when(testRepository.findOne(id))
        .thenReturn(null);

    try {
      testCrudService.delete(id);
      fail();
    } catch (EntityNotFoundException e) {
      verify(testRepository, times(1))
          .findOne(id);

      verifyNoMoreInteractions(testRepository);
    }
  }

  @Test
  public void delete_shouldDeleteItem_whenFoundItem()
      throws EntityDeleteException, EntityNotFoundException {

    final Integer id = 8932;
    final TestEntity testEntity = TestEntity.buildRandom();

    when(testRepository.findOne(id))
        .thenReturn(testEntity);

    boolean result = testCrudService.delete(id);

    verify(testRepository, times(1))
        .findOne(id);

    verify(testRepository, times(1))
        .delete((TestEntity) anyObject());
  }


  // endregion delete

}