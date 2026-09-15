package cn.bugstack.ai.application.symptom.assembler;

import cn.bugstack.ai.application.symptom.result.SymptomExtractionView;
import cn.bugstack.ai.domain.symptom.model.valobj.BodyLocation;
import cn.bugstack.ai.domain.symptom.model.valobj.BodyTemperature;
import cn.bugstack.ai.domain.symptom.model.valobj.SymptomDuration;
import cn.bugstack.ai.domain.symptom.model.valobj.SymptomExtraction;
import cn.bugstack.ai.domain.symptom.model.valobj.SymptomFact;

import java.util.List;

public final class SymptomExtractionViewAssembler {

    public SymptomExtractionView assemble(SymptomExtraction extraction) {
        List<SymptomExtractionView.SymptomFactView> symptoms = extraction.getSymptoms().stream()
                .map(this::toSymptomFactView)
                .toList();
        return new SymptomExtractionView(extraction.getOriginalText(), symptoms, toTemperatureView(extraction.getTemperature()));
    }

    private SymptomExtractionView.SymptomFactView toSymptomFactView(SymptomFact symptomFact) {
        return new SymptomExtractionView.SymptomFactView(
                symptomFact.getName().getValue(),
                symptomFact.getStatus().getDisplayName(),
                symptomFact.getEvidence().getText(),
                toBodyLocationView(symptomFact.getBodyLocation()),
                toDurationView(symptomFact.getDuration())
        );
    }

    private SymptomExtractionView.EvidenceValueView toBodyLocationView(BodyLocation bodyLocation) {
        if (bodyLocation == null) {
            return null;
        }
        return new SymptomExtractionView.EvidenceValueView(bodyLocation.getValue(), bodyLocation.getEvidence().getText());
    }

    private SymptomExtractionView.DurationView toDurationView(SymptomDuration duration) {
        if (duration == null) {
            return null;
        }
        return new SymptomExtractionView.DurationView(duration.getValue(), duration.getUnit(), duration.getEvidence().getText());
    }

    private SymptomExtractionView.TemperatureView toTemperatureView(BodyTemperature temperature) {
        if (temperature == null) {
            return null;
        }
        return new SymptomExtractionView.TemperatureView(
                temperature.getCelsius().stripTrailingZeros().toPlainString(),
                "摄氏度",
                temperature.getEvidence().getText()
        );
    }
}
