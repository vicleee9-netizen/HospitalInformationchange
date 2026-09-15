package cn.bugstack.ai.domain.symptom.model.valobj;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SymptomDuration {

    private static final Pattern DAY_PATTERN = Pattern.compile("^(\\d+|一|二|两|三|四|五|六|七|八|九|十)\\s*天$");
    private static final Pattern DAY_EVIDENCE_PATTERN = Pattern.compile("(\\d+|一|二|两|三|四|五|六|七|八|九|十)\\s*天");
    private static final Map<String, Integer> CHINESE_NUMBERS = Map.ofEntries(
            Map.entry("一", 1),
            Map.entry("二", 2),
            Map.entry("两", 2),
            Map.entry("三", 3),
            Map.entry("四", 4),
            Map.entry("五", 5),
            Map.entry("六", 6),
            Map.entry("七", 7),
            Map.entry("八", 8),
            Map.entry("九", 9),
            Map.entry("十", 10)
    );

    private final int value;
    private final String unit;
    private final Evidence evidence;

    private SymptomDuration(int value, String unit, Evidence evidence) {
        this.value = value;
        this.unit = unit;
        this.evidence = evidence;
    }

    public static Optional<SymptomDuration> normalize(String rawDuration, Evidence evidence) {
        if (rawDuration == null) {
            return Optional.empty();
        }

        Matcher matcher = DAY_PATTERN.matcher(rawDuration.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        Optional<Integer> value = parseValue(matcher.group(1));
        if (value.isEmpty() || value.get() <= 0 || !isSupportedBy(value.get(), evidence)) {
            return Optional.empty();
        }
        return Optional.of(new SymptomDuration(value.get(), "天", evidence));
    }

    public int getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public Evidence getEvidence() {
        return evidence;
    }

    private static Optional<Integer> parseValue(String rawValue) {
        Integer chineseValue = CHINESE_NUMBERS.get(rawValue);
        if (chineseValue != null) {
            return Optional.of(chineseValue);
        }

        try {
            return Optional.of(Integer.parseInt(rawValue));
        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }

    private static boolean isSupportedBy(int expectedValue, Evidence evidence) {
        Matcher matcher = DAY_EVIDENCE_PATTERN.matcher(evidence.getText());
        while (matcher.find()) {
            Optional<Integer> value = parseValue(matcher.group(1));
            if (value.isPresent() && value.get() == expectedValue) {
                return true;
            }
        }
        return false;
    }
}
