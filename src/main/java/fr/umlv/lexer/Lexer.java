package fr.umlv.lexer;

import java.util.Objects;
import java.util.Optional;

public class Lexer<T> {

    private static Lexer instance;

    private Lexer(){
    }

    public static <T> Lexer<T> create() {
        if(instance == null) {
            instance = new Lexer<>();
        }
        return instance;
    }

    public Optional<T> tryParse(String token) {
        Objects.requireNonNull(token);
        return Optional.empty();
    }
}
