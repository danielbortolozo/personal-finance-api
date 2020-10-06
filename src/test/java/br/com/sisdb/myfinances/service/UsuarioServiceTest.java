package br.com.sisdb.myfinances.service;


import br.com.sisdb.myfinances.exception.ErroAutenticacao;
import br.com.sisdb.myfinances.exception.RegraNegocioException;
import br.com.sisdb.myfinances.model.entity.Usuario;
import br.com.sisdb.myfinances.repository.UsuarioRepository;
import br.com.sisdb.myfinances.service.impl.UsuarioServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    UsuarioService usuarioService;
    @MockBean
    UsuarioRepository usuarioRepository;

    @Before
    public void setUp() {
       usuarioService = new UsuarioServiceImpl(usuarioRepository);
    }
    @Test
    public void deveAutenticarUmUsuarioComSucesso() {
        String email = "email@gmail.com";
        String senha ="senha";
        Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
        Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
        //acao
        Usuario result = usuarioService.autenticar(email, senha);
        //verificar
        Assertions.assertThat(result).isNotNull();
    }
    @Test(expected = ErroAutenticacao.class)
    public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComEmailInformacao() {
       //cenario
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        //acao
        usuarioService.autenticar("email@gmail.com", "senha");
    }
    @Test(expected = ErroAutenticacao.class)
    public void deveLancarErroQuandoSenhaNaoBatem() {
        //cenario
        Usuario usuario = Usuario.builder().email("daniel@fef.br").senha("senha").build();
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
        //acao
        usuarioService.autenticar("daniel@fef.br", "123");
    }

    @Test(expected = Test.None.class)
    public void deveValidarEmail() {
        //cenário
      //   usuarioRepository.deleteAll();
       Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        //acao
        usuarioService.validarEmail("daniel@fef.br");
    }

    @Test(expected = RegraNegocioException.class)
    public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
        //cenário
        Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
        //ação
        usuarioService.validarEmail("daniel@fef.br");
    }


}
