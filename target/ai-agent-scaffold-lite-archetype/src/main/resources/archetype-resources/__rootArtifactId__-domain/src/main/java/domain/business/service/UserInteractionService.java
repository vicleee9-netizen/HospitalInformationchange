package ${package}.domain.business.service;

import ${package}.domain.agent.model.entity.ChatCommandEntity;
import ${package}.domain.agent.service.IChatService;
import ${package}.domain.business.model.entity.UserInformationEntity;
import ${package}.types.enums.ResponseCode;
import ${package}.types.exception.AppException;
import io.reactivex.rxjava3.core.Flowable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户自然语言交互领域服务。
 */
@Service
@RequiredArgsConstructor
public class UserInteractionService implements IUserInteractionService {

    private final IChatService chatService;

    @Override
    public String createSession(UserInformationEntity userInformation, String agentId) {
        String userId = requireUserId(userInformation);
        String normalizedAgentId = requireText(agentId, "agentId");
        return chatService.createSession(normalizedAgentId, userId);
    }

    @Override
    public List<String> handleMessage(UserInformationEntity userInformation,
                                      String agentId,
                                      String sessionId,
                                      String message) {
        String userId = requireUserId(userInformation);
        String normalizedAgentId = requireText(agentId, "agentId");
        String normalizedMessage = requireMessage(message);
        String effectiveSessionId = resolveSessionId(normalizedAgentId, userId, sessionId);

        ChatCommandEntity command = ChatCommandEntity.builder()
                .agentId(normalizedAgentId)
                .userId(userId)
                .sessionId(effectiveSessionId)
                .texts(List.of(new ChatCommandEntity.Content.Text(normalizedMessage)))
                .build();

        // 第一阶段只完成接收和 Agent 调用，消息持久化可在这里扩展。
        return chatService.handleMessage(command);
    }

    @Override
    public Flowable<String> handleMessageStream(UserInformationEntity userInformation,
                                                String agentId,
                                                String sessionId,
                                                String message) {
        String userId = requireUserId(userInformation);
        String normalizedAgentId = requireText(agentId, "agentId");
        String normalizedMessage = requireMessage(message);
        String effectiveSessionId = resolveSessionId(normalizedAgentId, userId, sessionId);

        return chatService.handleMessageStream(normalizedAgentId, userId, effectiveSessionId, normalizedMessage)
                .map(event -> event.stringifyContent());
    }

    private String resolveSessionId(String agentId, String userId, String sessionId) {
        if (sessionId == null || sessionId.isBlank()) {
            return chatService.createSession(agentId, userId);
        }
        return sessionId.trim();
    }

    private String requireUserId(UserInformationEntity userInformation) {
        if (userInformation == null || !userInformation.hasIdentity()) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), "userId 不能为空");
        }
        return userInformation.getUserId().trim();
    }

    private String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), fieldName + " 不能为空");
        }
        return value.trim();
    }

    private String requireMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), "message 不能为空");
        }
        return message;
    }

}
