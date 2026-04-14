package com.xjtu.ai.domain.agent.service.armory.matter.skills;

import com.xjtu.ai.domain.agent.model.valobj.AiAgentConfigTableVO;
import org.springframework.ai.tool.ToolCallback;

/**
 * @author mlei@xjtu
 * @description Skills 构建服务
 * @create 2026/4/14 20:44
 */
public interface ToolSkillsCreateService {

    ToolCallback[] buildToolCallback(AiAgentConfigTableVO.Module.ChatModel.ToolSkills toolSkills) throws Exception;
}
