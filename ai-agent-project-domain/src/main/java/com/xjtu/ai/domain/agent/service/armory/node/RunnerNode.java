package com.xjtu.ai.domain.agent.service.armory.node;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.SequentialAgent;
import com.google.adk.runner.InMemoryRunner;
import com.xjtu.ai.domain.agent.model.entity.ArmoryCommandEntity;
import com.xjtu.ai.domain.agent.model.valobj.AIAgentRegisterVO;
import com.xjtu.ai.domain.agent.model.valobj.AiAgentConfigTableVO;
import com.xjtu.ai.domain.agent.service.armory.AbstractArmorySupport;
import com.xjtu.ai.domain.agent.service.armory.factory.DefaultArmoryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author mlei@xjtu
 * @description RunnerNode
 * @create 2026/4/12 11:32
 */
@Slf4j
@Service
public class RunnerNode extends AbstractArmorySupport {

    @Override
    protected AIAgentRegisterVO doApply(ArmoryCommandEntity armoryCommandEntity, DefaultArmoryFactory.DynamicContext dynamicContext) throws Exception {

        log.info("Ai Agent 装配操作 - RunnerNode");

        AiAgentConfigTableVO aiAgentConfigTableVO = armoryCommandEntity.getAiAgentConfigTableVO();
        String appName = aiAgentConfigTableVO.getAppName();
        AiAgentConfigTableVO.Agent agent = aiAgentConfigTableVO.getAgent();
        String agentName = agent.getAgentName();
        String agentId = agent.getAgentId();
        String agentDesc = agent.getAgentDesc();

        AiAgentConfigTableVO.Module.Runner runnerConfig = aiAgentConfigTableVO.getModule().getRunner();

        BaseAgent baseAgent = dynamicContext.getAgentGroup().get(runnerConfig.getAgentName());

        InMemoryRunner runner = new InMemoryRunner(baseAgent, appName);

        AIAgentRegisterVO aiAgentRegisterVO = AIAgentRegisterVO.builder()
                .appName(appName)
                .agentId(agentId)
                .agentName(agentName)
                .agentDesc(agentDesc)
                .runner(runner)
                .build();

        registerBean(agentId, AIAgentRegisterVO.class, aiAgentRegisterVO);

        return aiAgentRegisterVO;
    }

    @Override
    public StrategyHandler<ArmoryCommandEntity, DefaultArmoryFactory.DynamicContext, AIAgentRegisterVO> get(ArmoryCommandEntity armoryCommandEntity, DefaultArmoryFactory.DynamicContext dynamicContext) throws Exception {
        return defaultStrategyHandler;
    }
}
