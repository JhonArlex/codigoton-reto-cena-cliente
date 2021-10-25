package com.jhonocampo.codigotonretocenacliente.repository;

import com.jhonocampo.codigotonretocenacliente.model.Account;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
    
}
