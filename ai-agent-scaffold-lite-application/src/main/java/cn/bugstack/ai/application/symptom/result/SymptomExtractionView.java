package cn.bugstack.ai.application.symptom.result;

import java.util.List;

public final class SymptomExtractionView {

    private final String originalText;
    private final List<SymptomFactView> symptoms;
    private final TemperatureView temperature;

    public SymptomExtractionView(String originalText, List<SymptomFactView> symptoms, TemperatureView temperature) {
        this.originalText = originalText;
        this.symptoms = List.copyOf(symptoms);
        this.temperature = temperature;
    }

    public String getOriginalText() {
        return originalText;
    }

    public List<SymptomFactView> getSymptoms() {
        return symptoms;
    }

    public TemperatureView getTemperature() {
        return temperature;
    }

    public static final class SymptomFactView {

        private final String name;
        private final String status;
        private final String evidence;
        private final EvidenceValueView bodyLocation;
        private final DurationView duration;

        public SymptomFactView(String name, String status, String evidence, EvidenceValueView bodyLocation, DurationView duration) {
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

        public EvidenceValueView getBodyLocation() {
            return bodyLocation;
        }

        public DurationView getDuration() {
            return duration;
        }
    }

    public static final class EvidenceValueView {

        private final String value;
        private final String evidence;

        public EvidenceValueView(String value, String evidence) {
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

    public static final class DurationView {

        private final int value;
        private final String unit;
        private final String evidence;

        public DurationView(int value, String unit, String evidence) {
            this.value = value;
            this.unit = unit;
            this.evidence = evidence;
        }

        public int getValue() {
            return value;
        }

        public String getUnit() {
            return unit;
        }

        public String getEvidence() {
            return evidence;
        }
    }

    public static final class TemperatureView {

        private final String value;
        private final String unit;
        private final String evidence;

        public TemperatureView(String value, String unit, String evidence) {
            this.value = value;
            this.unit = unit;
            this.evidence = evidence;
        }

        public String getValue() {
            return value;
        }

        public String getUnit() {
            return unit;
        }

        public String getEvidence() {
            return evidence;
        }
    }
}
