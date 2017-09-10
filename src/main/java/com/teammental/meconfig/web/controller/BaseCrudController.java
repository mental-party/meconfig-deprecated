package com.teammental.meconfig.web.controller;

import java.io.Serializable;
import java.util.List;

import com.teammental.meconfig.bll.service.BaseCrudService;
import com.teammental.meconfig.dto.Dto;
import com.teammental.meconfig.dto.IdDto;
import com.teammental.meconfig.exception.NotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class BaseCrudController<ServiceT extends BaseCrudService, DtoT extends IdDto, IdT extends Serializable>
    extends BaseController {

  protected abstract ServiceT getBaseCrudService();


  @GetMapping("")
  public List<DtoT> getAll() throws NotFoundException {
    return getBaseCrudService().findAll();
  }

  @GetMapping("/{id}")
  public DtoT getById(@PathVariable(value = "id") IdT id) throws NotFoundException {
    return (DtoT) getBaseCrudService().findById(id);
  }

  @PostMapping()
  public DtoT insert(@Validated @RequestBody DtoT dto) {
    return (DtoT) getBaseCrudService().insert(dto);
  }

  @PutMapping()
  public DtoT update(@Validated @RequestBody DtoT dto) throws NotFoundException {
    return (DtoT) getBaseCrudService().update(dto);
  }

  @DeleteMapping("/{id}")
  public boolean delete(@PathVariable(value = "id") IdT id) throws NotFoundException {
    return getBaseCrudService().delete(id);
  }

}
