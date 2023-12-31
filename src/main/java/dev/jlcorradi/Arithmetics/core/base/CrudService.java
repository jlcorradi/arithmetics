package dev.jlcorradi.Arithmetics.core.base;

import dev.jlcorradi.Arithmetics.core.BusinessException;
import dev.jlcorradi.Arithmetics.core.commons.RecordStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CrudService<T extends SoftDeletable, ID, R extends JpaRepository<T, ID>> {

  default Optional<T> get(ID id) {
    return getRepository().findById(id);
  }

  @Transactional
  default T save(T entity)
      throws BusinessException {
    performValidations(entity);
    return getRepository().save(entity);
  }

  default void performValidations(T entity)
      throws BusinessException {
  }

  default List<T> list() {
    return getRepository().findAll();
  }

  @Transactional
  default void delete(ID id) {
    getRepository().findById(id).ifPresent(entity -> {
      entity.setStatus(RecordStatus.INACTIVE);
      save(entity);
    });
  }

  R getRepository();

  default boolean existsById(ID id) {
    return getRepository().existsById(id);
  }

}
