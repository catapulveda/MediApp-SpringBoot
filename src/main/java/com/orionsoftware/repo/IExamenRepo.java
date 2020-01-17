package com.orionsoftware.repo;

import com.orionsoftware.model.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExamenRepo extends JpaRepository<Examen, Integer> {


}