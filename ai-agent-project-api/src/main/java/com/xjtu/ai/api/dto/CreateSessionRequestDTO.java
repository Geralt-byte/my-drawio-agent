package com.xjtu.ai.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mlei@xjtu
 * @description CreateSessionRequestDTO
 * @create 2026/4/13 21:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSessionRequestDTO {

    private String agentId;

    private String userId;
}
