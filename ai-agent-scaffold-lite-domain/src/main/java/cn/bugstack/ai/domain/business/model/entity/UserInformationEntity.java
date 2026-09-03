package cn.bugstack.ai.domain.business.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息实体。
 *
 * <p>第一阶段只使用用户身份上下文，不负责用户持久化，也不保存会话和消息历史。</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInformationEntity {

    /**
     * 业务用户 ID。
     */
    private String userId;

    /**
     * 登录用户名，当前阶段可选。
     */
    private String userName;

    /**
     * 用户展示名称，当前阶段可选。
     */
    private String displayName;

    public static UserInformationEntity of(String userId) {
        return UserInformationEntity.builder()
                .userId(userId == null ? null : userId.trim())
                .build();
    }

    public boolean hasIdentity() {
        return userId != null && !userId.isBlank();
    }

}
