package com.naitech.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naitech.cursomc.domain.ClientOrder;

@Repository
public interface OrderRepository extends JpaRepository<ClientOrder, Integer> {

}
