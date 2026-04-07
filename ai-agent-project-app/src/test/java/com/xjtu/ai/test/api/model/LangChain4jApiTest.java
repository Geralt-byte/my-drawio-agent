package com.xjtu.ai.test.api.model;

import dev.langchain4j.model.openai.OpenAiChatModel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mlei@xjtu
 * @description LangChain4jApiTest
 * @create 2026/4/8 02:29
 */
@Slf4j
public class LangChain4jApiTest {
    public static void main(String[] args) {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("https://open.bigmodel.cn/api/paas/v4")
                .apiKey("835eab0c34d840639f04c901c96e2639.PhMbXRyA0DQZq5ij")
                .modelName("glm-4.6")
                .build();

        String answer = model.chat("介绍一下姚苌");
        log.info("测试结果: {}", answer);
    }
}
