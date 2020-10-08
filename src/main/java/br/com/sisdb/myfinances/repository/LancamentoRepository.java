package br.com.sisdb.myfinances.repository;

import br.com.sisdb.myfinances.enums.TipoLancamento;
import br.com.sisdb.myfinances.model.entity.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    @Query(value = "SELECT SUM(l.valor) FROM Lancamento l JOIN l.usuario u "+
                   "WHERE u.id=:idUsuario AND l.tipo=:tipo GROUP BY u")
    BigDecimal obterSaldoPorTipoLancamentoEUsuario(@Param("idUsuario") Long idUsuario, @Param("tipo") TipoLancamento tipo);


}
