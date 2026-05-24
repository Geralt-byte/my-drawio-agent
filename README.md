<div align="center">
  <h1>AI Agent Project</h1>
  <p>基于DDD架构的企业级AI智能体平台 | Enterprise-grade AI Agent Platform with DDD Architecture</p>
  
  <div>
    <button onclick="showLanguage('zh')" style="padding: 8px 16px; margin: 5px; cursor: pointer; background: #007bff; color: white; border: none; border-radius: 4px;">中文</button>
    <button onclick="showLanguage('en')" style="padding: 8px 16px; margin: 5px; cursor: pointer; background: #6c757d; color: white; border: none; border-radius: 4px;">English</button>
  </div>
</div>

<script>
function showLanguage(lang) {
  var zhContent = document.getElementById('zh-content');
  var enContent = document.getElementById('en-content');
  var buttons = document.querySelectorAll('button');
  
  if (lang === 'zh') {
    zhContent.style.display = 'block';
    enContent.style.display = 'none';
    buttons[0].style.background = '#007bff';
    buttons[1].style.background = '#6c757d';
  } else {
    zhContent.style.display = 'none';
    enContent.style.display = 'block';
    buttons[0].style.background = '#6c757d';
    buttons[1].style.background = '#007bff';
  }
}
</script>

<div id="zh-content">

## 项目简介

AI Agent Project 是一个基于领域驱动设计（DDD）架构的企业级AI智能体平台，提供灵活的智能体装配、对话交互和工作流编排能力。该项目整合了多种AI技术栈，包括Google ADK、Spring AI、LangChain4j等，支持单智能体、顺序工作流、并行工作流等多种智能体协作模式。

### 核心特性

- **多智能体协作**：支持单个智能体、顺序工作流、并行工作流、循环工作流等多种协作模式
- **灵活的工具集成**：支持MCP（Model Context Protocol）工具、Skills脚本、本地插件等多种工具类型
- **流式对话**：支持实时流式响应和批量对话两种交互方式
- **多模态支持**：支持文本、文件、图像等多种消息类型
- **动态装配**：基于责任链模式实现智能体动态装配和注册
- **企业级架构**：采用DDD分层架构，代码结构清晰，易于维护和扩展

## 技术架构

### 整体架构

项目采用经典的DDD四层架构，结合Spring Boot微服务框架：

```
├── ai-agent-project-api          # API接口层 - 对外服务接口定义
├── ai-agent-project-app          # 应用启动层 - Spring Boot应用入口和配置
├── ai-agent-project-domain       # 领域层 - 核心业务逻辑和领域模型
├── ai-agent-project-infrastructure # 基础设施层 - 数据访问和外部服务集成
├── ai-agent-project-trigger      # 触发器层 - HTTP接口和事件处理
└── ai-agent-project-types        # 类型层 - 通用类型和异常定义
```

### 技术栈

- **核心框架**: Spring Boot 3.4.3
- **AI框架**: Google ADK 1.0.0, Spring AI 1.1.0-M3, LangChain4j 1.4.0
- **设计模式**: BugStack Wrench Design Framework 3.0.0
- **数据库**: MySQL 8.0+ (可选), MyBatis 3.0.4
- **工具库**: Lombok, FastJSON 2.0.28, Guava 32.1.3
- **构建工具**: Maven 3.x
- **JDK版本**: Java 17

### DDD领域建模

#### 领域模型结构

项目严格按照DDD领域驱动设计原则进行建模：

**1. 领域实体**
- `ChatCommandEntity`: 对话命令实体，封装用户消息和处理上下文
- `ArmoryCommandEntity`: 装配命令实体，封装智能体配置信息

**2. 值对象**
- `AiAgentConfigTableVO`: 智能体配置表值对象，包含完整的智能体配置信息
- `AIAgentRegisterVO`: 智能体注册值对象，封装已装配的智能体实例
- `AgentTypeEnum`: 智能体类型枚举
- `ToolSkillsTypeEnum`: 工具技能类型枚举

**3. 领域服务**
- `IChatService`: 对话领域服务接口
- `ChatService`: 对话领域服务实现
- `IArmoryService`: 装配领域服务接口  
- `ArmoryService`: 装配领域服务实现

**4. 聚合根**
- 智能体配置聚合：包含Agent基础信息、模型配置、工作流配置等

**5. 工厂模式**
- `DefaultArmoryFactory`: 智能体装配工厂，负责创建和管理智能体实例

#### 领域边界

项目定义了清晰的领域边界：

**Agent领域**：
- 负责智能体的生命周期管理
- 处理对话交互逻辑
- 管理工具和技能集成
- 编排工作流执行

**Business领域**：
- 预留的业务领域扩展点
- 可根据具体业务需求扩展

### 设计模式应用

**1. 责任链模式**
- 用于智能体装配流程，通过AbstractArmorySupport实现
- 装配节点链：RootNode → AIApiNode → ChatModelNode → AgentNode → AgentWorkflowNode → RunnerNode

**2. 工厂模式**
- DefaultArmoryFactory负责智能体实例的创建和管理
- 支持动态注册和获取智能体实例

**3. 策略模式**
- 通过StrategyHandler实现不同类型智能体的差异化处理
- 支持顺序、并行、循环等不同工作流策略

**4. 建造者模式**
- 智能体配置采用Builder模式构建
- 提供流畅的API接口

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+ (可选)
- Docker (可选，用于容器化部署)

### 本地开发

1. **克隆项目**
```bash
git clone https://github.com/your-org/ai-agent-project.git
cd ai-agent-project
```

2. **配置数据库** (可选)
```sql
-- 创建数据库
CREATE DATABASE xfg_frame_archetype DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 导入初始化脚本
source docs/dev-ops/mysql/sql/xfg-frame-archetype.sql;
```

