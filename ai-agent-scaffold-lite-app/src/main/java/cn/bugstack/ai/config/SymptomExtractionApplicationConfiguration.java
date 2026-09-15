package cn.bugstack.ai.config;

import cn.bugstack.ai.application.symptom.port.SymptomExtractionModelPort;
import cn.bugstack.ai.application.symptom.service.ExtractSymptomInformationApplicationService;
import cn.bugstack.ai.infrastructure.adapter.port.StubSymptomExtractionModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SymptomExtractionApplicationConfiguration {

    @Bean
    public SymptomExtractionModelPort symptomExtractionModelPort() {
        return new StubSymptomExtractionModel();
    }

    @Bean
    public ExtractSymptomInformationApplicationService extractSymptomInformationApplicationService(
            SymptomExtractionModelPort symptomExtractionModelPort
    ) {
        return new ExtractSymptomInformationApplicationService(symptomExtractionModelPort);
    }
}
