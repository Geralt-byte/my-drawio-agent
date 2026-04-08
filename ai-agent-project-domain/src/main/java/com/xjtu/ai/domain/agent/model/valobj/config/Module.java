package com.xjtu.ai.domain.agent.model.valobj.config;

import lombok.Data;

import java.util.List;

/**
 * @author mlei@xjtu
 * @description 模型配置信息
 * @create 2026/4/8 22:04
 */
@Data
public class Module {

    /**
     * API连接配置
     */
    private AiApi aiApi;

    /**
     * 模型名称、MCP工具列表、Agent列表及工作流等
     */
    private ChatModel chatModel;

    /**
     * Agent列表
     */
    private List<Agent> agents;

    /**
     * Agent工作流配置列表
     */
    private List<AgentWorkflow> agentWorkflows;
}

