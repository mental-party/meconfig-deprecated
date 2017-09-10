package com.teammental.meconfig.bll.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.teammental.meconfig.dto.Dto;
import com.teammental.meconfig.dto.IdDto;
import com.teammental.meconfig.exception.NotFoundException;
import com.teammental.memapper.MeMapper;
import com.teammental.memapper.util.mapping.MapByFieldNameUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.annotation.Validated;

public abstract class BaseCrudServiceImpl<DtoT extends IdDto, IdT extends Serializable> implements BaseCrudService<DtoT, IdT> {
  protected abstract JpaRepository getRepository();

  protected abstract Class<?> getDtoClass();

  protected abstract Class<?> getEntityClass();

  @Override
  public List<DtoT> findAll() throws NotFoundException {
    List entities = getRepository().findAll();

    Optional<List<DtoT>> optionalDtos = MeMapper.getMapperFromList(entities)
        .mapToList(getDtoClass());

    if (!optionalDtos.isPresent() || optionalDtos.get().isEmpty()) {
      throw new NotFoundException();
    }

    return optionalDtos.get();
  }

  @Override
  public DtoT findById(IdT id) throws NotFoundException {
    Object entity = getRepository().findOne(id);
    Optional<DtoT> dto = MeMapper.getMapperFrom(entity).mapTo(getDtoClass());

    if (!dto.isPresent()) {
      throw new NotFoundException();
    }

    return dto.get();
  }

  @Override
  public DtoT insert(@Validated DtoT dto) {
    Optional optionalEntity = MeMapper.getMapperFrom(dto)
        .mapTo(getEntityClass());

    Object entity = getRepository().saveAndFlush(optionalEntity.get());

    Optional<Dto> optionalDto = MeMapper.getMapperFrom(entity)
        .mapTo(getDtoClass());

    return (DtoT) optionalDto.get();
  }

  @Override
  public DtoT update(@Validated DtoT dto) throws NotFoundException {

    Object entity = getRepository().findOne(dto.getId());

    if (entity == null) {
      throw new NotFoundException();
    }

    entity = MapByFieldNameUtil.map(dto, entity);

    entity = getRepository().saveAndFlush(entity);

    Optional<Dto> optionalDto = MeMapper.getMapperFrom(entity)
        .mapTo(getDtoClass());

    return (DtoT) optionalDto.get();
  }

  @Override
  public boolean delete(IdT id) throws NotFoundException {
    Object entity = getRepository().findOne(id);
    if (entity == null) {
      throw new NotFoundException();
    }
    getRepository().delete(entity);

    return true;
  }
}
