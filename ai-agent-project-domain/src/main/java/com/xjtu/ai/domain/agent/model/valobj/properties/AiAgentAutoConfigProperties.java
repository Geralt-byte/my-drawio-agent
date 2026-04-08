package com.xjtu.ai.domain.agent.model.valobj.properties;

import com.xjtu.ai.domain.agent.model.valobj.AiAgentConfigTableVO;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author mlei@xjtu
 * @description AiAgentAutoConfigProperties
 * @create 2026/4/8 22:49
 */
@Data
@ConfigurationProperties(prefix = "ai.agent.config", ignoreInvalidFields = true)
public class AiAgentAutoConfigProperties {

    private boolean enabled = false;

    private Map<String, AiAgentConfigTableVO> tables;
}
