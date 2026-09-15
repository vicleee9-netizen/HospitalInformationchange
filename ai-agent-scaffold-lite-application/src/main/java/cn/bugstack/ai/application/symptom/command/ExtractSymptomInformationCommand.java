package cn.bugstack.ai.application.symptom.command;

public final class ExtractSymptomInformationCommand {

    private final String message;

    public ExtractSymptomInformationCommand(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
