package com.xjtu.ai.test.api.tool.skills;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.springaicommunity.agent.tools.SkillsTool;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.core.io.ClassPathResource;

import java.time.Duration;
import java.util.ArrayList;

/**
 * @author mlei@xjtu
 * @description SpringAiToolTest
 * @create 2026/4/8 00:59
 */
@Slf4j
public class SpringAiToolTest {

    public static void main(String[] args) {
        OpenAiApi openAiApi = OpenAiApi.builder()
                .baseUrl("https://open.bigmodel.cn/api/paas/")
                .apiKey("835eab0c34d840639f04c901c96e2639.PhMbXRyA0DQZq5ij")
                .completionsPath("v4/chat/completions")
                .embeddingsPath("v4/embeddings")
                .build();

        ToolCallback toolCallback = SkillsTool.builder().addSkillsResource(new ClassPathResource("agent/skills")).build();
        ArrayList<ToolCallback> callbackArrayList = new ArrayList<>();
        callbackArrayList.add(toolCallback);

        ChatModel chatModel = OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("glm-4.6")
                        .toolCallbacks(callbackArrayList)
                        .build())
                .build();

        String call = chatModel.call("基于 skill 解答，电脑性能优化");

        log.info("测试结果:{}", call);
    }
}
