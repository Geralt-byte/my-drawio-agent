package com.xjtu.ai.domain.agent.service.armory.matter.mcp.client.factory;

import com.xjtu.ai.domain.agent.model.valobj.AiAgentConfigTableVO;
import com.xjtu.ai.domain.agent.service.armory.matter.mcp.client.ToolMcpCreateService;
import com.xjtu.ai.domain.agent.service.armory.matter.mcp.client.impl.LocalToolMcpCreateService;
import com.xjtu.ai.domain.agent.service.armory.matter.mcp.client.impl.SSEToolMcpCreateService;
import com.xjtu.ai.domain.agent.service.armory.matter.mcp.client.impl.StdioToolMcpCreateService;
import com.xjtu.ai.types.enums.ResponseCode;
import com.xjtu.ai.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author mlei@xjtu
 * @description mcp装配工厂
 * @create 2026/4/12 21:01
 */
@Slf4j
@Service
public class DefaultMcpClientFactory {

    @Resource
    private SSEToolMcpCreateService sseToolMcpCreateService;

    @Resource
    private LocalToolMcpCreateService localToolMcpCreateService;

    @Resource
    private StdioToolMcpCreateService stdioToolMcpCreateService;

    public ToolMcpCreateService getToolMcpCreateService(AiAgentConfigTableVO.Module.ChatModel.ToolMcp toolMcp) {
        if (toolMcp.getLocal() != null) {
            return localToolMcpCreateService;
        }
        if (toolMcp.getSse() != null) {
            return sseToolMcpCreateService;
        }
        if (toolMcp.getStdio() != null) {
            return stdioToolMcpCreateService;
        }
        throw new AppException(ResponseCode.NOT_FOUND_METHOD.getCode(), ResponseCode.NOT_FOUND_METHOD.getInfo());
    }
}