3. **修改配置文件**
编辑 `ai-agent-project-app/src/main/resources/application-dev.yml`:
```yaml
server:
  port: 8091

spring:
  datasource:
    username: root
    password: your_password
    url: jdbc:mysql://127.0.0.1:3306/xfg_frame_archetype?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&serverTimezone=UTC&useSSL=true
```

4. **配置智能体**
编辑 `ai-agent-project-app/src/main/resources/agent/` 目录下的配置文件：
- `test-agent.yml`: 测试智能体配置
- `single-agent.yml`: 单个智能体配置
- `parallel-agent.yml`: 并行工作流智能体配置

5. **启动应用**
```bash
mvn clean install
cd ai-agent-project-app
mvn spring-boot:run
```

### Docker部署

1. **构建镜像**
```bash
mvn clean package
docker build -t ai-agent-project:1.0 .
```

2. **使用Docker Compose启动**
```bash
cd docs/dev-ops
docker-compose -f docker-compose-app.yml up -d
```

3. **使用部署脚本**
```bash
cd docs/dev-ops/app
chmod +x start.sh
./start.sh
```

## API接口文档

### 基础信息

- **Base URL**: `http://localhost:8091/api/v1/`
- **Content-Type**: `application/json`

### 接口列表

#### 1. 查询智能体配置列表

**接口**: `GET /api/v1/query_ai_agent_config_list`

**描述**: 获取所有可用的智能体配置信息

**响应示例**:
```json
{
  "code": "0000",
  "info": "成功",
  "data": [
    {
      "agentId": "100001",
      "agentName": "测试智能体01",
      "agentDesc": "一个测试的智能体"
    }
  ]
}
```

**Curl示例**:
```bash
curl -X GET "http://localhost:8091/api/v1/query_ai_agent_config_list"
```

#### 2. 创建会话

**接口**: `POST /api/v1/create_session`

**描述**: 为指定用户和智能体创建新的对话会话

**请求参数**:
```json
{
  "agentId": "100001",
  "userId": "user001"
}
```

**响应示例**:
```json
{
  "code": "0000",
  "info": "成功",
  "data": {
    "sessionId": "session_abc123"
  }
}
```

**Curl示例**:
```bash
curl -X POST "http://localhost:8091/api/v1/create_session" \
  -H "Content-Type: application/json" \
  -d '{"agentId":"100001","userId":"user001"}'
```

#### 3. 智能体对话

**接口**: `POST /api/v1/chat`

**描述**: 与指定智能体进行对话，支持自动创建会话

**请求参数**:
```json
{
  "agentId": "100001",
  "userId": "user001",
  "sessionId": "session_abc123",
  "message": "你好"
}
```

**响应示例**:
```json
{
  "code": "0000",
  "info": "成功",
  "data": {
    "content": "你好！我是AI助手，有什么可以帮助您的吗？"
  }
}
```

**Curl示例**:
```bash
curl -X POST "http://localhost:8091/api/v1/chat" \
  -H "Content-Type: application/json" \
  -d '{"agentId":"100001","userId":"user001","sessionId":"session_abc123","message":"你好"}'
```

#### 4. 智能体流式对话

**接口**: `POST /api/v1/chat_stream`

**描述**: 与指定智能体进行流式对话，实时返回AI生成的响应内容

**请求参数**: 同对话接口

**响应**: Server-Sent Events (SSE) 流

**Curl示例**:
```bash
curl -X POST "http://localhost:8091/api/v1/chat_stream" \
  -H "Content-Type: application/json" \
  -d '{"agentId":"100001","userId":"user001","message":"介绍一下人工智能"}'
```

## 智能体配置

### 配置文件结构

智能体配置采用YAML格式，支持多智能体配置：

```yaml
ai:
  agent:
    config:
      tables:
        smartAgent:              # 智能体标识
          app-name: smartAgent   # 应用名称
          agent:                 # 智能体基础信息
            agent-id: 100001
            agent-name: 智能助手
            agent-desc: 一个通用的AI智能助手
          module:                # 模块配置
            ai-api:              # AI API配置
              base-url: https://open.bigmodel.cn/api/paas/
              api-key: your-api-key
              completions-path: v4/chat/completions
              embeddings-path: v4/embeddings
            chat-model:          # 聊天模型配置
              model: glm-4.5-air
              tool-mcp-list:     # MCP工具列表
                - sse:           # SSE方式连接
                    name: baidu-search
                    base-uri: http://appbuilder.baidu.com/v2/ai_search/mcp/
                    sse-endpoint: sse?api_key=your-key
                    request-timeout: 50000
                - local:         # 本地Bean方式
                    name: myToolCallbackProvider
              tool-skills-list:  # Skills工具列表
                - type: resource # resource:工程下，directory:用户配置
                  path: agent/skills
            agents:              # 智能体列表
              - name: assistant
                description: AI助手
                instruction: |
                  你是一个专业的AI助手，能够回答各种问题。
                output-key: response
            agent-workflows:     # 工作流配置
              - type: sequential # 工作流类型：sequential/parallel/loop
                name: workflow1
                description: 顺序工作流
                sub-agents:
                  - agent1
                  - agent2
                max-iterations: 3
            runner:              # 运行器配置
              agent-name: workflow1
              pluginNameList:
                - myLogPlugin
```

### 工作流类型

**1. 顺序工作流**
```yaml
- type: sequential
  name: CodePipelineAgent
  description: 代码编写管道
  sub-agents:
    - CodeWriterAgent    # 先执行代码编写
    - CodeReviewerAgent  # 再执行代码审查  
    - CodeRefactorerAgent # 最后执行代码重构
```

**2. 并行工作流**
```yaml
- type: parallel
  name: ParallelWebResearchAgent
  description: 并行研究
  sub-agents:
    - RenewableEnergyResearcher  # 并行执行
    - EVResearcher
    - CarbonCaptureResearcher
```

