package cn.bugstack.ai.domain.symptom.model.draft;

import java.util.List;

/**
 * An untrusted, unnormalized extraction proposal supplied by a model adapter.
 */
public final class SymptomExtractionDraft {

    private final List<SymptomDraft> symptoms;
    private final TemperatureDraft temperature;

    public SymptomExtractionDraft(List<SymptomDraft> symptoms, TemperatureDraft temperature) {
        this.symptoms = symptoms == null ? List.of() : List.copyOf(symptoms);
        this.temperature = temperature;
    }

    public static SymptomExtractionDraft empty() {
        return new SymptomExtractionDraft(List.of(), null);
    }

    public List<SymptomDraft> getSymptoms() {
        return symptoms;
    }

    public TemperatureDraft getTemperature() {
        return temperature;
    }

    public static final class SymptomDraft {

        private final String name;
        private final String status;
        private final String evidence;
        private final AttributeDraft bodyLocation;
        private final AttributeDraft duration;

        public SymptomDraft(String name, String status, String evidence, AttributeDraft bodyLocation, AttributeDraft duration) {
            this.name = name;
            this.status = status;
            this.evidence = evidence;
            this.bodyLocation = bodyLocation;
            this.duration = duration;
        }

        public String getName() {
            return name;
        }

        public String getStatus() {
            return status;
        }

        public String getEvidence() {
            return evidence;
        }

        public AttributeDraft getBodyLocation() {
            return bodyLocation;
        }

        public AttributeDraft getDuration() {
            return duration;
        }
    }

    public static final class AttributeDraft {

        private final String value;
        private final String evidence;

        public AttributeDraft(String value, String evidence) {
            this.value = value;
            this.evidence = evidence;
        }

        public String getValue() {
            return value;
        }

        public String getEvidence() {
            return evidence;
        }
    }

    public static final class TemperatureDraft {

        private final String celsius;
        private final String evidence;

        public TemperatureDraft(String celsius, String evidence) {
            this.celsius = celsius;
            this.evidence = evidence;
        }

        public String getCelsius() {
            return celsius;
        }

        public String getEvidence() {
            return evidence;
        }
    }
}
