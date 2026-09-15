package cn.bugstack.ai.application.symptom.result;

public final class ExtractSymptomInformationResult {

    private final boolean successful;
    private final String information;
    private final SymptomExtractionView extraction;

    private ExtractSymptomInformationResult(boolean successful, String information, SymptomExtractionView extraction) {
        this.successful = successful;
        this.information = information;
        this.extraction = extraction;
    }

    public static ExtractSymptomInformationResult success(SymptomExtractionView extraction) {
        return new ExtractSymptomInformationResult(true, "症状信息抽取成功", extraction);
    }

    public static ExtractSymptomInformationResult rejected(String information) {
        return new ExtractSymptomInformationResult(false, information, null);
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getInformation() {
        return information;
    }

    public SymptomExtractionView getExtraction() {
        return extraction;
    }
}
