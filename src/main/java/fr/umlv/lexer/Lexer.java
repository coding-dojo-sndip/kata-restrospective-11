package fr.umlv.lexer;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer<T> {

    private static final Lexer SIMPLE_LEXER = new Lexer<>();
    private static final Pattern ONLY_ONE_CAPTURE_GROUP = Pattern.compile("[^()]*\\([^()]+\\)[^()]*");
    private Pattern pattern;

    private Lexer(){ }

    private Lexer(Pattern pattern){
        this.pattern = pattern;
    }

    public static <T> Lexer create() {
        return SIMPLE_LEXER;
    }

    public static <T> Lexer<T> from(Pattern pattern) {
        Objects.requireNonNull(pattern);
        Matcher matcher = ONLY_ONE_CAPTURE_GROUP.matcher(pattern.pattern());
        if(matcher.matches()) return new Lexer<>(pattern);
        throw new IllegalArgumentException();
    }

    public static <T> Lexer<T> from(String regex) {
        Objects.requireNonNull(regex);
        return Lexer.from(Pattern.compile(regex));
    }

    public Optional<String> tryParse(String token) {
        Objects.requireNonNull(token);
        if(pattern == null) return Optional.empty();
        Matcher matcher = pattern.matcher(token);
        if(matcher.matches() && matcher.groupCount() == 1) {
            return Optional.of(matcher.group(1));
        }
        return Optional.empty();
    }
}
