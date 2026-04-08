package com.xjtu.ai.domain.agent.model.valobj;

import com.xjtu.ai.domain.agent.model.valobj.config.AgentInfo;
import com.xjtu.ai.domain.agent.model.valobj.config.Module;
import lombok.Data;

/**
 * @author mlei@xjtu
 * @description 智能体配置表值对象
 * @create 2026/4/8 22:04
 */
@Data
public class AiAgentConfigTableVO {

    /**
     * 应用名称
     */
    private String appName;

    /**
     * Agent基础信息
     */
    private AgentInfo agentInfo;

    /**
     * 模型配置信息
     */
    private Module module;
}
