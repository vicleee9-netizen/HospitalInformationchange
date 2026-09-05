package cn.bugstack.ai.domain.symptom.model.valobj;

/**
 * The domain result of receiving a symptom message. Information exposed by this result is Chinese.
 */
public final class SymptomMessageReceptionResult {

    private final boolean accepted;
    private final String information;
    private final SymptomMessage symptomMessage;

    private SymptomMessageReceptionResult(boolean accepted, String information, SymptomMessage symptomMessage) {
        this.accepted = accepted;
        this.information = information;
        this.symptomMessage = symptomMessage;
    }

    public static SymptomMessageReceptionResult accepted(SymptomMessage symptomMessage) {
        return new SymptomMessageReceptionResult(true, "已接收", symptomMessage);
    }

    public static SymptomMessageReceptionResult rejected(String information) {
        return new SymptomMessageReceptionResult(false, information, null);
    }

    public boolean isAccepted() {
        return accepted;
    }

    public String getInformation() {
        return information;
    }

    public SymptomMessage getSymptomMessage() {
        return symptomMessage;
    }
}
