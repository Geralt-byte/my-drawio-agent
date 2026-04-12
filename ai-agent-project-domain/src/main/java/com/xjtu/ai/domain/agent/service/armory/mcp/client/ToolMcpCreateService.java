package com.xjtu.ai.domain.agent.service.armory.mcp.client;

import com.xjtu.ai.domain.agent.model.valobj.AiAgentConfigTableVO;
import org.springframework.ai.tool.ToolCallback;

/**
 * @author mlei@xjtu
 * @description MCP 构建服务
 * @create 2026/4/12 21:00
 */
public interface ToolMcpCreateService {

    ToolCallback[] buildToolCallback(AiAgentConfigTableVO.Module.ChatModel.ToolMcp toolMcp) throws Exception;
}