**3. 循环工作流**
```yaml
- type: loop
  name: LoopAnalysisAgent
  description: 循环分析
  sub-agents:
    - analyzer
  max-iterations: 5  # 最大迭代次数
```

### 工具集成

**1. MCP工具集成**
支持三种连接方式：

- **SSE方式**: 通过Server-Sent Events连接远程MCP服务
- **Stdio方式**: 通过标准输入输出连接本地MCP进程
- **Local方式**: 直接引用Spring容器中的Bean

**2. Skills工具**
支持Python脚本和Shell脚本工具：
- 放置在`agent/skills/`目录下
- 每个技能目录包含`SKILL.md`描述文件和`scripts/`脚本目录

**3. 插件系统**
支持自定义插件扩展：
- 实现特定插件接口
- 在配置中引用插件名称

## 项目结构详解

### API模块 (`ai-agent-project-api`)

**职责**: 定义对外服务接口和数据传输对象

**主要内容**:
- `IAgentService`: 智能体服务接口
- `ChatRequestDTO`: 对话请求DTO
- `ChatResponseDTO`: 对话响应DTO
- `CreateSessionRequestDTO`: 创建会话请求DTO
- `CreateSessionResponseDTO`: 创建会话响应DTO
- `AIAgentConfigResponseDTO`: 智能体配置响应DTO
- `Response`: 通用响应对象

**依赖关系**: 只依赖基础库，无业务逻辑依赖

### APP模块 (`ai-agent-project-app`)

**职责**: 应用启动入口和配置管理

**主要内容**:
- `Application`: Spring Boot启动类
- `AiAgentAutoConfig`: 智能体自动配置
- `ThreadPoolConfig`: 线程池配置
- `GuavaConfig`: Guava缓存配置
- 配置文件: `application.yml`, `application-dev.yml`等
- 智能体配置: `agent/*.yml`

**依赖关系**: 依赖trigger、infrastructure模块

### Domain模块 (`ai-agent-project-domain`)

**职责**: 核心业务逻辑实现，领域模型和领域服务

**主要内容**:

**领域模型** (`model/`):
- `entity/`: 领域实体
  - `ChatCommandEntity`: 对话命令实体
  - `ArmoryCommandEntity`: 装配命令实体
- `valobj/`: 值对象
  - `AiAgentConfigTableVO`: 智能体配置值对象
  - `AIAgentRegisterVO`: 智能体注册值对象
  - `enums/`: 枚举类型
  - `properties/`: 配置属性

**领域服务** (`service/`):
- `IChatService`: 对话服务接口
- `ChatService`: 对话服务实现
- `IArmoryService`: 装配服务接口
- `ArmoryService`: 装配服务实现

**智能体装配** (`service/armory/`):
- `AbstractArmorySupport`: 装配支持基类
- `factory/DefaultArmoryFactory`: 装配工厂
- `node/`: 装配节点
  - `RootNode`: 根节点
  - `AIApiNode`: API配置节点
  - `ChatModelNode`: 模型配置节点
  - `AgentNode`: 智能体节点
  - `AgentWorkflowNode`: 工作流节点
  - `RunnerNode`: 运行器节点
  - `workflow/`: 工作流节点实现
- `matter/`: 装配素材
  - `mcp/`: MCP客户端
  - `skills/`: Skills工具
  - `plugin/`: 插件

**依赖关系**: 依赖types模块，是整个项目的核心

### Infrastructure模块 (`ai-agent-project-infrastructure`)

**职责**: 基础设施实现，数据访问和外部服务集成

**主要内容**:
- `dao/`: 数据访问对象
- `adapter/`: 适配器模式实现
- `gateway/`: 网关服务
- `redis/`: Redis缓存服务

**依赖关系**: 依赖domain模块

### Trigger模块 (`ai-agent-project-trigger`)

**职责**: 触发器实现，处理外部请求

**主要内容**:
- `http/`: HTTP接口控制器
  - `AgentServiceController`: 智能体服务控制器
- `job/`: 定时任务
- `listener/`: 事件监听器

**依赖关系**: 依赖api、domain、types模块

### Types模块 (`ai-agent-project-types`)

**职责**: 通用类型定义和工具类

**主要内容**:
- `enums/ResponseCode`: 响应码枚举
- `exception/AppException`: 应用异常
- `common/Constants`: 常量定义

**依赖关系**: 无其他模块依赖，是最基础的模块

## 部署运维

### 环境配置

项目支持多环境配置，通过Spring Profile切换：

**开发环境** (`application-dev.yml`):
```yaml
server:
  port: 8091
spring:
  profiles:
    active: dev
```

**测试环境** (`application-test.yml`):
```yaml
server:
  port: 8092
spring:
  profiles:
    active: test
```

**生产环境** (`application-prod.yml`):
```yaml
server:
  port: 8093
spring:
  profiles:
    active: prod
```

### 打包部署

**Maven打包**:
```bash
# 清理并打包
mvn clean package

# 跳过测试打包
mvn clean package -DskipTests

# 指定环境打包
mvn clean package -Pprod
```

**启动应用**:
```bash
# 直接启动JAR
java -jar ai-agent-project-app/target/ai-agent-project-app.jar

# 指定环境启动
java -jar -Dspring.profiles.active=prod ai-agent-project-app/target/ai-agent-project-app.jar

# 指定JVM参数启动
java -Xms2G -Xmx2G -jar ai-agent-project-app/target/ai-agent-project-app.jar
```

### Docker部署

**构建Docker镜像**:
```bash
# 在项目根目录创建Dockerfile
cat > Dockerfile << 'EOF'
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY ai-agent-project-app/target/ai-agent-project-app.jar app.jar
EXPOSE 8091
ENTRYPOINT ["java", "-jar", "app.jar"]
EOF

# 构建镜像
docker build -t ai-agent-project:1.0 .
```

