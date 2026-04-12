package com.xjtu.ai.domain.agent.model.entity;

import com.google.genai.types.Content;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mlei@xjtu
 * @description 对话命令，实体对象
 * @create 2026/4/13 01:05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatCommandEntity {

    /**
     * 智能体Id
     */
    private String agentId;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 会话Id
     */
    private String sessionId;

    /**
     * 文本消息
     */
    private List<Content.Text> texts;

    /**
     * 文件消息
     */
    private List<Content.File> files;

    /**
     * 内联数据消息（如图像、音频等二进制数据）
     */
    private List<Content.InlineData> inlineDatas;


    @Data
    public static class Content {

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Text {

            private String message;
        }

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class File {
            private String fileUri;
            private String mimeType;
        }

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class InlineData {
            private byte[] bytes;
            private String mimeType;
        }
    }

    public ChatCommandEntity buildSessionCommand(String agentId, String userId) {
        ChatCommandEntity chatCommandEntity = new ChatCommandEntity();
        chatCommandEntity.setAgentId(agentId);
        chatCommandEntity.setUserId(userId);
        return chatCommandEntity;
    }

    public ChatCommandEntity buildSessionCommand(String agentId, String userId, String message) {
        ChatCommandEntity chatCommandEntity = new ChatCommandEntity();
        chatCommandEntity.setAgentId(agentId);
        chatCommandEntity.setUserId(userId);

        List<Content.Text> texts = new ArrayList<>();
        texts.add(new Content.Text(message));
        chatCommandEntity.setTexts(texts);

        return chatCommandEntity;
    }
}
