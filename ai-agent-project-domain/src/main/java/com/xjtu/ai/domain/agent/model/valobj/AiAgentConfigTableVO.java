package com.xjtu.ai.domain.agent.model.valobj;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author mlei@xjtu
 * @description 智能体配置表值对象
 * @create 2026/4/8 22:04
 */
@Data
public class AiAgentConfigTableVO {

    /**
     * 应用名称
     */
    private String appName;

    /**
     * Agent基础信息
     */
    private Agent agent;

    /**
     * 模型配置信息
     */
    private Module module;

    @Data
    public static class Agent {
        /**
         * Agent唯一标识
         */
        private String agentId;

        /**
         * Agent名称
         */
        private String agentName;

        /**
         * Agent描述信息
         */
        private String agentDesc;
    }

    @Data
    public static class Module {
        /**
         * API连接配置
         */
        private AiApi aiApi;

        /**
         * 模型名称、MCP工具列表、Agent列表及工作流等
         */
        private ChatModel chatModel;

        /**
         * Agent列表
         */
        private List<Agent> agents;

        /**
         * 运行器配置
         */
        private Runner runner;

        /**
         * Agent工作流配置列表
         */
        private List<AgentWorkflow> agentWorkflows;

        @Data
        public static class AiApi {
            /**
             * API基础地址
             */
            private String baseUrl;

            /**
             * API密钥，用于接口鉴权
             */
            private String apiKey;

            /**
             * 对话补全接口路径，默认值 v1/chat/completions
             */
            private String completionsPath = "v1/chat/completions";

            /**
             * 向量嵌入接口路径，默认值 v1/embeddings
             */
            private String embeddingsPath = "v1/embeddings";
        }

        @Data
        public static class ChatModel {
            /**
             * 模型名称，例如 gpt-4o、qwen-plus 等
             */
            private String model;

            /**
             * MCP（Model Context Protocol）工具配置列表，支持SSE和Stdio两种连接方式
             */
            private List<ToolMcp> toolMcpList;

            @Data
            public static class ToolMcp {
                /**
                 * SSE（Server-Sent Events）方式连接的MCP服务端参数
                 */
                private SSEServerParameters sse;

                /**
                 * Stdio（标准输入输出）方式连接的MCP服务端参数
                 */
                private StdioServerParameters stdio;

                /**
                 * local方式连接的MCP服务端参数
                 */
                private LocalParameters local;

                @Data
                public static class SSEServerParameters {

                    /**
                     * 工具名称
                     */
                    private String name;

                    /**
                     * MCP服务端基础地址
                     */
                    private String baseUri;

                    /**
                     * MCP服务端接口端点路径
                     */
                    private String sseEndpoint;

                    /**
                     * 请求超时时间，单位：毫秒，默认值 3000
                     */
                    private Integer requestTimeout = 3000;
                }

                @Data
                public static class StdioServerParameters {

                    /**
                     * 工具名称
                     */
                    private String name;

                    /**
                     * 请求超时时间，单位：毫秒，默认值 3000
                     */
                    private Integer requestTimeout = 3000;

                    /**
                     * 本地服务进程启动参数
                     */
                    private ServerParameters serverParameters;

                    @Data
                    public static class ServerParameters {

                        /**
                         * 启动命令，例如 node、python 等
                         */
                        private String command;

                        /**
                         * 命令参数列表
                         */
                        private List<String> args;

                        /**
                         * 进程环境变量键值对
                         */
                        private Map<String, String> env;
                    }
                }

                @Data
                public static class LocalParameters{
                    /**
                     * bean配置mcp服务
                     */
                    private String name;
                }
            }
        }

        @Data
        public static class Agent {
            /**
             * 模型名称
             */
            private String name;

            /**
             * 系统指令/提示词，定义行为规则和能力边界
             */
            private String instruction;

            /**
             * 角色描述
             */
            private String description;

            /**
             * 输出结果
             */
            private String outputKey;
        }

        @Data
        public static class AgentWorkflow {
            /**
             * 工作流类型，例如 sequential（顺序）、parallel（并行）等
             */
            private String type;

            /**
             * 工作流名称
             */
            private String name;

            /**
             * 子Agent名称列表，按顺序参与工作流执行
             */
            private List<String> subAgents;

            /**
             * 工作流描述信息
             */
            private String description;

            /**
             * 工作流最大迭代次数，默认值 3
             */
            private Integer maxIterations = 3;
        }

        @Data
        public static class Runner {
            private String agentName;
        }
    }
}
