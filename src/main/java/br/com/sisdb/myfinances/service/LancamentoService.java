package br.com.sisdb.myfinances.service;

import br.com.sisdb.myfinances.enums.StatusLancamento;
import br.com.sisdb.myfinances.model.entity.Lancamento;

import java.util.List;
import java.util.Optional;

public interface LancamentoService {

    Lancamento salvar(Lancamento lancamento);
    Lancamento atualizar(Lancamento lancamento);
    void deletar(Lancamento lancamento);
    List<Lancamento> buscar(Lancamento lancamentoFiltro);
    void atualizarStatus(Lancamento lancamento, StatusLancamento status);
    void validar(Lancamento lancamento);
    Optional<Lancamento> obterPorId(Long id);


}
