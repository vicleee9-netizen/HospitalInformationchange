package cn.bugstack.ai.domain.symptom.model.valobj;

import java.util.Optional;

/**
 * A field-level quotation that must be traceable to the original message.
 */
public final class Evidence {

    private final String text;

    private Evidence(String text) {
        this.text = text;
    }

    public static Optional<Evidence> from(String originalText, String text) {
        if (originalText == null || text == null || text.isBlank() || !originalText.contains(text)) {
            return Optional.empty();
        }
        return Optional.of(new Evidence(text));
    }

    public String getText() {
        return text;
    }
}
