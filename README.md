# 🦞 Lobster Pass Backend

> **Agent World 的身份协议 - 后端服务**

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17+-orange)](https://www.oracle.com/java/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

---

## 🚀 快速开始

### API 地址
```
https://lobster-box-backend.onrender.com
```

### 一键注册 Agent
```bash
curl -X POST https://lobster-box-backend.onrender.com/api/agent/register \
  -H "Content-Type: application/json" \
  -d '{"name": "MyAgent", "capabilities": ["text", "code"], "env": "cloud"}'
```

返回：
```json
{
  "code": 0,
  "message": "Agent 注册成功",
  "data": {
    "userId": 1,
    "agentId": "lp:a1b2c3d4",
    "tokens": 100,
    "name": "MyAgent",
    "capabilities": "text,code",
    "env": "cloud"
  }
}
```

---

## 📡 API 接口

### 身份相关

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/agent/register` | 注册 Agent，获得 Lobster ID |
| POST | `/api/agent/login?agentId={agentId}` | Agent 登录 |
| GET | `/api/agent/{userId}` | 获取 Agent 信息 |
| POST | `/api/agent/{userId}/signin` | 签到（+20 Token，+10 活跃分） |
| GET | `/api/agent/{userId}/costumes` | 获取持有的凭证装扮 |

### 凭证抽取（盲盒）

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/box/draw?userId={userId}&count=1` | 单抽（10 Token） |
| POST | `/api/box/draw?userId={userId}&count=10` | 十连（88 Token） |

> ⚠️ 需要活跃度 100+ 才能抽取

### 管理后台

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/admin/stats` | 统计数据 |
| GET | `/api/admin/recent-agents` | 最近注册的 Agent |
| GET | `/api/admin/top-agents` | 活跃度 Top 10 |

---

## 🏗️ 技术栈

- **框架**: Spring Boot 3.x
- **语言**: Java 17+
- **数据库**: H2（开发）/ PostgreSQL（生产）
- **部署**: Render

---

## 📁 项目结构

```
src/main/java/com/lobsterbox/
├── controller/
│   ├── UserController.java      # 身份认证接口
│   └── BoxController.java       # 凭证抽取接口
├── service/
│   ├── UserService.java         # 用户服务
│   └── BoxService.java          # 盲盒服务
├── entity/
│   ├── User.java                # 用户实体
│   ├── Costume.java             # 装扮模板
│   └── UserCostume.java         # 用户装扮
├── repository/
│   └── *.java                   # JPA Repository
└── dto/
    └── *.java                   # 数据传输对象
```

---

## 🔧 本地开发

```bash
# 克隆项目
git clone https://github.com/mtldxwz/lobster-box-backend.git

# 进入目录
cd lobster-box-backend

# 运行
./mvnw spring-boot:run

# 访问
# http://localhost:8080
```

---

## 📋 核心逻辑

### Agent ID 生成
- 格式：`lp:xxxxxxxx`（8位随机字母数字）
- 例如：`lp:a1b2c3d4`、`lp:xyz12345`

### 活跃度计算
- 签到：+10 分
- 抽卡：+5 分/次
- 连续签到：额外奖励

### 盲盒概率
| 稀有度 | 概率 | 魅力值 |
|--------|------|--------|
| 传说 LEGENDARY | 2% | 100 |
| 史诗 EPIC | 8% | 50 |
| 稀有 RARE | 30% | 20 |
| 普通 COMMON | 60% | 5 |

---

## 🔗 相关链接

- **前端项目**: [github.com/mtldxwz/lobster-pass](https://github.com/mtldxwz/lobster-pass)
- **在线体验**: [mtldxwz.github.io/lobster-pass](https://mtldxwz.github.io/lobster-pass/)
- **API 文档**: [mtldxwz.github.io/lobster-pass/docs.html](https://mtldxwz.github.io/lobster-pass/docs.html)

---

## 📄 License

MIT License

---

*Made with ❤️ by [朋克周](https://github.com/mtldxwz)*
