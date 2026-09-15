package cn.bugstack.ai.infrastructure.adapter.port;

import cn.bugstack.ai.application.symptom.port.SymptomExtractionModelPort;
import cn.bugstack.ai.domain.symptom.model.draft.SymptomExtractionDraft;
import cn.bugstack.ai.domain.symptom.model.valobj.SymptomMessage;

import java.util.List;

/**
 * A deterministic adapter used to verify the extraction boundary before a real model is connected.
 */
public final class StubSymptomExtractionModel implements SymptomExtractionModelPort {

    @Override
    public SymptomExtractionDraft extract(SymptomMessage symptomMessage) {
        return switch (symptomMessage.getOriginalText()) {
            case "右边肚子疼两天，还有点发烧。", "右边肚子疼两天，还有点发烧" -> abdominalPainAndFeverDraft();
            case "没有发烧，只是肚子疼。", "没有发烧，只是肚子疼" -> noFeverAndAbdominalPainDraft();
            case "可能有点发烧。", "可能有点发烧" -> possibleFeverDraft();
            case "右边肚子疼，体温38.5度。", "右边肚子疼，体温38.5度" -> abdominalPainWithTemperatureDraft();
            default -> SymptomExtractionDraft.empty();
        };
    }

    private SymptomExtractionDraft abdominalPainAndFeverDraft() {
        return new SymptomExtractionDraft(
                List.of(
                        new SymptomExtractionDraft.SymptomDraft(
                                "肚子疼", "有", "肚子疼",
                                new SymptomExtractionDraft.AttributeDraft("右边", "右边"),
                                new SymptomExtractionDraft.AttributeDraft("两天", "两天")
                        ),
                        new SymptomExtractionDraft.SymptomDraft("发烧", "有", "发烧", null, null)
                ),
                null
        );
    }

    private SymptomExtractionDraft noFeverAndAbdominalPainDraft() {
        return new SymptomExtractionDraft(
                List.of(
                        new SymptomExtractionDraft.SymptomDraft("发烧", "没有", "没有发烧", null, null),
                        new SymptomExtractionDraft.SymptomDraft("肚子疼", "有", "肚子疼", null, null)
                ),
                null
        );
    }

    private SymptomExtractionDraft possibleFeverDraft() {
        return new SymptomExtractionDraft(
                List.of(new SymptomExtractionDraft.SymptomDraft("发烧", "可能", "可能有点发烧", null, null)),
                null
        );
    }

    private SymptomExtractionDraft abdominalPainWithTemperatureDraft() {
        return new SymptomExtractionDraft(
                List.of(new SymptomExtractionDraft.SymptomDraft(
                        "肚子疼", "有", "肚子疼",
                        new SymptomExtractionDraft.AttributeDraft("右边", "右边"),
                        null
                )),
                new SymptomExtractionDraft.TemperatureDraft("38.5", "38.5度")
        );
    }
}
