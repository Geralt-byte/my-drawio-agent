package com.xjtu.ai.domain.agent.model.valobj.config;

import lombok.Data;

/**
 * @author mlei@xjtu
 * @description Agent基础信息
 * @create 2026/4/8 22:04
 */
@Data
public class AgentInfo {

    /**
     * Agent唯一标识
     */
    private String agentId;

    /**
     * Agent名称
     */
    private String agentName;

    /**
     * Agent描述信息
     */
    private String agentDesc;
}
