package com.orionsoftware.repo;

import com.orionsoftware.model.Medico;
import com.orionsoftware.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMedicoRepo extends JpaRepository<Medico, Integer> {


}