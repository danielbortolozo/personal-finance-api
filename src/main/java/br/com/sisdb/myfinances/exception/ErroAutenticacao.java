package br.com.sisdb.myfinances.exception;

public class ErroAutenticacao extends RuntimeException {

    public ErroAutenticacao(String message) {
        super(message);
    }
}
