package com.xjtu.ai.domain.agent.model.valobj.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author mlei@xjtu
 * @description AgentTypeEnum
 * @create 2026/4/11 00:08
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AgentTypeEnum {

    loop("循环执行", "loop", "loopAgentNode"),
    Parallel("并行执行", "parallel", "parallelAgentNode"),
    Sequential("串行执行", "sequential", "sequentialAgentNode"),
    ;

    private String name;
    private String type;
    private String node;

    public static AgentTypeEnum fromType(String type) {
        if (type == null) {
            return null;
        }

        for (AgentTypeEnum value : AgentTypeEnum.values()) {
            if (type.equals(value.getType())) {
                return value;
            }
        }

        return null;
    }
}