**使用Docker Compose**:
```bash
# 启动所有服务
docker-compose -f docs/dev-ops/docker-compose-app.yml up -d

# 查看日志
docker-compose -f docs/dev-ops/docker-compose-app.yml logs -f

# 停止服务
docker-compose -f docs/dev-ops/docker-compose-app.yml down
```

### 生产环境配置

**JVM参数优化**:
```bash
java -Xms6G -Xmx6G \
     -XX:MaxPermSize=256M \
     -Xss256K \
     -XX:+DisableExplicitGC \
     -XX:+UseG1GC \
     -XX:LargePageSizeInBytes=128m \
     -XX:+UseFastAccessorMethods \
     -XX:+HeapDumpOnOutOfMemoryError \
     -XX:HeapDumpPath=/export/Logs/ai-agent-project-boot \
     -Xloggc:/export/Logs/ai-agent-project-boot/gc.log \
     -XX:+PrintGCDetails \
     -XX:+PrintGCDateStamps \
     -jar ai-agent-project-app.jar
```

**日志配置**:
- 日志文件位置: `/export/Logs/ai-agent-project-boot/`
- 日志配置文件: `logback-spring.xml`
- 支持按日期和大小滚动

### 监控和维护

**健康检查**:
```bash
# 检查应用状态
curl http://localhost:8091/actuator/health

# 检查智能体列表
curl http://localhost:8091/api/v1/query_ai_agent_config_list
```

**日志查看**:
```bash
# 查看应用日志
tail -f /export/Logs/ai-agent-project-boot/app.log

# 查看GC日志
tail -f /export/Logs/ai-agent-project-boot/gc.log
```

## 测试

### 单元测试

```bash
# 运行所有测试
mvn test

# 运行指定测试类
mvn test -Dtest=ChatServiceTest

# 运行指定测试方法
mvn test -Dtest=ChatServiceTest#testHandleMessage
```

### 集成测试

项目提供了多个集成测试示例：

**顺序工作流测试** (`SequentialAgentTest`):
- 演示代码编写、审查、重构的顺序工作流
- 展示多智能体协作模式

**并行工作流测试** (`ParallelAgentTest`):
- 演示并行研究工作流
- 展示智能体并行执行能力

**循环工作流测试** (`LoopAgentTest`):
- 演示循环分析工作流
- 展示迭代优化能力

### API测试

使用提供的curl脚本测试API:

```bash
# 测试智能体查询
curl -X GET "http://localhost:8091/api/v1/query_ai_agent_config_list"

# 测试创建会话
curl -X POST "http://localhost:8091/api/v1/create_session" \
  -H "Content-Type: application/json" \
  -d '{"agentId":"100001","userId":"test_user"}'

# 测试对话
curl -X POST "http://localhost:8091/api/v1/chat" \
  -H "Content-Type: application/json" \
  -d '{"agentId":"100001","userId":"test_user","message":"你好"}'
```

## 扩展开发

### 添加新的智能体

1. **创建智能体配置文件**:
```yaml
# 在resources/agent/目录下创建新配置
ai:
  agent:
    config:
      tables:
        myAgent:
          app-name: myAgent
          agent:
            agent-id: 100004
            agent-name: 我的智能体
            agent-desc: 自定义智能体描述
          module:
            # ... 配置详情
```

2. **在应用配置中引入**:
```yaml
spring:
  config:
    import:
      - classpath:agent/my-agent.yml
```

### 开发自定义工具

**1. MCP工具开发**:
实现MCP服务端接口，支持SSE、Stdio或Local连接方式。

**2. Skills工具开发**:
在`agent/skills/`目录下创建技能目录:
```
agent/skills/my-skill/
├── SKILL.md          # 技能描述
└── scripts/          # 脚本文件
    └── my-script.py
```

**3. 插件开发**:
实现插件接口并注册到Spring容器:
```java
@Component("myPlugin")
public class MyPlugin implements Plugin {
    // 插件实现
}
```

### 扩展工作流节点

1. **创建新的工作流节点**:
```java
@Service
public class CustomWorkflowNode extends AbstractArmorySupport {
    @Override
    protected AIAgentRegisterVO doApply(ArmoryCommandEntity request, 
                                        DefaultArmoryFactory.DynamicContext context) {
        // 自定义逻辑
        return router(request, context);
    }
}
```

2. **在配置文件中引用**:
```yaml
agent-workflows:
  - type: custom
    name: customWorkflow
    sub-agents:
      - agent1
```

## 常见问题

### Q1: 如何切换不同的AI模型？

A: 在智能体配置文件中修改`model`字段：
```yaml
chat-model:
  model: glm-4.6  # 修改为目标模型
```

### Q2: 如何添加新的MCP工具？

A: 在`tool-mcp-list`中添加工具配置：
```yaml
tool-mcp-list:
  - sse:
      name: my-tool
      base-uri: http://my-tool.com/mcp/
      sse-endpoint: sse?api_key=your-key
```

### Q3: 如何调试智能体工作流？

A: 
1. 查看应用日志中的装配信息
2. 使用测试类验证单个智能体
3. 通过API接口逐步测试工作流

### Q4: 如何处理并发请求？

A: 
1. 应用内置线程池配置
2. 每个用户会话独立管理
3. 使用流式接口减少内存占用

### Q5: 如何优化性能？

A:
1. 调整JVM参数和内存配置
2. 使用流式接口而非批量接口
3. 合理设置工具超时时间
4. 启用缓存机制

## 贡献指南

欢迎贡献代码、报告问题或提出改进建议！

1. Fork项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启Pull Request

## 许可证

本项目采用 Apache License 2.0 许可证 - 详见 LICENSE 文件

## 联系方式

- **作者**: xiaofuge (mlei@xjtu)
- **组织**: fuzhengwei
- **GitHub**: https://github.com/fuzhengwei

