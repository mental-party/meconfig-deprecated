package com.teammental.meconfig.web.controller;

import com.teammental.meconfig.bll.service.BaseCrudService;
import com.teammental.meconfig.dto.IdDto;
import com.teammental.meconfig.exception.entity.EntityDeleteException;
import com.teammental.meconfig.exception.entity.EntityInsertException;
import com.teammental.meconfig.exception.entity.EntityNotFoundException;
import com.teammental.meconfig.exception.entity.EntityUpdateException;

import java.io.Serializable;
import java.util.List;

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

public abstract class BaseCrudController<ServiceT extends BaseCrudService,
    DtoT extends IdDto, IdT extends Serializable>
    extends BaseController {

  // region request methods

  /**
   * Get all DtoT items.
   * @return HttpStatus=200, List of DtoT objects
   * @throws EntityNotFoundException if no item found
   */
  @GetMapping("")
  public final ResponseEntity getAll() throws EntityNotFoundException {
    final List<DtoT> dtos = doGetAll();
    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/{id}")
  public final ResponseEntity getById(@PathVariable(value = "id") final IdT id)
      throws EntityNotFoundException {
    DtoT dto = doGetById(id);
    return ResponseEntity.ok(dto);
  }

  /**
   * Insert a new DtoT item.
   * @param dto DtoT object to be inserted
   * @return HttpStatus=201, Location of newly created item's detail url
   * @throws EntityInsertException if insert transaction fails
   */
  @PostMapping()
  public final ResponseEntity insert(@Validated @RequestBody final DtoT dto)
      throws EntityInsertException {
    Serializable id = doInsert(dto);
    String location = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path(getMappingUrlOfController() + "/" + id.toString()).build().toUriString();

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .header("Location", location)
        .build();
  }

  /**
   * Update a DtoT item.
   * @param dto DtoT object to be updated.
   * @return HttpStatus=200, DtoT object newly updated
   * @throws EntityNotFoundException if the item is not already in DB
   * @throws EntityUpdateException if update process fails
   */
  @PutMapping()
  public final ResponseEntity update(@Validated @RequestBody final DtoT dto)
      throws EntityNotFoundException, EntityUpdateException {
    DtoT dtoResult = doUpdate(dto);
    return ResponseEntity.ok(dtoResult);
  }

  /**
   * Delete a DtoT item.
   * @param id id of the DtoT item to be deleted
   * @return HttpStatus=204
   * @throws EntityNotFoundException if the item is not already in DB
   * @throws EntityDeleteException if delete process fails
   */
  @DeleteMapping("/{id}")
  public final ResponseEntity delete(@PathVariable(value = "id") final IdT id)
      throws EntityNotFoundException, EntityDeleteException {
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

  protected Serializable doInsert(final DtoT dto) throws EntityInsertException {
    Serializable id = getBaseCrudService().insert(dto);
    return id;
  }

  protected DtoT doUpdate(final DtoT dto) throws EntityNotFoundException, EntityUpdateException {
    DtoT dtoResult = (DtoT) getBaseCrudService().update(dto);
    return dtoResult;
  }

  protected boolean doDelete(final IdT id) throws EntityNotFoundException, EntityDeleteException {
    boolean result = getBaseCrudService().delete(id);
    return result;
  }

  // region abstract methods

  protected abstract ServiceT getBaseCrudService();

  protected abstract String getMappingUrlOfController();

  // endregion abstract methods

  // endregion
}
