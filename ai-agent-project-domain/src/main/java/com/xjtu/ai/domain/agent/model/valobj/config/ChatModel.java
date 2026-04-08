package com.xjtu.ai.domain.agent.model.valobj.config;

import lombok.Data;

import java.util.List;

/**
 * @author mlei@xjtu
 * @description 聊天模型配置
 * @create 2026/4/8 22:04
 */
@Data
public class ChatModel {

    /**
     * 模型名称，例如 gpt-4o、qwen-plus 等
     */
    private String model;

    /**
     * MCP（Model Context Protocol）工具配置列表，支持SSE和Stdio两种连接方式
     */
    private List<ToolMcp> toolMcpList;
}
