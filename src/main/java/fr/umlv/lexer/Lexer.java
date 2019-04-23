package fr.umlv.lexer;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FunctionalInterface
interface Lexer<T> {

    Optional<T> tryParse(String text);

    static <T> Lexer<T> create() {
        return (String text) -> {
            Objects.requireNonNull(text);
            return Optional.empty();
        };
    }

    static Lexer<String> from(String regex) {
        return from(Pattern.compile(regex));
    }

    static Lexer<String> from(Pattern pattern) {
        Objects.requireNonNull(pattern);
        requireOneCaptureGroup(pattern);
        return text -> Optional.of(pattern.matcher(text))
            .filter(Matcher::matches)
            .map(matcher -> matcher.group(1));
    }

    static void requireOneCaptureGroup(Pattern pattern) {
        if(pattern.matcher("").groupCount() != 1) throw new IllegalArgumentException();
    }

    default <R> Lexer<R> map(Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(mapper);
        return text -> tryParse(text).map(mapper);
    }

    default Lexer<T> or(Lexer<? extends T> other) {
        Objects.requireNonNull(other);
        return text -> this.tryParse(text).or(() -> other.tryParse(text));
    }

    default Lexer<T> with(String regex, Function<String, ? extends T> mapper) {
        return this.or(from(regex).map(mapper));
    }
}