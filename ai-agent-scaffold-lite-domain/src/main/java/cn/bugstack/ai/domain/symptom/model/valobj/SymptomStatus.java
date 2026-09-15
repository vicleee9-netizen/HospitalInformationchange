package cn.bugstack.ai.domain.symptom.model.valobj;

import java.util.Optional;

public enum SymptomStatus {

    CONFIRMED_PRESENT("明确存在"),
    CONFIRMED_ABSENT("明确否认"),
    POSSIBLE_PRESENT("可能存在"),
    UNKNOWN("未知");

    private final String displayName;

    SymptomStatus(String displayName) {
        this.displayName = displayName;
    }

    public static Optional<SymptomStatus> fromDraft(String rawStatus) {
        if (rawStatus == null) {
            return Optional.empty();
        }

        return switch (rawStatus.trim()) {
            case "有", "存在", "明确存在" -> Optional.of(CONFIRMED_PRESENT);
            case "没有", "无", "明确否认" -> Optional.of(CONFIRMED_ABSENT);
            case "可能", "可能有", "疑似", "可能存在" -> Optional.of(POSSIBLE_PRESENT);
            case "未知" -> Optional.of(UNKNOWN);
            default -> Optional.empty();
        };
    }

    public boolean isSupportedBy(Evidence evidence) {
        String evidenceText = evidence.getText();
        return switch (this) {
            case CONFIRMED_PRESENT -> !containsNegativeMarker(evidenceText) && !containsUncertainMarker(evidenceText);
            case CONFIRMED_ABSENT -> containsNegativeMarker(evidenceText);
            case POSSIBLE_PRESENT -> containsUncertainMarker(evidenceText);
            case UNKNOWN -> false;
        };
    }

    public String getDisplayName() {
        return displayName;
    }

    private static boolean containsNegativeMarker(String text) {
        return text.contains("没有") || text.contains("没") || text.contains("无") || text.contains("不发烧") || text.contains("不疼");
    }

    private static boolean containsUncertainMarker(String text) {
        return text.contains("可能") || text.contains("好像") || text.contains("似乎") || text.contains("疑似");
    }
}
