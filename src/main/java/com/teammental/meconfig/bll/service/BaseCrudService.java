package com.teammental.meconfig.bll.service;

import com.teammental.meconfig.dto.IdDto;
import com.teammental.meconfig.exception.entity.EntityDeleteException;
import com.teammental.meconfig.exception.entity.EntityInsertException;
import com.teammental.meconfig.exception.entity.EntityNotFoundException;
import com.teammental.meconfig.exception.entity.EntityUpdateException;

import java.io.Serializable;
import java.util.List;

public interface BaseCrudService<DtoT extends IdDto, IdT extends Serializable> {
  List<DtoT> findAll() throws EntityNotFoundException;

  DtoT findById(IdT id) throws EntityNotFoundException;

  IdT insert(DtoT dto) throws EntityInsertException;

  DtoT update(DtoT dto) throws EntityNotFoundException, EntityUpdateException;

  boolean delete(IdT id) throws EntityNotFoundException, EntityDeleteException;
}
