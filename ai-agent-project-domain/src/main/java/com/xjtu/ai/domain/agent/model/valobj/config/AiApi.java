package com.xjtu.ai.domain.agent.model.valobj.config;

import lombok.Data;

/**
 * @author mlei@xjtu
 * @description API连接配置
 * @create 2026/4/8 22:04
 */
@Data
public class AiApi {

    /**
     * API基础地址
     */
    private String baseUrl;

    /**
     * API密钥，用于接口鉴权
     */
    private String apiKey;

    /**
     * 对话补全接口路径，默认值 v1/chat/completions
     */
    private String completionsPath = "v1/chat/completions";

    /**
     * 向量嵌入接口路径，默认值 v1/embeddings
     */
    private String embeddingsPath = "v1/embeddings";
}
