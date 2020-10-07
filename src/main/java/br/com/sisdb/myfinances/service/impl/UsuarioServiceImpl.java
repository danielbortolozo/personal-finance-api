package br.com.sisdb.myfinances.service.impl;

import br.com.sisdb.myfinances.exception.ErroAutenticacao;
import br.com.sisdb.myfinances.exception.RegraNegocioException;
import br.com.sisdb.myfinances.model.entity.Usuario;
import br.com.sisdb.myfinances.repository.UsuarioRepository;
import br.com.sisdb.myfinances.service.UsuarioService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioServiceImpl  implements UsuarioService {


    private final UsuarioRepository repository;

    @Autowired
    public UsuarioServiceImpl(final UsuarioRepository repository) {
        this.repository = repository;

    }





    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario = repository.findByEmail(email);
        if (!usuario.isPresent()){
            throw new ErroAutenticacao("Email inválido(a).");
        }
        if (!usuario.get().getSenha().equals(senha)){
            throw new ErroAutenticacao("Senha inválida.");
        }


        return usuario.get();
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());

        return repository.save(usuario);
    }
    @Override
    public void validarEmail(String email) {
        boolean existe = repository.existsByEmail(email);
        if (existe) {
            throw new RegraNegocioException("Já existe um usuário cadastrado com este email");
        }
    }

    @Override
    public Optional<Usuario> obterPorId(Long id) {
        return repository.findById(id);
    }
}









