package cn.bugstack.ai.application.symptom.result;

public final class ReceiveSymptomMessageResult {

    private final boolean accepted;
    private final String information;

    public ReceiveSymptomMessageResult(boolean accepted, String information) {
        this.accepted = accepted;
        this.information = information;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public String getInformation() {
        return information;
    }
}