## 致谢

感谢以下开源项目：

- Google ADK
- Spring AI
- LangChain4j
- BugStack Wrench Design Framework

## 更新日志

### v1.0.0 (2026-05-25)
- 初始版本发布
- 支持单智能体、顺序工作流、并行工作流
- 集成Google ADK、Spring AI、LangChain4j
- 实现完整的DDD架构设计
- 支持MCP工具和Skills工具集成
- 提供HTTP API和流式接口
- 支持多环境配置和Docker部署

</div>

<div id="en-content" style="display: none;">

## Project Overview

AI Agent Project is an enterprise-grade AI agent platform based on Domain-Driven Design (DDD) architecture, providing flexible agent assembly, conversational interaction, and workflow orchestration capabilities. The project integrates multiple AI technology stacks, including Google ADK, Spring AI, LangChain4j, etc., supporting various agent collaboration modes such as single agent, sequential workflow, and parallel workflow.

### Core Features

- **Multi-Agent Collaboration**: Supports single agent, sequential workflow, parallel workflow, loop workflow, and other collaboration modes
- **Flexible Tool Integration**: Supports MCP (Model Context Protocol) tools, Skills scripts, local plugins, and other tool types
- **Streaming Conversation**: Supports both real-time streaming response and batch conversation modes
- **Multi-modal Support**: Supports text, files, images, and other message types
- **Dynamic Assembly**: Implements dynamic agent assembly and registration based on chain of responsibility pattern
- **Enterprise Architecture**: Adopts DDD layered architecture with clear code structure, easy to maintain and extend

## Technical Architecture

### Overall Architecture

The project adopts classic DDD four-layer architecture combined with Spring Boot microservice framework:

```
├── ai-agent-project-api          # API Interface Layer - External service interface definitions
├── ai-agent-project-app          # Application Layer - Spring Boot application entry and configuration
├── ai-agent-project-domain       # Domain Layer - Core business logic and domain models
├── ai-agent-project-infrastructure # Infrastructure Layer - Data access and external service integration
├── ai-agent-project-trigger      # Trigger Layer - HTTP interfaces and event handling
└── ai-agent-project-types        # Types Layer - Common types and exception definitions
```

### Technology Stack

- **Core Framework**: Spring Boot 3.4.3
- **AI Framework**: Google ADK 1.0.0, Spring AI 1.1.0-M3, LangChain4j 1.4.0
- **Design Pattern**: BugStack Wrench Design Framework 3.0.0
- **Database**: MySQL 8.0+ (optional), MyBatis 3.0.4
- **Utility Libraries**: Lombok, FastJSON 2.0.28, Guava 32.1.3
- **Build Tool**: Maven 3.x
- **JDK Version**: Java 17

### DDD Domain Modeling

#### Domain Model Structure

The project follows DDD domain-driven design principles strictly:

**1. Domain Entities**
- `ChatCommandEntity`: Conversation command entity, encapsulating user messages and processing context
- `ArmoryCommandEntity`: Assembly command entity, encapsulating agent configuration information

**2. Value Objects**
- `AiAgentConfigTableVO`: Agent configuration table value object, containing complete agent configuration information
- `AIAgentRegisterVO`: Agent registration value object, encapsulating assembled agent instances
- `AgentTypeEnum`: Agent type enumeration
- `ToolSkillsTypeEnum`: Tool skill type enumeration

**3. Domain Services**
- `IChatService`: Conversation domain service interface
- `ChatService`: Conversation domain service implementation
- `IArmoryService`: Assembly domain service interface  
- `ArmoryService`: Assembly domain service implementation

**4. Aggregate Root**
- Agent configuration aggregate: Includes agent basic information, model configuration, workflow configuration, etc.

**5. Factory Pattern**
- `DefaultArmoryFactory`: Agent assembly factory, responsible for creating and managing agent instances

#### Domain Boundaries

The project defines clear domain boundaries:

**Agent Domain**:
- Responsible for agent lifecycle management
- Handles conversation interaction logic
- Manages tool and skill integration
- Orchestrates workflow execution

**Business Domain**:
- Reserved business domain extension points
- Can be extended according to specific business requirements

### Design Pattern Application

**1. Chain of Responsibility Pattern**
- Used for agent assembly process, implemented through AbstractArmorySupport
- Assembly node chain: RootNode → AIApiNode → ChatModelNode → AgentNode → AgentWorkflowNode → RunnerNode

**2. Factory Pattern**
- DefaultArmoryFactory is responsible for creating and managing agent instances
- Supports dynamic registration and retrieval of agent instances

**3. Strategy Pattern**
- Implements differentiated processing for different types of agents through StrategyHandler
- Supports different workflow strategies such as sequential, parallel, and loop

**4. Builder Pattern**
- Agent configuration uses Builder pattern for construction
- Provides fluent API interfaces

## Quick Start

### Prerequisites

- JDK 17+
- Maven 3.6+
- MySQL 8.0+ (optional)
- Docker (optional, for containerized deployment)

### Local Development

1. **Clone the Project**
```bash
git clone https://github.com/your-org/ai-agent-project.git
cd ai-agent-project
```

2. **Configure Database** (optional)
```sql
-- Create database
CREATE DATABASE xfg_frame_archetype DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Import initialization script
source docs/dev-ops/mysql/sql/xfg-frame-archetype.sql;
```

3. **Modify Configuration File**
Edit `ai-agent-project-app/src/main/resources/application-dev.yml`:
```yaml
server:
  port: 8091

spring:
  datasource:
    username: root
    password: your_password
    url: jdbc:mysql://127.0.0.1:3306/xfg_frame_archetype?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&serverTimezone=UTC&useSSL=true
```

