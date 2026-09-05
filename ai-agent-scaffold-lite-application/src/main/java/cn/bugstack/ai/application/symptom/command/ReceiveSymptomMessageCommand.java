package cn.bugstack.ai.application.symptom.command;

public final class ReceiveSymptomMessageCommand {

    private final String message;

    public ReceiveSymptomMessageCommand(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
