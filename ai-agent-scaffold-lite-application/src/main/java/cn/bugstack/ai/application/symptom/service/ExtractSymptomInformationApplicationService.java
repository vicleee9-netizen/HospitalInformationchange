package cn.bugstack.ai.application.symptom.service;

import cn.bugstack.ai.application.symptom.assembler.SymptomExtractionViewAssembler;
import cn.bugstack.ai.application.symptom.command.ExtractSymptomInformationCommand;
import cn.bugstack.ai.application.symptom.port.SymptomExtractionModelPort;
import cn.bugstack.ai.application.symptom.result.ExtractSymptomInformationResult;
import cn.bugstack.ai.domain.symptom.model.draft.SymptomExtractionDraft;
import cn.bugstack.ai.domain.symptom.model.valobj.SymptomExtraction;
import cn.bugstack.ai.domain.symptom.model.valobj.SymptomMessage;
import cn.bugstack.ai.domain.symptom.model.valobj.SymptomMessageReceptionResult;

/**
 * Coordinates a single extraction request while keeping model-provider details outside this layer.
 */
public final class ExtractSymptomInformationApplicationService {

    private final SymptomExtractionModelPort symptomExtractionModelPort;
    private final SymptomExtractionViewAssembler symptomExtractionViewAssembler;

    public ExtractSymptomInformationApplicationService(SymptomExtractionModelPort symptomExtractionModelPort) {
        this.symptomExtractionModelPort = symptomExtractionModelPort;
        this.symptomExtractionViewAssembler = new SymptomExtractionViewAssembler();
    }

    public ExtractSymptomInformationResult execute(ExtractSymptomInformationCommand command) {
        SymptomMessageReceptionResult receptionResult = SymptomMessage.receive(command.getMessage());
        if (!receptionResult.isAccepted()) {
            return ExtractSymptomInformationResult.rejected(receptionResult.getInformation());
        }

        SymptomExtractionDraft draft = symptomExtractionModelPort.extract(receptionResult.getSymptomMessage());
        SymptomExtraction extraction = SymptomExtraction.reconcile(receptionResult.getSymptomMessage(), draft);
        return ExtractSymptomInformationResult.success(symptomExtractionViewAssembler.assemble(extraction));
    }
}
