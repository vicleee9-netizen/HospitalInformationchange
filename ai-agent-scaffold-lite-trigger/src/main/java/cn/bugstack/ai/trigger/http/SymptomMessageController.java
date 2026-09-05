package cn.bugstack.ai.trigger.http;

import cn.bugstack.ai.api.response.Response;
import cn.bugstack.ai.application.symptom.command.ReceiveSymptomMessageCommand;
import cn.bugstack.ai.application.symptom.result.ReceiveSymptomMessageResult;
import cn.bugstack.ai.application.symptom.service.ReceiveSymptomMessageApplicationService;
import cn.bugstack.ai.trigger.http.dto.ReceiveSymptomMessageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/symptom-messages")
public class SymptomMessageController {

    private final ReceiveSymptomMessageApplicationService receiveSymptomMessageApplicationService;

    public SymptomMessageController(ReceiveSymptomMessageApplicationService receiveSymptomMessageApplicationService) {
        this.receiveSymptomMessageApplicationService = receiveSymptomMessageApplicationService;
    }

    @PostMapping
    public ResponseEntity<Response<Void>> receive(@RequestBody(required = false) ReceiveSymptomMessageRequest request) {
        String message = request == null ? null : request.getMessage();
        ReceiveSymptomMessageResult result = receiveSymptomMessageApplicationService.execute(
                new ReceiveSymptomMessageCommand(message)
        );

        HttpStatus status = result.isAccepted() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        Response<Void> response = Response.<Void>builder()
                .code(String.valueOf(status.value()))
                .info(result.getInformation())
                .data(null)
                .build();
        return ResponseEntity.status(status).body(response);
    }
}
