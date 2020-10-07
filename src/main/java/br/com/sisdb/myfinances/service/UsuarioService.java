package br.com.sisdb.myfinances.service;

import br.com.sisdb.myfinances.model.entity.Usuario;

import java.util.Optional;

public interface UsuarioService {

    Usuario autenticar(String email, String senha);
    Usuario salvarUsuario(Usuario usuario);
    void validarEmail(String email);
    Optional<Usuario> obterPorId(Long id);
}
