package dev.jlcorradi.Arithmetics.core.service;

import dev.jlcorradi.Arithmetics.core.base.CrudService;
import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import dev.jlcorradi.Arithmetics.core.dao.ArithmeticsUserDao;

import java.util.Optional;

public interface ArithmeticsUserService extends CrudService<ArithmeticsUser, Long, ArithmeticsUserDao> {
  Optional<ArithmeticsUser> findByUsername(String username);
}
