package cn.bugstack.ai.domain.symptom.model.valobj;

import cn.bugstack.ai.domain.symptom.model.draft.SymptomExtractionDraft;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A validated single-turn extraction result. Invalid model proposals are omitted rather than trusted.
 */
public final class SymptomExtraction {

    private final SymptomMessage originalMessage;
    private final List<SymptomFact> symptoms;
    private final BodyTemperature temperature;

    private SymptomExtraction(SymptomMessage originalMessage, List<SymptomFact> symptoms, BodyTemperature temperature) {
        this.originalMessage = originalMessage;
        this.symptoms = List.copyOf(symptoms);
        this.temperature = temperature;
    }

    public static SymptomExtraction reconcile(SymptomMessage originalMessage, SymptomExtractionDraft draft) {
        SymptomExtractionDraft safeDraft = draft == null ? SymptomExtractionDraft.empty() : draft;
        List<SymptomFact> symptoms = new ArrayList<>();
        Set<String> factKeys = new HashSet<>();
        for (SymptomExtractionDraft.SymptomDraft symptomDraft : safeDraft.getSymptoms()) {
            SymptomFact.fromDraft(originalMessage.getOriginalText(), symptomDraft).ifPresent(symptomFact -> {
                String key = symptomFact.getName().getValue() + "|" + symptomFact.getStatus().name() + "|" + symptomFact.getEvidence().getText();
                if (factKeys.add(key)) {
                    symptoms.add(symptomFact);
                }
            });
        }

        BodyTemperature temperature = normalizeTemperature(originalMessage.getOriginalText(), safeDraft.getTemperature());
        return new SymptomExtraction(originalMessage, symptoms, temperature);
    }

    public String getOriginalText() {
        return originalMessage.getOriginalText();
    }

    public List<SymptomFact> getSymptoms() {
        return symptoms;
    }

    public BodyTemperature getTemperature() {
        return temperature;
    }

    private static BodyTemperature normalizeTemperature(String originalText, SymptomExtractionDraft.TemperatureDraft temperatureDraft) {
        if (temperatureDraft == null) {
            return null;
        }
        return Evidence.from(originalText, temperatureDraft.getEvidence())
                .flatMap(evidence -> BodyTemperature.fromDraft(temperatureDraft.getCelsius(), evidence))
                .orElse(null);
    }
}
