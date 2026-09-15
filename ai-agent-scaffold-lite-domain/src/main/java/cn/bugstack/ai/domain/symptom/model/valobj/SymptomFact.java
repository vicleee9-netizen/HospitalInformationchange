package cn.bugstack.ai.domain.symptom.model.valobj;

import cn.bugstack.ai.domain.symptom.model.draft.SymptomExtractionDraft;

import java.util.Optional;

public final class SymptomFact {

    private final SymptomName name;
    private final SymptomStatus status;
    private final Evidence evidence;
    private final BodyLocation bodyLocation;
    private final SymptomDuration duration;

    private SymptomFact(SymptomName name, SymptomStatus status, Evidence evidence, BodyLocation bodyLocation, SymptomDuration duration) {
        this.name = name;
        this.status = status;
        this.evidence = evidence;
        this.bodyLocation = bodyLocation;
        this.duration = duration;
    }

    public static Optional<SymptomFact> fromDraft(String originalText, SymptomExtractionDraft.SymptomDraft draft) {
        if (draft == null) {
            return Optional.empty();
        }

        Optional<Evidence> evidence = Evidence.from(originalText, draft.getEvidence());
        Optional<SymptomName> name = SymptomName.normalize(draft.getName());
        Optional<SymptomStatus> status = SymptomStatus.fromDraft(draft.getStatus());
        if (evidence.isEmpty() || name.isEmpty() || status.isEmpty() || status.get() == SymptomStatus.UNKNOWN) {
            return Optional.empty();
        }
        if (!name.get().isSupportedBy(evidence.get()) || !status.get().isSupportedBy(evidence.get())) {
            return Optional.empty();
        }

        BodyLocation bodyLocation = normalizeBodyLocation(originalText, draft.getBodyLocation(), name.get());
        SymptomDuration duration = normalizeDuration(originalText, draft.getDuration());
        return Optional.of(new SymptomFact(name.get(), status.get(), evidence.get(), bodyLocation, duration));
    }

    public SymptomName getName() {
        return name;
    }

    public SymptomStatus getStatus() {
        return status;
    }

    public Evidence getEvidence() {
        return evidence;
    }

    public BodyLocation getBodyLocation() {
        return bodyLocation;
    }

    public SymptomDuration getDuration() {
        return duration;
    }

    private static BodyLocation normalizeBodyLocation(String originalText, SymptomExtractionDraft.AttributeDraft locationDraft, SymptomName symptomName) {
        if (locationDraft == null) {
            return null;
        }
        return Evidence.from(originalText, locationDraft.getEvidence())
                .flatMap(evidence -> BodyLocation.normalize(locationDraft.getValue(), evidence, symptomName))
                .orElse(null);
    }

    private static SymptomDuration normalizeDuration(String originalText, SymptomExtractionDraft.AttributeDraft durationDraft) {
        if (durationDraft == null) {
            return null;
        }
        return Evidence.from(originalText, durationDraft.getEvidence())
                .flatMap(evidence -> SymptomDuration.normalize(durationDraft.getValue(), evidence))
                .orElse(null);
    }
}
