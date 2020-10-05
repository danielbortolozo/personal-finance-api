package br.com.sisdb.myfinances.repository;

import br.com.sisdb.myfinances.model.entity.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
}
