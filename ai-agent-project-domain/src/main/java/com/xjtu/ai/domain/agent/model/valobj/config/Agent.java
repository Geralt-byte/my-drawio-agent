package com.xjtu.ai.domain.agent.model.valobj.config;

import lombok.Data;

/**
 * @author mlei@xjtu
 * @description 模块内的Agent定义
 * @create 2026/4/8 22:04
 */
@Data
public class Agent {

    /**
     * 模型名称
     */
    private String name;

    /**
     * 系统指令/提示词，定义行为规则和能力边界
     */
    private String instruction;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 输出结果
     */
    private String outputKey;
}
