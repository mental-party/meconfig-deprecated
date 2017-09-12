package com.teammental.meconfig.web.controller;

import java.io.Serializable;
import java.util.List;

import com.teammental.meconfig.bll.service.BaseCrudService;
import com.teammental.meconfig.dto.Dto;
import com.teammental.meconfig.dto.IdDto;
import com.teammental.meconfig.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class BaseCrudController<ServiceT extends BaseCrudService, DtoT extends IdDto, IdT extends Serializable>
    extends BaseController {

  protected abstract ServiceT getBaseCrudService();

  protected abstract String getMappingUrlOfController();


  @GetMapping("")
  public ResponseEntity getAll() throws NotFoundException {
    List<DtoT> dtos = getBaseCrudService().findAll();
    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/{id}")
  public ResponseEntity getById(@PathVariable(value = "id") IdT id) throws NotFoundException {
    DtoT dto = (DtoT) getBaseCrudService().findById(id);
    return ResponseEntity.ok(dto);
  }

  @PostMapping()
  public ResponseEntity insert(@Validated @RequestBody DtoT dto) {
    dto = (DtoT) getBaseCrudService().insert(dto);
    String location = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path(getMappingUrlOfController() + "/" + dto.getId()).build().toUriString();

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .header("Location", location)
        .build();
  }

  @PutMapping()
  public ResponseEntity update(@Validated @RequestBody DtoT dto) throws NotFoundException {
    dto = (DtoT) getBaseCrudService().update(dto);
    return ResponseEntity.ok(dto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable(value = "id") IdT id) throws NotFoundException {
    boolean result = getBaseCrudService().delete(id);
    if (result) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

}
