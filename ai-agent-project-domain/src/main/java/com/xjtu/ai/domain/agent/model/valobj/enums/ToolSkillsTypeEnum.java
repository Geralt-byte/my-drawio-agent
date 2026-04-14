package com.xjtu.ai.domain.agent.model.valobj.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author mlei@xjtu
 * @description ToolSkillsTypeEnum
 * @create 2026/4/14 20:48
 */
@Getter
@AllArgsConstructor
public enum ToolSkillsTypeEnum {

    directory("directory"),
    resource("resource"),
    ;

    private final String type;
}
