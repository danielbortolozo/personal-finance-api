package br.com.sisdb.myfinances.service;


import br.com.sisdb.myfinances.exception.RegraNegocioException;
import br.com.sisdb.myfinances.model.entity.Usuario;
import br.com.sisdb.myfinances.repository.UsuarioRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Test(expected = Test.None.class)
    public void deveValidarEmail() {
        //cenário
         usuarioRepository.deleteAll();
        //acao
        usuarioService.validarEmail("daniel@fef.br");
    }

    @Test(expected = RegraNegocioException.class)
    public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
        //cenário
        Usuario usuario = Usuario.builder().nome("daniel").email("daniel@fef.br").build();
        usuarioRepository.save(usuario);
        //ação
        usuarioService.validarEmail("daniel@fef.br");
    }


}
