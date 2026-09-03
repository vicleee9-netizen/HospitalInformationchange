package ${package}.test.domain.business;

import ${package}.domain.agent.model.entity.ChatCommandEntity;
import ${package}.domain.agent.service.IChatService;
import ${package}.domain.business.model.entity.UserInformationEntity;
import ${package}.domain.business.service.UserInteractionService;
import ${package}.types.enums.ResponseCode;
import ${package}.types.exception.AppException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserInteractionServiceTest {

    private IChatService chatService;
    private UserInteractionService userInteractionService;

    @Before
    public void setUp() {
        chatService = mock(IChatService.class);
        userInteractionService = new UserInteractionService(chatService);
    }

    @Test
    public void shouldReceiveTextAndDelegateToAgent() {
        when(chatService.createSession("agent-1", "user-1")).thenReturn("session-1");
        when(chatService.handleMessage(any(ChatCommandEntity.class))).thenReturn(List.of("reply"));

        List<String> result = userInteractionService.handleMessage(
                UserInformationEntity.of(" user-1 "),
                " agent-1 ",
                null,
                "  hello agent  ");

        assertEquals(List.of("reply"), result);
        verify(chatService).createSession("agent-1", "user-1");
        verify(chatService).handleMessage(argThat(command ->
                "agent-1".equals(command.getAgentId())
                        && "user-1".equals(command.getUserId())
                        && "session-1".equals(command.getSessionId())
                        && command.getTexts() != null
                        && command.getTexts().size() == 1
                        && "  hello agent  ".equals(command.getTexts().get(0).getMessage())));
    }

    @Test
    public void shouldRejectBlankMessage() {
        AppException exception = assertThrows(AppException.class, () -> userInteractionService.handleMessage(
                UserInformationEntity.of("user-1"),
                "agent-1",
                "session-1",
                "  "));

        assertEquals(ResponseCode.ILLEGAL_PARAMETER.getCode(), exception.getCode());
    }

    @Test
    public void shouldRejectMissingUser() {
        AppException exception = assertThrows(AppException.class, () -> userInteractionService.handleMessage(
                UserInformationEntity.of(null),
                "agent-1",
                "session-1",
                "hello"));

        assertEquals(ResponseCode.ILLEGAL_PARAMETER.getCode(), exception.getCode());
    }

}
