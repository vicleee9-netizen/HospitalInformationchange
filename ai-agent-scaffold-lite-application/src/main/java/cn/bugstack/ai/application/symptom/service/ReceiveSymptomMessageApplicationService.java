package cn.bugstack.ai.application.symptom.service;

import cn.bugstack.ai.application.symptom.command.ReceiveSymptomMessageCommand;
import cn.bugstack.ai.application.symptom.result.ReceiveSymptomMessageResult;
import cn.bugstack.ai.domain.symptom.model.valobj.SymptomMessage;
import cn.bugstack.ai.domain.symptom.model.valobj.SymptomMessageReceptionResult;

/**
 * Orchestrates the use case without knowing HTTP, persistence, or model-provider details.
 */
public final class ReceiveSymptomMessageApplicationService {

    public ReceiveSymptomMessageResult execute(ReceiveSymptomMessageCommand command) {
        SymptomMessageReceptionResult receptionResult = SymptomMessage.receive(command.getMessage());
        return new ReceiveSymptomMessageResult(receptionResult.isAccepted(), receptionResult.getInformation());
    }
}
