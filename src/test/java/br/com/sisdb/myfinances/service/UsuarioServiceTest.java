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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Optional;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @SpyBean
    UsuarioServiceImpl usuarioService;
    @MockBean
    UsuarioRepository usuarioRepository;
    @Test(expected = Test.None.class)
    public void deveSalvarUmUsuario() {
        //cenario

        Mockito.doNothing().when(usuarioService).validarEmail(Mockito.anyString());
        Usuario usuario = Usuario.builder()
                .id(1l)
                .nome("nome")
                .email("email@gmail.com")
                .senha("senha").build();
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
        //acao
        Usuario usuarioSalvo = usuarioService.salvarUsuario(new Usuario());

        //verificar
        Assertions.assertThat(usuarioSalvo).isNotNull();
        Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1l);
        Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
        Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@gmail.com");
        Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
    }
    @Test(expected = RegraNegocioException.class)
    public void naoDeveCadastrarUmUsuarioComEmailJaCadastrado() {
        //cenario
        String email = "daniel@fef.br";
        Usuario usuario = Usuario.builder().email(email).build();
        Mockito.doThrow(RegraNegocioException.class).when(usuarioService).validarEmail(email);
        //acao
         usuarioService.salvarUsuario(usuario);
        //verificacao
        Mockito.verify(usuarioRepository, Mockito.never()).save(usuario);
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
    @Test
    public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComEmailInformacao() {
       //cenario
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        //acao
        Throwable exception = Assertions.catchThrowable(() -> usuarioService.autenticar("deniel@fef.br", "senha"));
        Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Email inválido(a).");
    }
    @Test
    public void deveLancarErroQuandoSenhaNaoBatem() {
        //cenario
        Usuario usuario = Usuario.builder().email("daniel@fef.br").senha("senha").build();
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
        //acao
       Throwable exception = Assertions.catchThrowable(() -> usuarioService.autenticar("daniel@fef.br", "'122"));
       Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha inválida.");
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
