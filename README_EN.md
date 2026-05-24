<div align="center">

English Version | [中文版本](README.md)

</div>

# AI Agent Project

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

---

**[English Version](README_EN.md) | [中文版本](README.md)**