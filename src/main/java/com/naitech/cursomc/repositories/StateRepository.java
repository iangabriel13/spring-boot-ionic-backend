package com.naitech.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naitech.cursomc.domain.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {

}
