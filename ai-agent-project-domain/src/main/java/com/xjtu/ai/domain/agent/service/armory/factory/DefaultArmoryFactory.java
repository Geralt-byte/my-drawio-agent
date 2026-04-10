package com.xjtu.ai.domain.agent.service.armory.factory;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.google.adk.agents.BaseAgent;
import com.xjtu.ai.domain.agent.model.entity.ArmoryCommandEntity;
import com.xjtu.ai.domain.agent.model.valobj.AIAgentRegisterVO;
import com.xjtu.ai.domain.agent.model.valobj.AiAgentConfigTableVO;
import com.xjtu.ai.domain.agent.service.armory.node.RootNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mlei@xjtu
 * @description 默认装配工厂
 * @create 2026/4/8 23:54
 */
@Service
public class DefaultArmoryFactory {

    @Resource
    private RootNode rootNode;

    public StrategyHandler<ArmoryCommandEntity, DynamicContext, AIAgentRegisterVO> armoryStrategyHandler() {
        return rootNode;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {
        /*LLM API*/
        private OpenAiApi openAiApi;

        private ChatModel chatModel;

        private Map<String, BaseAgent> agentGroup = new HashMap<>();

        private List<AiAgentConfigTableVO.Module.AgentWorkflow> agentWorkflows = new ArrayList<>();

        private Map<String, Object> dataObjects = new HashMap<>();

        public <T> void setValue(String key, T value) {
            dataObjects.put(key, value);
        }

        public <T> T getValue(String key) {
            return (T) dataObjects.get(key);
        }
    }
}
