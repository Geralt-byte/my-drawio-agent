package com.xjtu.ai.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mlei@xjtu
 * @description CreateSessionResponseDTO
 * @create 2026/4/13 21:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSessionResponseDTO {
    private String sessionId;
}