4. **Configure Agents**
Edit configuration files in `ai-agent-project-app/src/main/resources/agent/` directory:
- `test-agent.yml`: Test agent configuration
- `single-agent.yml`: Single agent configuration
- `parallel-agent.yml`: Parallel workflow agent configuration

5. **Start Application**
```bash
mvn clean install
cd ai-agent-project-app
mvn spring-boot:run
```

### Docker Deployment

1. **Build Image**
```bash
mvn clean package
docker build -t ai-agent-project:1.0 .
```

2. **Start with Docker Compose**
```bash
cd docs/dev-ops
docker-compose -f docker-compose-app.yml up -d
```

3. **Use Deployment Script**
```bash
cd docs/dev-ops/app
chmod +x start.sh
./start.sh
```

## API Documentation

### Basic Information

- **Base URL**: `http://localhost:8091/api/v1/`
- **Content-Type**: `application/json`

### API Endpoints

#### 1. Query Agent Configuration List

**Endpoint**: `GET /api/v1/query_ai_agent_config_list`

**Description**: Get all available agent configuration information

**Response Example**:
```json
{
  "code": "0000",
  "info": "Success",
  "data": [
    {
      "agentId": "100001",
      "agentName": "Test Agent 01",
      "agentDesc": "A test agent"
    }
  ]
}
```

**Curl Example**:
```bash
curl -X GET "http://localhost:8091/api/v1/query_ai_agent_config_list"
```

#### 2. Create Session

**Endpoint**: `POST /api/v1/create_session`

**Description**: Create a new conversation session for specified user and agent

**Request Parameters**:
```json
{
  "agentId": "100001",
  "userId": "user001"
}
```

**Response Example**:
```json
{
  "code": "0000",
  "info": "Success",
  "data": {
    "sessionId": "session_abc123"
  }
}
```

**Curl Example**:
```bash
curl -X POST "http://localhost:8091/api/v1/create_session" \
  -H "Content-Type: application/json" \
  -d '{"agentId":"100001","userId":"user001"}'
```

#### 3. Agent Conversation

**Endpoint**: `POST /api/v1/chat`

**Description**: Have conversation with specified agent, supports automatic session creation

**Request Parameters**:
```json
{
  "agentId": "100001",
  "userId": "user001",
  "sessionId": "session_abc123",
  "message": "Hello"
}
```

**Response Example**:
```json
{
  "code": "0000",
  "info": "Success",
  "data": {
    "content": "Hello! I am an AI assistant, how can I help you?"
  }
}
```

**Curl Example**:
```bash
curl -X POST "http://localhost:8091/api/v1/chat" \
  -H "Content-Type: application/json" \
  -d '{"agentId":"100001","userId":"user001","sessionId":"session_abc123","message":"Hello"}'
```

#### 4. Agent Streaming Conversation

**Endpoint**: `POST /api/v1/chat_stream`

**Description**: Have streaming conversation with specified agent, returning AI-generated response content in real-time

**Request Parameters**: Same as conversation endpoint

**Response**: Server-Sent Events (SSE) stream

**Curl Example**:
```bash
curl -X POST "http://localhost:8091/api/v1/chat_stream" \
  -H "Content-Type: application/json" \
  -d '{"agentId":"100001","userId":"user001","message":"Introduce artificial intelligence"}'
```

## Agent Configuration

### Configuration File Structure

Agent configuration uses YAML format, supporting multi-agent configuration:

```yaml
ai:
  agent:
    config:
      tables:
        smartAgent:              # Agent identifier
          app-name: smartAgent   # Application name
          agent:                 # Agent basic information
            agent-id: 100001
            agent-name: Smart Assistant
            agent-desc: A general-purpose AI assistant
          module:                # Module configuration
            ai-api:              # AI API configuration
              base-url: https://open.bigmodel.cn/api/paas/
              api-key: your-api-key
              completions-path: v4/chat/completions
              embeddings-path: v4/embeddings
            chat-model:          # Chat model configuration
              model: glm-4.5-air
              tool-mcp-list:     # MCP tool list
                - sse:           # SSE connection
                    name: baidu-search
                    base-uri: http://appbuilder.baidu.com/v2/ai_search/mcp/
                    sse-endpoint: sse?api_key=your-key
                    request-timeout: 50000
                - local:         # Local Bean method
                    name: myToolCallbackProvider
              tool-skills-list:  # Skills tool list
                - type: resource # resource: in project, directory: user configured
                  path: agent/skills
            agents:              # Agent list
              - name: assistant
                description: AI assistant
                instruction: |
                  You are a professional AI assistant capable of answering various questions.
                output-key: response
            agent-workflows:     # Workflow configuration
              - type: sequential # Workflow type: sequential/parallel/loop
                name: workflow1
                description: Sequential workflow
                sub-agents:
                  - agent1
                  - agent2
                max-iterations: 3
            runner:              # Runner configuration
              agent-name: workflow1
              pluginNameList:
                - myLogPlugin
```

### Workflow Types

**1. Sequential Workflow**
```yaml
- type: sequential
  name: CodePipelineAgent
  description: Code writing pipeline
  sub-agents:
    - CodeWriterAgent    # Execute code writing first
    - CodeReviewerAgent  # Then execute code review  
    - CodeRefactorerAgent # Finally execute code refactoring
```

**2. Parallel Workflow**
```yaml
- type: parallel
  name: ParallelWebResearchAgent
  description: Parallel research
  sub-agents:
    - RenewableEnergyResearcher  # Execute in parallel
    - EVResearcher
    - CarbonCaptureResearcher
```

**3. Loop Workflow**
```yaml
- type: loop
  name: LoopAnalysisAgent
  description: Loop analysis
  sub-agents:
    - analyzer
  max-iterations: 5  # Maximum iteration count
```

### Tool Integration

**1. MCP Tool Integration**
Supports three connection methods:

