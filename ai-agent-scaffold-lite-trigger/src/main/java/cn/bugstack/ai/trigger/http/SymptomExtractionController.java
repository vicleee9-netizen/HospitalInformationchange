package cn.bugstack.ai.trigger.http;

import cn.bugstack.ai.api.response.Response;
import cn.bugstack.ai.application.symptom.command.ExtractSymptomInformationCommand;
import cn.bugstack.ai.application.symptom.result.ExtractSymptomInformationResult;
import cn.bugstack.ai.application.symptom.result.SymptomExtractionView;
import cn.bugstack.ai.application.symptom.service.ExtractSymptomInformationApplicationService;
import cn.bugstack.ai.trigger.http.dto.ExtractSymptomInformationRequest;
import cn.bugstack.ai.types.enums.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/symptom-extractions")
public class SymptomExtractionController {

    private final ExtractSymptomInformationApplicationService extractSymptomInformationApplicationService;

    public SymptomExtractionController(ExtractSymptomInformationApplicationService extractSymptomInformationApplicationService) {
        this.extractSymptomInformationApplicationService = extractSymptomInformationApplicationService;
    }

    @PostMapping
    public ResponseEntity<Response<SymptomExtractionView>> extract(
            @RequestBody(required = false) ExtractSymptomInformationRequest request
    ) {
        String message = request == null ? null : request.getMessage();
        ExtractSymptomInformationResult result = extractSymptomInformationApplicationService.execute(
                new ExtractSymptomInformationCommand(message)
        );

        if (!result.isSuccessful()) {
            Response<SymptomExtractionView> response = Response.<SymptomExtractionView>builder()
                    .code(ResponseCode.ILLEGAL_PARAMETER.getCode())
                    .info(result.getInformation())
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Response<SymptomExtractionView> response = Response.<SymptomExtractionView>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(result.getInformation())
                .data(result.getExtraction())
                .build();
        return ResponseEntity.ok(response);
    }
}
