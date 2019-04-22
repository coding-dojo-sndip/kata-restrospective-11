package fr.umlv.lexer;

import java.util.Objects;
import java.util.Optional;

@FunctionalInterface
interface Lexer<T> {

    Optional<T> tryParse(String input);

    static <T> Lexer<T> create() {
        return (String s) -> {
            Objects.requireNonNull(s);
            return Optional.empty();
        };
    }
}