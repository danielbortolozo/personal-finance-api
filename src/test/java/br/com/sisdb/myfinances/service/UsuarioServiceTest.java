package br.com.sisdb.myfinances.service;


import br.com.sisdb.myfinances.exception.RegraNegocioException;
import br.com.sisdb.myfinances.model.entity.Usuario;
import br.com.sisdb.myfinances.repository.UsuarioRepository;
import br.com.sisdb.myfinances.service.impl.UsuarioServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    UsuarioService usuarioService;
    UsuarioRepository usuarioRepository;

    @Before
    public void setUp() {
       usuarioRepository = Mockito.mock(UsuarioRepository.class);
       usuarioService = new UsuarioServiceImpl(usuarioRepository);
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
