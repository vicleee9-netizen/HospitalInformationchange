package cn.bugstack.ai.application.symptom.port;

import cn.bugstack.ai.domain.symptom.model.draft.SymptomExtractionDraft;
import cn.bugstack.ai.domain.symptom.model.valobj.SymptomMessage;

/**
 * External capability required by the symptom extraction use case.
 */
public interface SymptomExtractionModelPort {

    SymptomExtractionDraft extract(SymptomMessage symptomMessage);
}
