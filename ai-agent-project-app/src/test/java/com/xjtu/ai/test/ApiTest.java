package com.xjtu.ai.test;

import com.alibaba.fastjson.JSON;
import com.google.adk.agents.LlmAgent;
import com.google.adk.events.Event;
import com.google.adk.models.springai.SpringAI;
import com.google.adk.runner.InMemoryRunner;
import com.google.adk.sessions.Session;
import com.google.genai.types.Content;
import com.google.genai.types.Part;
import io.reactivex.rxjava3.core.Flowable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import java.io.InputStream;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream resourceAsStream = classLoader.getResourceAsStream("dog.png");
        Resource resource = new ClassPathResource("dog.png", classLoader);
        assert resourceAsStream != null;

        OpenAiApi openAiApi = OpenAiApi.builder()
                .baseUrl("https://open.bigmodel.cn/api/paas/")
                .apiKey("835eab0c34d840639f04c901c96e2639.PhMbXRyA0DQZq5ij")
                .completionsPath("v4/chat/completions")
                .embeddingsPath("v4/embeddings")
                .build();

        ChatModel chatModel = OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("glm-4.6v")
                        .build())
                .build();

//        ChatResponse response = chatModel.call(new Prompt(
//                UserMessage.builder()
//                        .text("请描述这张图片的主要内容，并说明图中物品的可能用途。")
//                        .media(Media.builder()
//                                .mimeType(MimeType.valueOf(MimeTypeUtils.IMAGE_PNG_VALUE))
//                                .data(resource)
//                                .build())
//                        .build(),
//                OpenAiChatOptions.builder()
//                        .model("glm-4.6v")
//                        .build()));

//        System.out.println("测试结果" + JSON.toJSONString(response));


        // agent 测试
        LlmAgent agent = LlmAgent.builder()
                .name("test")
                .description("Photo description agent")
                .model(new SpringAI(chatModel))
                .instruction("""
                        You are a knowledgeable photo description agent
                        who can describe the main content of a photo and the possible uses of the items in the photo.
                        """)
                .build();

        InMemoryRunner runner = new InMemoryRunner(agent);

        Session session = runner
                .sessionService()
                .createSession("test", "mlei")
                .blockingGet();

        Flowable<Event> events = runner.runAsync("mlei", session.id(),
                Content.fromParts(Part.fromText("这是什么图片"),
                        Part.fromBytes(resource.getContentAsByteArray(), MimeTypeUtils.IMAGE_PNG_VALUE)));

        System.out.print("\nAgent > ");
        events.blockingForEach(event -> System.out.println(event.stringifyContent()));
    }
}
