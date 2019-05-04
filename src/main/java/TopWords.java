import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TopWords {
    private static final Pattern PATTERN = Pattern.compile("[\\s|[^\\w|']]|_");
    private static final Pattern PATTERN_WORD = Pattern.compile("[\\w+'?\\w*]+");

    public static List<String> top3(final String s) {
        final List<String> result = new ArrayList<>(3);
        Arrays.stream(PATTERN.split(s))
                .filter(Objects::nonNull)
                .filter(word -> !word.isEmpty() && !word.isBlank())
                .filter(word -> PATTERN_WORD.matcher(word).matches())
                .filter(word -> !word.replaceAll("'", "").isEmpty())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((a, b) -> b.getValue().equals(a.getValue()) ? a.getKey().compareToIgnoreCase(b.getKey()) : Long.compare(b.getValue(), a.getValue()))
                .forEach(entry -> {
                    if (result.size() < 3) {
                        result.add(entry.getKey().toLowerCase(Locale.ENGLISH));
                    }
                });

        return result;
    }
}