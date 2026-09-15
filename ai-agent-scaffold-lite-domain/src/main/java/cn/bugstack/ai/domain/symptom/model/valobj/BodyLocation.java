package cn.bugstack.ai.domain.symptom.model.valobj;

import java.util.Optional;

public final class BodyLocation {

    private final String value;
    private final Evidence evidence;

    private BodyLocation(String value, Evidence evidence) {
        this.value = value;
        this.evidence = evidence;
    }

    public static Optional<BodyLocation> normalize(String rawLocation, Evidence evidence, SymptomName symptomName) {
        if (rawLocation == null || !"腹痛".equals(symptomName.getValue())) {
            return Optional.empty();
        }

        String normalizedLocation = switch (rawLocation.trim()) {
            case "右边", "右侧", "右侧腹部" -> "右侧腹部";
            case "右下腹" -> "右下腹";
            case "左边", "左侧", "左侧腹部" -> "左侧腹部";
            case "左下腹" -> "左下腹";
            default -> null;
        };

        if (normalizedLocation == null || !isSupportedBy(normalizedLocation, evidence)) {
            return Optional.empty();
        }
        return Optional.of(new BodyLocation(normalizedLocation, evidence));
    }

    public String getValue() {
        return value;
    }

    public Evidence getEvidence() {
        return evidence;
    }

    private static boolean isSupportedBy(String normalizedLocation, Evidence evidence) {
        String evidenceText = evidence.getText();
        return switch (normalizedLocation) {
            case "右侧腹部" -> evidenceText.contains("右边") || evidenceText.contains("右侧") || evidenceText.contains("右侧腹部");
            case "右下腹" -> evidenceText.contains("右下腹");
            case "左侧腹部" -> evidenceText.contains("左边") || evidenceText.contains("左侧") || evidenceText.contains("左侧腹部");
            case "左下腹" -> evidenceText.contains("左下腹");
            default -> false;
        };
    }
}
