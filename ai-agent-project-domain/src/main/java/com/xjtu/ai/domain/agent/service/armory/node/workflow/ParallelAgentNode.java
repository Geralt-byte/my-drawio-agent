package com.xjtu.ai.domain.agent.service.armory.node.workflow;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.xjtu.ai.domain.agent.model.entity.ArmoryCommandEntity;
import com.xjtu.ai.domain.agent.model.valobj.AIAgentRegisterVO;
import com.xjtu.ai.domain.agent.model.valobj.AiAgentConfigTableVO;
import com.xjtu.ai.domain.agent.model.valobj.enums.AgentTypeEnum;
import com.xjtu.ai.domain.agent.service.armory.AbstractArmorySupport;
import com.xjtu.ai.domain.agent.service.armory.factory.DefaultArmoryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mlei@xjtu
 * @description LoopAgentNode
 * @create 2026/4/11 00:05
 */
@Slf4j
@Service("parallelAgentNode")
public class ParallelAgentNode extends AbstractArmorySupport {
    @Override
    protected AIAgentRegisterVO doApply(ArmoryCommandEntity armoryCommandEntity, DefaultArmoryFactory.DynamicContext dynamicContext) throws Exception {
        return null;
    }

    @Override
    public StrategyHandler<ArmoryCommandEntity, DefaultArmoryFactory.DynamicContext, AIAgentRegisterVO> get(ArmoryCommandEntity armoryCommandEntity, DefaultArmoryFactory.DynamicContext dynamicContext) throws Exception {
        List<AiAgentConfigTableVO.Module.AgentWorkflow> agentWorkflows = dynamicContext.getAgentWorkflows();

        if (agentWorkflows == null || agentWorkflows.isEmpty()) {
            return defaultStrategyHandler;
        }

        AiAgentConfigTableVO.Module.AgentWorkflow agentWorkflow = agentWorkflows.get(0);
        String type = agentWorkflow.getType();
        AgentTypeEnum agentTypeEnum = AgentTypeEnum.fromType(type);
        if (agentTypeEnum == null) {
            throw new RuntimeException("agentWorkflow type is error!");
        }

        String node = agentTypeEnum.getNode();

        return switch (node) {
            case "parallel" -> getBean("parallel");
            case "sequential" -> getBean("sequential");
            default -> defaultStrategyHandler;
        };
    }
}
