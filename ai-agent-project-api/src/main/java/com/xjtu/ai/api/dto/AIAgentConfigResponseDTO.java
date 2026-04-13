package com.xjtu.ai.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mlei@xjtu
 * @description AIAgentConfigResponseDTO
 * @create 2026/4/13 21:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIAgentConfigResponseDTO {
    /**
     * 智能体ID
     */
    private String agentId;
    /**
     * 智能体名称
     */
    private String agentName;
    /**
     * 智能体描述
     */
    private String agentDesc;
}
