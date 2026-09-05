package cn.bugstack.ai.config;

import cn.bugstack.ai.application.symptom.service.ReceiveSymptomMessageApplicationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SymptomMessageApplicationConfiguration {

    @Bean
    public ReceiveSymptomMessageApplicationService receiveSymptomMessageApplicationService() {
        return new ReceiveSymptomMessageApplicationService();
    }
}