- **SSE Method**: Connect to remote MCP service via Server-Sent Events
- **Stdio Method**: Connect to local MCP process via standard input/output
- **Local Method**: Directly reference Bean in Spring container

**2. Skills Tools**
Support Python script and Shell script tools:
- Place in `agent/skills/` directory
- Each skill directory contains `SKILL.md` description file and `scripts/` script directory

**3. Plugin System**
Supports custom plugin extensions:
- Implement specific plugin interface
- Reference plugin name in configuration

## Project Structure Details

### API Module (`ai-agent-project-api`)

**Responsibility**: Define external service interfaces and data transfer objects

**Main Content**:
- `IAgentService`: Agent service interface
- `ChatRequestDTO`: Conversation request DTO
- `ChatResponseDTO`: Conversation response DTO
- `CreateSessionRequestDTO`: Create session request DTO
- `CreateSessionResponseDTO`: Create session response DTO
- `AIAgentConfigResponseDTO`: Agent configuration response DTO
- `Response`: Generic response object

**Dependencies**: Only depends on basic libraries, no business logic dependencies

### APP Module (`ai-agent-project-app`)

**Responsibility**: Application entry point and configuration management

**Main Content**:
- `Application`: Spring Boot startup class
- `AiAgentAutoConfig`: Agent auto-configuration
- `ThreadPoolConfig`: Thread pool configuration
- `GuavaConfig`: Guava cache configuration
- Configuration files: `application.yml`, `application-dev.yml`, etc.
- Agent configuration: `agent/*.yml`

**Dependencies**: Depends on trigger, infrastructure modules

### Domain Module (`ai-agent-project-domain`)

**Responsibility**: Core business logic implementation, domain models and domain services

**Main Content**:

**Domain Models** (`model/`):
- `entity/`: Domain entities
  - `ChatCommandEntity`: Conversation command entity
  - `ArmoryCommandEntity`: Assembly command entity
- `valobj/`: Value objects
  - `AiAgentConfigTableVO`: Agent configuration value object
  - `AIAgentRegisterVO`: Agent registration value object
  - `enums/`: Enumeration types
  - `properties/`: Configuration properties

**Domain Services** (`service/`):
- `IChatService`: Conversation service interface
- `ChatService`: Conversation service implementation
- `IArmoryService`: Assembly service interface
- `ArmoryService`: Assembly service implementation

**Agent Assembly** (`service/armory/`):
- `AbstractArmorySupport`: Assembly support base class
- `factory/DefaultArmoryFactory`: Assembly factory
- `node/`: Assembly nodes
  - `RootNode`: Root node
  - `AIApiNode`: API configuration node
  - `ChatModelNode`: Model configuration node
  - `AgentNode`: Agent node
  - `AgentWorkflowNode`: Workflow node
  - `RunnerNode`: Runner node
  - `workflow/`: Workflow node implementations
- `matter/`: Assembly materials
  - `mcp/`: MCP clients
  - `skills/`: Skills tools
  - `plugin/`: Plugins

**Dependencies**: Depends on types module, is the core of the entire project

### Infrastructure Module (`ai-agent-project-infrastructure`)

**Responsibility**: Infrastructure implementation, data access and external service integration

**Main Content**:
- `dao/`: Data access objects
- `adapter/`: Adapter pattern implementations
- `gateway/`: Gateway services
- `redis/`: Redis cache services

**Dependencies**: Depends on domain module

### Trigger Module (`ai-agent-project-trigger`)

**Responsibility**: Trigger implementation, handling external requests

**Main Content**:
- `http/`: HTTP interface controllers
  - `AgentServiceController`: Agent service controller
- `job/`: Scheduled tasks
- `listener/`: Event listeners

**Dependencies**: Depends on api, domain, types modules

### Types Module (`ai-agent-project-types`)

**Responsibility**: Common type definitions and utility classes

**Main Content**:
- `enums/ResponseCode`: Response code enumeration
- `exception/AppException`: Application exception
- `common/Constants`: Constant definitions

**Dependencies**: No dependencies on other modules, is the most basic module

## Deployment and Operations

### Environment Configuration

The project supports multi-environment configuration, switched through Spring Profile:

**Development Environment** (`application-dev.yml`):
```yaml
server:
  port: 8091
spring:
  profiles:
    active: dev
```

**Test Environment** (`application-test.yml`):
```yaml
server:
  port: 8092
spring:
  profiles:
    active: test
```

**Production Environment** (`application-prod.yml`):
```yaml
server:
  port: 8093
spring:
  profiles:
    active: prod
```

### Packaging and Deployment

**Maven Packaging**:
```bash
# Clean and package
mvn clean package

# Package skipping tests
mvn clean package -DskipTests

# Package with specified environment
mvn clean package -Pprod
```

**Start Application**:
```bash
# Start JAR directly
java -jar ai-agent-project-app/target/ai-agent-project-app.jar

# Start with specified environment
java -jar -Dspring.profiles.active=prod ai-agent-project-app/target/ai-agent-project-app.jar

# Start with JVM parameters
java -Xms2G -Xmx2G -jar ai-agent-project-app/target/ai-agent-project-app.jar
```

### Docker Deployment

**Build Docker Image**:
```bash
# Create Dockerfile in project root
cat > Dockerfile << 'EOF'
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY ai-agent-project-app/target/ai-agent-project-app.jar app.jar
EXPOSE 8091
ENTRYPOINT ["java", "-jar", "app.jar"]
EOF

# Build image
docker build -t ai-agent-project:1.0 .
```

**Use Docker Compose**:
```bash
# Start all services
docker-compose -f docs/dev-ops/docker-compose-app.yml up -d

# View logs
docker-compose -f docs/dev-ops/docker-compose-app.yml logs -f

# Stop services
docker-compose -f docs/dev-ops/docker-compose-app.yml down
```

### Production Environment Configuration

