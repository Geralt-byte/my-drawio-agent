package com.xjtu.ai.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mlei@xjtu
 * @description ChatRequestDTO
 * @create 2026/4/13 21:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequestDTO {
    private String agentId;
    private String userId;
    private String sessionId;
    private String message;
}
