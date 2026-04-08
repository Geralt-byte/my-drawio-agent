package com.xjtu.ai.domain.agent.model.valobj.config;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author mlei@xjtu
 * @description MCP工具配置
 * @create 2026/4/8 22:04
 */
@Data
public class ToolMcp {

    /**
     * SSE（Server-Sent Events）方式连接的MCP服务端参数
     */
    private SSEServerParameters sse;

    /**
     * Stdio（标准输入输出）方式连接的MCP服务端参数
     */
    private StdioServerParameters stdio;

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
}
