package com.teammental.meconfig.bll.service;

import java.io.Serializable;
import java.util.List;

import com.teammental.meconfig.dto.IdDto;
import com.teammental.meconfig.exception.NotFoundException;

public interface BaseCrudService<DtoT extends IdDto, IdT extends Serializable> {
  List<DtoT> findAll() throws NotFoundException;
  DtoT findById(IdT id) throws NotFoundException;
  DtoT insert(DtoT dto);
  DtoT update(DtoT dto) throws NotFoundException;
  boolean delete(IdT id) throws NotFoundException;
}
