package com.xjtu.ai.domain.agent.service.armory.node.workflow;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.SequentialAgent;
import com.xjtu.ai.domain.agent.model.entity.ArmoryCommandEntity;
import com.xjtu.ai.domain.agent.model.valobj.AIAgentRegisterVO;
import com.xjtu.ai.domain.agent.model.valobj.AiAgentConfigTableVO;
import com.xjtu.ai.domain.agent.service.armory.AbstractArmorySupport;
import com.xjtu.ai.domain.agent.service.armory.factory.DefaultArmoryFactory;
import com.xjtu.ai.domain.agent.service.armory.node.RunnerNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mlei@xjtu
 * @description SequentialAgentNode
 * @create 2026/4/11 00:21
 */
@Slf4j
@Service("sequentialAgentNode")
public class SequentialAgentNode extends AbstractArmorySupport {

    @Resource
    private RunnerNode runnerNode;

    @Override
    protected AIAgentRegisterVO doApply(ArmoryCommandEntity armoryCommandEntity, DefaultArmoryFactory.DynamicContext dynamicContext) throws Exception {

        log.info("Ai Agent 装配操作 - SequentialAgentNode");

        List<AiAgentConfigTableVO.Module.AgentWorkflow> agentWorkflows = dynamicContext.getAgentWorkflows();
        AiAgentConfigTableVO.Module.AgentWorkflow agentWorkflow = agentWorkflows.remove(0);

        List<String> subAgentNames = agentWorkflow.getSubAgents();
        List<BaseAgent> agents = dynamicContext.queryAgentList(subAgentNames);

        SequentialAgent sequentialAgent = SequentialAgent.builder()
                .name(agentWorkflow.getName())
                .description(agentWorkflow.getDescription())
                .subAgents(agents)
                .build();

        dynamicContext.getAgentGroup().put(agentWorkflow.getName(), sequentialAgent);

        dynamicContext.setSequentialAgent(sequentialAgent);

        registerBean(agentWorkflow.getName(), SequentialAgent.class, sequentialAgent);

        return router(armoryCommandEntity, dynamicContext);
    }

    @Override
    public StrategyHandler<ArmoryCommandEntity, DefaultArmoryFactory.DynamicContext, AIAgentRegisterVO> get(ArmoryCommandEntity armoryCommandEntity, DefaultArmoryFactory.DynamicContext dynamicContext) throws Exception {
        return runnerNode;
    }
}
