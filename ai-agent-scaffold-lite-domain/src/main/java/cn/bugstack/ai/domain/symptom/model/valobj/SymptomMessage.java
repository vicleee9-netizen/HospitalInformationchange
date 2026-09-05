package cn.bugstack.ai.domain.symptom.model.valobj;

/**
 * The user's original symptom description. Its value is kept unchanged for later extraction.
 */
public final class SymptomMessage {

    private final String originalText;

    private SymptomMessage(String originalText) {
        this.originalText = originalText;
    }

    public static SymptomMessageReceptionResult receive(String originalText) {
        if (originalText == null || originalText.isBlank()) {
            return SymptomMessageReceptionResult.rejected("症状信息接收请求参数不完整");
        }

        return SymptomMessageReceptionResult.accepted(new SymptomMessage(originalText));
    }

    public String getOriginalText() {
        return originalText;
    }
}