**JVM Parameter Optimization**:
```bash
java -Xms6G -Xmx6G \
     -XX:MaxPermSize=256M \
     -Xss256K \
     -XX:+DisableExplicitGC \
     -XX:+UseG1GC \
     -XX:LargePageSizeInBytes=128m \
     -XX:+UseFastAccessorMethods \
     -XX:+HeapDumpOnOutOfMemoryError \
     -XX:HeapDumpPath=/export/Logs/ai-agent-project-boot \
     -Xloggc:/export/Logs/ai-agent-project-boot/gc.log \
     -XX:+PrintGCDetails \
     -XX:+PrintGCDateStamps \
     -jar ai-agent-project-app.jar
```

**Logging Configuration**:
- Log file location: `/export/Logs/ai-agent-project-boot/`
- Log configuration file: `logback-spring.xml`
- Supports rolling by date and size

### Monitoring and Maintenance

**Health Check**:
```bash
# Check application status
curl http://localhost:8091/actuator/health

# Check agent list
curl http://localhost:8091/api/v1/query_ai_agent_config_list
```

**Log Viewing**:
```bash
# View application logs
tail -f /export/Logs/ai-agent-project-boot/app.log

# View GC logs
tail -f /export/Logs/ai-agent-project-boot/gc.log
```

## Testing

### Unit Testing

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=ChatServiceTest

# Run specific test method
mvn test -Dtest=ChatServiceTest#testHandleMessage
```

### Integration Testing

The project provides multiple integration test examples:

**Sequential Workflow Test** (`SequentialAgentTest`):
- Demonstrates sequential workflow of code writing, review, and refactoring
- Shows multi-agent collaboration mode

**Parallel Workflow Test** (`ParallelAgentTest`):
- Demonstrates parallel research workflow
- Shows agent parallel execution capability

**Loop Workflow Test** (`LoopAgentTest`):
- Demonstrates loop analysis workflow
- Shows iterative optimization capability

### API Testing

Test APIs using provided curl scripts:

```bash
# Test agent query
curl -X GET "http://localhost:8091/api/v1/query_ai_agent_config_list"

# Test session creation
curl -X POST "http://localhost:8091/api/v1/create_session" \
  -H "Content-Type: application/json" \
  -d '{"agentId":"100001","userId":"test_user"}'

# Test conversation
curl -X POST "http://localhost:8091/api/v1/chat" \
  -H "Content-Type: application/json" \
  -d '{"agentId":"100001","userId":"test_user","message":"Hello"}'
```

## Extension Development

### Adding New Agents

1. **Create Agent Configuration File**:
```yaml
# Create new configuration in resources/agent/ directory
ai:
  agent:
    config:
      tables:
        myAgent:
          app-name: myAgent
          agent:
            agent-id: 100004
            agent-name: My Agent
            agent-desc: Custom agent description
          module:
            # ... configuration details
```

2. **Import in Application Configuration**:
```yaml
spring:
  config:
    import:
      - classpath:agent/my-agent.yml
```

### Developing Custom Tools

**1. MCP Tool Development**:
Implement MCP server interface, supporting SSE, Stdio, or Local connection methods.

**2. Skills Tool Development**:
Create skill directory in `agent/skills/`:
```
agent/skills/my-skill/
├── SKILL.md          # Skill description
└── scripts/          # Script files
    └── my-script.py
```

**3. Plugin Development**:
Implement plugin interface and register to Spring container:
```java
@Component("myPlugin")
public class MyPlugin implements Plugin {
    // Plugin implementation
}
```

### Extending Workflow Nodes

1. **Create New Workflow Node**:
```java
@Service
public class CustomWorkflowNode extends AbstractArmorySupport {
    @Override
    protected AIAgentRegisterVO doApply(ArmoryCommandEntity request, 
                                        DefaultArmoryFactory.DynamicContext context) {
        // Custom logic
        return router(request, context);
    }
}
```

2. **Reference in Configuration File**:
```yaml
agent-workflows:
  - type: custom
    name: customWorkflow
    sub-agents:
      - agent1
```

## FAQ

### Q1: How to switch between different AI models?

A: Modify the `model` field in the agent configuration file:
```yaml
chat-model:
  model: glm-4.6  # Change to target model
```

### Q2: How to add new MCP tools?

A: Add tool configuration in `tool-mcp-list`:
```yaml
tool-mcp-list:
  - sse:
      name: my-tool
      base-uri: http://my-tool.com/mcp/
      sse-endpoint: sse?api_key=your-key
```

### Q3: How to debug agent workflows?

A: 
1. Check assembly information in application logs
2. Use test classes to verify individual agents
3. Test workflows step by step through API interfaces

### Q4: How to handle concurrent requests?

A: 
1. Application has built-in thread pool configuration
2. Each user session is managed independently
3. Use streaming interfaces to reduce memory usage

### Q5: How to optimize performance?

A:
1. Adjust JVM parameters and memory configuration
2. Use streaming interfaces instead of batch interfaces
3. Set reasonable tool timeout values
4. Enable caching mechanisms

## Contributing

Contributions are welcome! Feel free to submit code, report issues, or suggest improvements!

1. Fork the project
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details

## Contact

- **Author**: xiaofuge (mlei@xjtu)
- **Organization**: fuzhengwei
- **GitHub**: https://github.com/fuzhengwei

## Acknowledgments

Thanks to the following open source projects:

- Google ADK
- Spring AI
- LangChain4j
- BugStack Wrench Design Framework

## Changelog

### v1.0.0 (2026-05-25)
- Initial release
- Supports single agent, sequential workflow, parallel workflow
- Integrates Google ADK, Spring AI, LangChain4j
- Implements complete DDD architecture design
- Supports MCP tools and Skills tools integration
- Provides HTTP API and streaming interfaces
- Supports multi-environment configuration and Docker deployment

</div>