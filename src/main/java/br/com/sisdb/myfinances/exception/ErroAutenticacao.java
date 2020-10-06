package br.com.sisdb.myfinances.exception;

import org.springframework.stereotype.Component;


public class ErroAutenticacao extends RuntimeException {

    public ErroAutenticacao(String message) {
        super(message);
    }
}
