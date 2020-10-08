package br.com.sisdb.myfinances.repository;


import br.com.sisdb.myfinances.enums.StatusLancamento;
import br.com.sisdb.myfinances.enums.TipoLancamento;
import br.com.sisdb.myfinances.model.entity.Lancamento;
import static org.assertj.core.api.Assertions.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LancamentoRepositoryTest {

    @Autowired
    LancamentoRepository repository;
    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveSalvarLancamento() {
        Lancamento lancamento = criarLancamento();
        lancamento = repository.save(lancamento);
        assertThat(lancamento.getId()).isNotNull();
    }
    @Test
    public void deveDeletarUmLancamento() {
        Lancamento lancamento = criarEPersistirUmLancamento();
        lancamento = entityManager.find(Lancamento.class, lancamento.getId());
        repository.delete(lancamento);
        Lancamento lancamentoInexistente = entityManager.find(Lancamento.class, lancamento.getId());
        assertThat(lancamentoInexistente).isNull();
    }
    @Test
    public void deveAtualizarUmLancamento() {
       Lancamento lancamento = criarEPersistirUmLancamento();

       lancamento.setAno(2018);
       lancamento.setMes(10);
       lancamento.setDescricao("Atualizado");
       lancamento.setStatus(StatusLancamento.CANCELADO);
       repository.save(lancamento);
       Lancamento lancamentoAtualizado = entityManager.find(Lancamento.class, lancamento.getId());
       assertThat(lancamentoAtualizado.getAno()).isEqualTo(2018);
       assertThat(lancamentoAtualizado.getMes()).isEqualTo(10);
       assertThat(lancamentoAtualizado.getDescricao()).isEqualTo("Atualizado");
       assertThat(lancamentoAtualizado.getStatus()).isEqualTo(StatusLancamento.CANCELADO);
    }
    @Test
    public void deveBuscarUmLancamentoPorId() {
        Lancamento lancamento = criarEPersistirUmLancamento();
        Optional<Lancamento> lancamentoEncontrado =  repository.findById(lancamento.getId());
        assertThat(lancamentoEncontrado.isPresent()).isTrue();
    }

    private Lancamento criarEPersistirUmLancamento() {
        Lancamento lancamento = criarLancamento();
       return entityManager.persist(lancamento);
    }
    public static Lancamento criarLancamento() {
        return Lancamento.builder()
                .ano(2020)
                .mes(1)
                .descricao("descrição qualquer")
                .status(StatusLancamento.PENDENTE)
                .tipo(TipoLancamento.RECEITA)
                .dataCadastro(LocalDate.now()).build();
    }
}









