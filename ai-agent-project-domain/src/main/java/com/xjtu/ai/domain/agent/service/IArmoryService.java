package com.xjtu.ai.domain.agent.service;

import com.xjtu.ai.domain.agent.model.valobj.AiAgentConfigTableVO;

import java.util.List;

/**
 * @author mlei@xjtu
 * @description 装配功能接口
 * @create 2026/4/8 23:49
 */
public interface IArmoryService {

    void acceptArmoryAgents(List<AiAgentConfigTableVO> tables) throws Exception;
}
