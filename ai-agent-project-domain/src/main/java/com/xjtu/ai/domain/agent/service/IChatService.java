package com.xjtu.ai.domain.agent.service;

import com.google.adk.events.Event;
import com.xjtu.ai.domain.agent.model.entity.ChatCommandEntity;
import com.xjtu.ai.domain.agent.model.valobj.AiAgentConfigTableVO;
import io.reactivex.rxjava3.core.Flowable;

import java.util.List;

/**
 * @author mlei@xjtu
 * @description 对话功能接口
 * @create 2026/4/8 23:49
 */
public interface IChatService {

    /**
     * 查询智能体配置列表
     * @return 智能体配置列表
     */
    List<AiAgentConfigTableVO.Agent> queryAiAgentConfigList();

/**
     * 创建会话
     * @param agentId 智能体Id
     * @param userId 用户Id
     * @return 会话Id
     */
    String createSession(String agentId, String userId);

    /**
     * 处理消息
     * @param agentId 智能体Id
     * @param userId 用户Id
     * @param message 消息内容
     * @return 处理结果
     */
    List<String> handleMessage(String agentId, String userId, String message);

    /**
     * 处理消息
     * @param agentId 智能体Id
     * @param userId 用户Id
     * @param sessionId 会话Id
     * @param message 消息内容
     * @return 处理结果
     */
    List<String> handleMessage(String agentId, String userId, String sessionId, String message);

    /**
     * 处理消息流
     * @param agentId 智能体Id
     * @param userId 用户Id
     * @param sessionId 会话Id
     * @param message 消息内容
     * @return 处理结果流
     */
    Flowable<Event> handleMessageStream(String agentId, String userId, String sessionId, String message);

    /**
     * 处理消息
     * @param chatCommandEntity 消息实体
     * @return 处理结果
     */
    List<String> handleMessage(ChatCommandEntity chatCommandEntity);
}
