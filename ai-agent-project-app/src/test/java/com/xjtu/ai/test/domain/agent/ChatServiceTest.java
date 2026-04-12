package com.xjtu.ai.test.domain.agent;

import com.alibaba.fastjson.JSON;
import com.xjtu.ai.domain.agent.model.entity.ChatCommandEntity;
import com.xjtu.ai.domain.agent.service.IChatService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MimeTypeUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author mlei@xjtu
 * @description ChatServiceTest
 * @create 2026/4/13 02:03
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ChatServiceTest {

    @Resource
    private IChatService chatService;

    @Value("classpath:file/dog.png")
    private org.springframework.core.io.Resource imageResource;

    @Test
    public void test_handleMessage_01() {
        List<String> message = chatService.handleMessage("100003", "mlei", "你具备哪些能力");
        log.info("测试结果:{}", JSON.toJSONString(message));
    }

    @Test
    public void test_handleMessage_04_withImage() throws IOException {
        String agentId = "100002";
        String userId = "mlei";

        String sessionId = chatService.createSession(agentId, userId);

        ChatCommandEntity chatCommandEntity = new ChatCommandEntity();
        chatCommandEntity.setAgentId(agentId);
        chatCommandEntity.setUserId(userId);
        chatCommandEntity.setSessionId(sessionId);
        chatCommandEntity.setTexts(List.of(new ChatCommandEntity.Content.Text("请识别这个图片,并用一句话描述。")));
        chatCommandEntity.setInlineDatas(List.of(new ChatCommandEntity.Content.InlineData(imageResource.getContentAsByteArray(), MimeTypeUtils.IMAGE_PNG_VALUE)));

        List<String> outputs = chatService.handleMessage(chatCommandEntity);
        log.info("测试结果:{}", JSON.toJSONString(outputs));
    }
}
