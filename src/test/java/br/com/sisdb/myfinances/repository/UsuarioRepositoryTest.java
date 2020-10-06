package br.com.sisdb.myfinances.repository;

import br.com.sisdb.myfinances.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository repository;
    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveVerificarAExistenciaDeUmEmail() {
        //cenário
        Usuario usuario =criarUsuario();
        entityManager.persist(usuario);
        //ação/ execução
        boolean result = repository.existsByEmail("rod@rod.br");
        //Verificação
        Assertions.assertThat(result).isTrue();
    }
    @Test
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
        //cenário
        //ação
        boolean result = repository.existsByEmail("daniel@fef.br");
        //verificar a ação
        Assertions.assertThat(result).isFalse();
    }
    @Test
    public void devePersistirUmUsuarioNaBaseDeDados() {
        //cenario
        Usuario usuario =criarUsuario();

        //acao
        Usuario usuarioSalvo = repository.save(usuario);
        //verificacao
        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
    }
    @Test
    public void deveBuscarUsuarioPorEmail() {
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);
        //acao
       Optional<Usuario> result = repository.findByEmail("rod@rod.br");
       //verifica
        Assertions.assertThat(result.isPresent()).isTrue();
    }
    @Test
    public void deveRetornarVazioUsuarioPorEmailQuandoNaoExisteNaBase() {
        //cenario
        //acao
        Optional<Usuario> result = repository.findByEmail("rod@rod.br");
        //verifica
        Assertions.assertThat(result.isPresent()).isFalse();
    }
    public static Usuario criarUsuario() {
        return Usuario
                .builder()
                .nome("Rodrigo Boy")
                .email("rod@rod.br")
                .senha("senha123")
                .build();
    }
}
