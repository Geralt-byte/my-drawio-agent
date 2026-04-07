package com.xjtu.ai.test.api.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;

/**
 * @author mlei@xjtu
 * @description SpringAiApiTest
 * @create 2026/4/8 02:15
 */
@Slf4j
public class SpringAiApiTest {

    public static void main(String[] args) {
        OpenAiApi openAiApi = OpenAiApi.builder()
                .baseUrl("https://open.bigmodel.cn/api/paas/")
                .apiKey("835eab0c34d840639f04c901c96e2639.PhMbXRyA0DQZq5ij")
                .completionsPath("v4/chat/completions")
                .embeddingsPath("v4/embeddings")
                .build();

        ChatModel chatModel = OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("glm-4.6")
                        .build())
                .build();

        String call = chatModel.call("介绍汾阳王郭子仪");

        log.info("测试结果:{}", call);
    }
}
