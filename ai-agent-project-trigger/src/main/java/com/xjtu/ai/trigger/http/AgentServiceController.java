package com.xjtu.ai.trigger.http;

import com.alibaba.fastjson.JSON;
import com.xjtu.ai.api.IAgentService;
import com.xjtu.ai.api.dto.*;
import com.xjtu.ai.api.response.Response;
import com.xjtu.ai.domain.agent.model.valobj.AiAgentConfigTableVO;
import com.xjtu.ai.domain.agent.service.IChatService;
import com.xjtu.ai.types.enums.ResponseCode;
import com.xjtu.ai.types.exception.AppException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.List;

/**
 * @author mlei@xjtu
 * @description 智能体服务接口
 * @create 2026/4/13 21:46
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "*")
public class AgentServiceController implements IAgentService {

    @Resource
    private IChatService chatService;

    /**
     * 查询智能体配置列表
     * <p>
     * 获取所有可用的智能体配置信息，包括智能体ID、名称和描述
     * </p>
     * <p>curl示例：</p>
     * <pre>
     * curl -X GET "http://localhost:8080/api/v1/query_ai_agent_config_list"
     * </pre>
     *
     * @return 智能体配置列表响应
     */
    @RequestMapping(value = "query_ai_agent_config_list", method = RequestMethod.GET)
    @Override
    public Response<List<AIAgentConfigResponseDTO>> queryAIAgentConfigList() {
        try {
            log.info("查询智能体配置列表");

            List<AiAgentConfigTableVO.Agent> agentConfigList = chatService.queryAiAgentConfigList();
            List<AIAgentConfigResponseDTO> response = agentConfigList.stream().map(agent -> AIAgentConfigResponseDTO.builder()
                    .agentId(agent.getAgentId())
                    .agentName(agent.getAgentName())
                    .agentDesc(agent.getAgentDesc())
                    .build()).toList();
            return Response.<List<AIAgentConfigResponseDTO>>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(response)
                    .build();
        } catch (AppException e) {
            log.error("查询智能体配置列表异常", e);
            return Response.<List<AIAgentConfigResponseDTO>>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("查询智能体配置列表失败", e);
            return Response.<List<AIAgentConfigResponseDTO>>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 创建会话
     * <p>
     * 为指定用户和智能体创建一个新的对话会话
     * </p>
     * <p>curl示例：</p>
     * <pre>
     * curl -X GET "http://localhost:8080/api/v1/create_session" \
     *   -H "Content-Type: application/json" \
     *   -d '{"agentId":"agent001","userId":"user001"}'
     * </pre>
     *
     * @param request 创建会话请求，包含agentId和userId
     * @return 会话创建响应，包含sessionId
     */
    @RequestMapping(value = "create_session", method = RequestMethod.POST)
    @Override
    public Response<CreateSessionResponseDTO> createSession(@RequestBody CreateSessionRequestDTO request) {
        try {
            log.info("创建会话 agentId: {} userId:{}", request.getAgentId(), request.getUserId());
            String sessionId = chatService.createSession(request.getAgentId(), request.getUserId());
            CreateSessionResponseDTO response = new CreateSessionResponseDTO();
            response.setSessionId(sessionId);
            return Response.<CreateSessionResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(response)
                    .build();
        } catch (AppException e) {
            log.error("创建会话异常 agentId: {} userId:{}", request.getAgentId(), request.getUserId(), e);
            return Response.<CreateSessionResponseDTO>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("创建会话失败 agentId: {} userId:{}", request.getAgentId(), request.getUserId(), e);
            return Response.<CreateSessionResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 智能体对话
     * <p>
     * 与指定智能体进行对话，如果sessionId为空则自动创建新会话
     * </p>
     * <p>curl示例：</p>
     * <pre>
     * curl -X POST "http://localhost:8080/api/v1/chat" \
     *   -H "Content-Type: application/json" \
     *   -d '{"agentId":"agent001","userId":"user001","sessionId":"session123","message":"你好"}'
     * </pre>
     *
     * @param request 对话请求，包含agentId、userId、sessionId和message
     * @return 对话响应，包含AI回复内容
     */
    @RequestMapping(value = "chat", method = RequestMethod.POST)
    @Override
    public Response<ChatResponseDTO> chat(@RequestBody ChatRequestDTO request) {
        try {
            log.info("智能体对话 agentId: {} userId:{} sessionId:{}", request.getAgentId(), request.getUserId(), request.getSessionId());
            String sessionId = request.getSessionId();
            if (StringUtils.isBlank(sessionId)) {
                sessionId = chatService.createSession(request.getAgentId(), request.getUserId());
            }

            List<String> messages = chatService.handleMessage(request.getAgentId(), request.getUserId(), sessionId, request.getMessage());

            ChatResponseDTO response = new ChatResponseDTO();

            try {
                // 尝试获取最后一条消息并解析
                String result = messages.stream().reduce((first, second) -> second).orElse("");
                ChatResponseDTO parsed = JSON.parseObject(result, ChatResponseDTO.class);
                if (null != parsed) {
                    response = parsed;
                    // 如果解析后的对象 type 为空，则默认为 user
                    if (null == response.getType()) {
                        response.setType("user");
                    }
                } else {
                    response.setType("user");
                    response.setContent(String.join("\n", messages));
                }
            } catch (Exception e) {
                response.setType("user");
                response.setContent(String.join("\n", messages));
            }

            //response.setContent(String.join("\n", messages));
            return Response.<ChatResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(response)
                    .build();
        } catch (AppException e) {
            log.error("智能体对话异常 agentId: {} userId:{} sessionId:{}", request.getAgentId(), request.getUserId(), request.getSessionId(), e);
            return Response.<ChatResponseDTO>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("智能体对话失败 agentId: {} userId:{} sessionId:{}", request.getAgentId(), request.getUserId(), request.getSessionId(), e);
            return Response.<ChatResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 智能体流式对话
     * <p>
     * 与指定智能体进行流式对话，实时返回AI生成的响应内容
     * </p>
     * <p>curl示例：</p>
     * <pre>
     * curl -X POST "http://localhost:8080/api/v1/chat_stream" \
     *   -H "Content-Type: application/json" \
     *   -d '{"agentId":"agent001","userId":"user001","sessionId":"session123","message":"你好"}'
     * </pre>
     *
     * @param request 对话请求，包含agentId、userId、sessionId和message
     * @return ResponseBodyEmitter 用于流式响应
     */
    @RequestMapping(value = "chat_stream", method = RequestMethod.POST)
    @Override
    public ResponseBodyEmitter chatStream(@RequestBody ChatRequestDTO request) {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter(3 * 60 * 1000L);
        try {
            log.info("智能体流式对话 agentId: {} userId:{} sessionId:{}", request.getAgentId(), request.getUserId(), request.getSessionId());
            chatService.handleMessageStream(request.getAgentId(), request.getUserId(), request.getSessionId(), request.getMessage())
                    .subscribe(event -> {
                        try {
                            emitter.send(event.stringifyContent());
                        } catch (Exception e) {
                            log.error("智能体流式对话发送失败", e);
                            emitter.completeWithError(e);
                        }
                    }, emitter::completeWithError, emitter::complete);
        } catch (Exception e) {
            log.error("智能体流式对话失败 agentId: {} userId:{} sessionId:{}", request.getAgentId(), request.getUserId(), request.getSessionId(), e);
            emitter.completeWithError(e);
        }
        return emitter;
    }
}