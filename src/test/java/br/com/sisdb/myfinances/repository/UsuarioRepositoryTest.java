package br.com.sisdb.myfinances.repository;

import br.com.sisdb.myfinances.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository repository;
    @Test
    public void deveVerificarAExistenciaDeUmEmail() {
        //cenário
        Usuario usuario = Usuario.builder().nome("daniel").email("daniel@fef.br").build();
        repository.save(usuario);
        //ação/ execução
        boolean result = repository.existsByEmail("daniel@fef.br");
        //Verificação
        Assertions.assertThat(result).isTrue();
    }
    @Test
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
        //cenário
        repository.deleteAll();
        //ação
        boolean result = repository.existsByEmail("daniel@fef.br");
        //verificar a ação
        Assertions.assertThat(result).isFalse();
    }

}
