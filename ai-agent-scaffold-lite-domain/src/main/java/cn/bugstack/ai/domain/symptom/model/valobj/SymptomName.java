package cn.bugstack.ai.domain.symptom.model.valobj;

import java.util.Optional;

public final class SymptomName {

    private final String value;

    private SymptomName(String value) {
        this.value = value;
    }

    public static Optional<SymptomName> normalize(String rawName) {
        if (rawName == null) {
            return Optional.empty();
        }

        return switch (rawName.trim()) {
            case "肚子疼", "肚子痛", "腹痛" -> Optional.of(new SymptomName("腹痛"));
            case "发烧", "发热" -> Optional.of(new SymptomName("发热"));
            default -> Optional.empty();
        };
    }

    public boolean isSupportedBy(Evidence evidence) {
        String evidenceText = evidence.getText();
        return switch (value) {
            case "腹痛" -> evidenceText.contains("肚子疼") || evidenceText.contains("肚子痛") || evidenceText.contains("腹痛");
            case "发热" -> evidenceText.contains("发烧") || evidenceText.contains("发热");
            default -> false;
        };
    }

    public String getValue() {
        return value;
    }
}
