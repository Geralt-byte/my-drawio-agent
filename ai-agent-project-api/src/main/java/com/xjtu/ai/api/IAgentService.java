package com.xjtu.ai.api;

import com.xjtu.ai.api.dto.*;
import com.xjtu.ai.api.response.Response;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.List;

/**
 * @author mlei@xjtu
 * @description 智能体服务接口
 * @create 2026/4/13 21:44
 */
public interface IAgentService {

    
    Response<List<AIAgentConfigResponseDTO>> queryAIAgentConfigList();

    Response<CreateSessionResponseDTO> createSession(CreateSessionRequestDTO request);

    Response<ChatResponseDTO> chat(ChatRequestDTO request);

    ResponseBodyEmitter chatStream(ChatRequestDTO request);
}
