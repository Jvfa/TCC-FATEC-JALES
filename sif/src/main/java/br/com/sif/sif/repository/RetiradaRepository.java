package br.com.sif.sif.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sif.sif.entity.Retirada;

@Repository
public interface RetiradaRepository extends JpaRepository<Retirada, Long>{
}
