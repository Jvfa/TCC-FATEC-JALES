package br.com.sif.sif.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sif.sif.entity.ItemProcesso;

@Repository
public interface ItemProcessoRepository extends JpaRepository<ItemProcesso, Long>{
    
}
