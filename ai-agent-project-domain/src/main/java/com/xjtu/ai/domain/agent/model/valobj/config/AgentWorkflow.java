package com.xjtu.ai.domain.agent.model.valobj.config;

import lombok.Data;

import java.util.List;

/**
 * @author mlei@xjtu
 * @description Agent工作流配置
 * @create 2026/4/8 22:04
 */
@Data
public class AgentWorkflow {

    /**
     * 工作流类型，例如 sequential（顺序）、parallel（并行）等
     */
    private String type;

    /**
     * 工作流名称
     */
    private String name;

    /**
     * 子Agent名称列表，按顺序参与工作流执行
     */
    private List<String> subAgents;

    /**
     * 工作流描述信息
     */
    private String description;

    /**
     * 工作流最大迭代次数，默认值 3
     */
    private Integer maxIterations = 3;
}
