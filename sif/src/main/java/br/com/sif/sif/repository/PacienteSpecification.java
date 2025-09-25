package br.com.sif.sif.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import br.com.sif.sif.entity.Paciente;

public class PacienteSpecification {
    public static Specification<Paciente> comFiltros(Map<String, String> filtros) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            filtros.forEach((campo, valor) -> {
                if (valor != null && !valor.isEmpty()) {
                    if (campo.equals("nome") || campo.equals("nomeMae")) {
                        predicates.add(builder.like(builder.lower(root.get(campo)), "%" + valor.toLowerCase() + "%"));
                    } else if (campo.equals("cpf")) {
                        predicates.add(builder.equal(root.get(campo), valor));
                    }
                    // Adicionar lógica para data de nascimento se necessário
                }
            });

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
