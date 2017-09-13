package com.teammental.meconfig.web.controller;

import java.io.Serializable;
import java.util.List;

import com.teammental.meconfig.bll.service.BaseCrudService;
import com.teammental.meconfig.dto.IdDto;
import com.teammental.meconfig.exception.entity.EntityNotFoundException;
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

  // region request methods

  @GetMapping("")
  public final ResponseEntity getAll() throws EntityNotFoundException {
    final List<DtoT> dtos = doGetAll();
    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/{id}")
  public final ResponseEntity getById(@PathVariable(value = "id") final IdT id) throws EntityNotFoundException {
    DtoT dto = doGetById(id);
    return ResponseEntity.ok(dto);
  }

  @PostMapping()
  public final ResponseEntity insert(@Validated @RequestBody final DtoT dto) {
    IdT id = doInsert(dto);
    String location = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path(getMappingUrlOfController() + "/" + id.toString()).build().toUriString();

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .header("Location", location)
        .build();
  }

  @PutMapping()
  public final ResponseEntity update(@Validated @RequestBody final DtoT dto) throws EntityNotFoundException {
    DtoT dtoResult = doUpdate(dto);
    return ResponseEntity.ok(dtoResult);
  }

  @DeleteMapping("/{id}")
  public final ResponseEntity delete(@PathVariable(value = "id") final IdT id) throws EntityNotFoundException {
    boolean result = doDelete(id);
    if (result) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  // endregion

  // region protected methods

  protected List<DtoT> doGetAll() throws EntityNotFoundException {
    List<DtoT> dtos = getBaseCrudService().findAll();
    return dtos;
  }

  protected DtoT doGetById(final IdT id) throws EntityNotFoundException {
    DtoT dtoResult = (DtoT) getBaseCrudService().findById(id);
    return dtoResult;
  }

  protected IdT doInsert(final DtoT dto) {
    DtoT dtoResult = (DtoT) getBaseCrudService().insert(dto);
    return (IdT) dtoResult.getId();
  }

  protected DtoT doUpdate(final DtoT dto) throws EntityNotFoundException {
    DtoT dtoResult = (DtoT) getBaseCrudService().update(dto);
    return dtoResult;
  }

  protected boolean doDelete(final IdT id) throws EntityNotFoundException {
    boolean result = getBaseCrudService().delete(id);
    return result;
  }

  // region abstract methods

  protected abstract ServiceT getBaseCrudService();

  protected abstract String getMappingUrlOfController();

  // endregion abstract methods

  // endregion
}
