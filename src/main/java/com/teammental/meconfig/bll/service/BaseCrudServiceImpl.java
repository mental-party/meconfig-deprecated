package com.teammental.meconfig.bll.service;

import com.teammental.meconfig.dto.Dto;
import com.teammental.meconfig.dto.IdDto;
import com.teammental.meconfig.exception.entity.EntityDeleteException;
import com.teammental.meconfig.exception.entity.EntityInsertException;
import com.teammental.meconfig.exception.entity.EntityNotFoundException;
import com.teammental.meconfig.exception.entity.EntityUpdateException;
import com.teammental.memapper.MeMapper;
import com.teammental.memapper.util.mapping.MapByFieldNameUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseCrudServiceImpl<DtoT extends IdDto, IdT extends Serializable>
    implements BaseCrudService<DtoT, IdT> {

  private static final Logger LOGGER = LoggerFactory.getLogger(BaseCrudServiceImpl.class);

  // region override methods

  @Override
  public final List<DtoT> findAll() throws EntityNotFoundException {
    LOGGER.debug("findAll of " + getDtoClass().getSimpleName() + " started");
    try {
      List<DtoT> dtos = doFindAll();
      LOGGER.debug("findAll of " + getDtoClass().getSimpleName() + " ended");
      return dtos;
    } catch (EntityNotFoundException ex) {
      LOGGER.error("findAll of " + getDtoClass().getSimpleName()
          + " throwed a EntityNotFoundException");
      throw ex;
    }
  }

  @Override
  public final DtoT findById(final IdT id) throws EntityNotFoundException {
    LOGGER.debug("findById of " + getDtoClass().getSimpleName()
        + " started, with parameter: id=" + id);
    try {
      DtoT dto = doFindById(id);
      LOGGER.debug("findById of " + getDtoClass().getSimpleName()
          + " ended, with parameter: id=" + id);
      return dto;
    } catch (EntityNotFoundException ex) {
      LOGGER.error("findById of " + getDtoClass().getSimpleName()
          + " throwed a EntityNotFoundException");
      throw ex;
    }
  }

  @Override
  public final IdT insert(final DtoT dto) throws EntityInsertException {
    LOGGER.debug("insert of " + getDtoClass().getSimpleName()
        + " started, with parameter: dto=" + dto.toString());
    try {
      IdT id = doInsert(dto);
      LOGGER.debug("insert of " + getDtoClass().getSimpleName() + " ended");
      return id;
    } catch (EntityInsertException ex) {
      LOGGER.error("insert of " + getDtoClass().getSimpleName()
          + " throwed a EntityInsertException");
      throw ex;
    }
  }

  @Override
  public final DtoT update(final DtoT dto)
      throws EntityNotFoundException, EntityUpdateException {
    LOGGER.debug("update of " + getDtoClass().getSimpleName()
        + " started, with parameter: dto=" + dto.toString());
    try {
      DtoT dtoResult = doUpdate(dto);
      LOGGER.debug("update of " + getDtoClass().getSimpleName() + " ended");
      return dtoResult;
    } catch (EntityUpdateException ex) {
      LOGGER.error("update of " + getDtoClass().getSimpleName()
          + " throwed an EntityUpdateException");
      throw ex;
    } catch (EntityNotFoundException ex) {
      LOGGER.error("update of " + getDtoClass().getSimpleName()
          + " throwed an EntityNotFoundException");
      throw ex;
    }
  }

  @Override
  public final boolean delete(final IdT id) throws EntityNotFoundException, EntityDeleteException {
    LOGGER.debug("delete of " + getDtoClass().getSimpleName()
        + " started, with parameter: id=" + id.toString());
    try {
      boolean result = doDelete(id);
      LOGGER.debug("delete of " + getDtoClass().getSimpleName() + " ended");
      return result;
    } catch (EntityNotFoundException ex) {
      LOGGER.error("delete of " + getDtoClass().getSimpleName()
          + " throwed an EntityNotFoundException");
      throw ex;
    } catch (EntityDeleteException ex) {
      LOGGER.error("delete of " + getDtoClass().getSimpleName()
          + " throwed an EntityDeleteException");
      throw ex;
    }
  }

  // endregion override methods

  // region protected default methods

  protected List<DtoT> doFindAll() throws EntityNotFoundException {

    List entities = getRepository().findAll();

    Optional<List<DtoT>> optionalDtos = MeMapper.getMapperFromList(entities)
        .mapToList(getDtoClass());

    if (!optionalDtos.isPresent() || optionalDtos.get().isEmpty()) {
      throw new EntityNotFoundException();
    }

    return optionalDtos.get();
  }

  protected DtoT doFindById(final IdT id) throws EntityNotFoundException {

    Object entity = getRepository().findOne(id);
    Optional<DtoT> dto = MeMapper.getMapperFrom(entity).mapTo(getDtoClass());

    if (!dto.isPresent()) {
      throw new EntityNotFoundException();
    }

    return dto.get();
  }

  protected IdT doInsert(final DtoT dto) throws EntityInsertException {

    Optional optionalEntity = MeMapper.getMapperFrom(dto)
        .mapTo(getEntityClass());

    try {
      Object entity = getRepository().saveAndFlush(optionalEntity.get());
      Optional<DtoT> optionalDto = MeMapper.getMapperFrom(entity)
          .mapTo(getDtoClass());

      return (IdT) optionalDto.get().getId();
    } catch (Exception ex) {
      throw new EntityInsertException("", ex);
    }

  }

  protected DtoT doUpdate(final DtoT dto) throws EntityNotFoundException, EntityUpdateException {
    Object entity = getRepository().findOne(dto.getId());

    if (entity == null) {
      throw new EntityNotFoundException();
    }

    entity = MapByFieldNameUtil.map(dto, entity);

    try {
      Object resultEntity = getRepository().saveAndFlush(entity);
      Optional<Dto> optionalDto = MeMapper.getMapperFrom(resultEntity)
          .mapTo(getDtoClass());

      return (DtoT) optionalDto.get();
    } catch (Exception ex) {
      throw new EntityUpdateException("", ex);
    }
  }

  protected boolean doDelete(final IdT id) throws EntityNotFoundException, EntityDeleteException {

    Object entity = getRepository().findOne(id);
    if (entity == null) {
      throw new EntityNotFoundException();
    }
    try {
      getRepository().delete(entity);
      return true;
    } catch (Exception ex) {
      throw new EntityDeleteException("", ex);
    }

  }

  // endregion protected default methods

  // region abstract methods

  protected abstract JpaRepository getRepository();

  protected abstract Class<?> getDtoClass();

  protected abstract Class<?> getEntityClass();

  // endregion abstract methods

}
