package cn.bugstack.ai.domain.symptom.model.valobj;

import java.math.BigDecimal;
import java.util.Optional;

public final class BodyTemperature {

    private static final BigDecimal MINIMUM_CELSIUS = new BigDecimal("34.0");
    private static final BigDecimal MAXIMUM_CELSIUS = new BigDecimal("43.0");

    private final BigDecimal celsius;
    private final Evidence evidence;

    private BodyTemperature(BigDecimal celsius, Evidence evidence) {
        this.celsius = celsius;
        this.evidence = evidence;
    }

    public static Optional<BodyTemperature> fromDraft(String rawCelsius, Evidence evidence) {
        if (rawCelsius == null || !evidence.getText().contains(rawCelsius.trim())) {
            return Optional.empty();
        }

        try {
            BigDecimal celsius = new BigDecimal(rawCelsius.trim());
            if (celsius.compareTo(MINIMUM_CELSIUS) < 0 || celsius.compareTo(MAXIMUM_CELSIUS) > 0) {
                return Optional.empty();
            }
            return Optional.of(new BodyTemperature(celsius, evidence));
        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }

    public BigDecimal getCelsius() {
        return celsius;
    }

    public Evidence getEvidence() {
        return evidence;
    }
}
