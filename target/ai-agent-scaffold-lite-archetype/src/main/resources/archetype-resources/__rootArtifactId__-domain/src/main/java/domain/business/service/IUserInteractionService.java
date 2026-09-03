package ${package}.domain.business.service;

import ${package}.domain.business.model.entity.UserInformationEntity;
import io.reactivex.rxjava3.core.Flowable;

import java.util.List;

/**
 * 用户自然语言交互服务。
 *
 * <p>负责协调用户身份、输入校验和 Agent 调用。第一阶段不做消息持久化。</p>
 */
public interface IUserInteractionService {

    String createSession(UserInformationEntity userInformation, String agentId);

    List<String> handleMessage(UserInformationEntity userInformation,
                               String agentId,
                               String sessionId,
                               String message);

    Flowable<String> handleMessageStream(UserInformationEntity userInformation,
                                         String agentId,
                                         String sessionId,
                                         String message);

}
