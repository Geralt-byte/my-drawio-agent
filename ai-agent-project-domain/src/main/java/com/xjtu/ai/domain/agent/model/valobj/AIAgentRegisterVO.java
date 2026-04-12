package com.xjtu.ai.domain.agent.model.valobj;

import com.google.adk.runner.InMemoryRunner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author mlei@xjtu
 * @description 智能体注册值对象
 * @create 2026/4/9 00:00
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AIAgentRegisterVO {

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 智能体唯一标识
     */
    private String agentId;

    /**
     * 智能体名称
     */
    private String agentName;

    /**
     * 智能体描述信息
     */
    private String agentDesc;

    /**
     * 智能体执行对象
     */
    private InMemoryRunner runner;
}
