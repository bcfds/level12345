# 理智系统加入说明

## 一、概览

加入了一个 **玩家理智值（Sanity）** 系统
- ✅ 持久化存储（玩家下线/世界重启后保留）
- ✅ 死亡重生数据继承
- 🚧 服务端 ↔ 客户端网络同步（需补充实现）
- 🚧 HUD 渲染（需补充实现）
- ✅ 易于扩展（可快速添加其他类似能力）

---

## 二、核心工作流程

> **通过 `Capability` 将自定义数据“挂载”到玩家身上，利用 NBT 实现存档，通过事件监听处理数据克隆，通过网络包同步到客户端，最终在 HUD 上渲染。**

---

## 三、模块职责与调用链

```mermaid
graph TD
    A[ISanity 接口] --> B[SanityDataManager 实现]
    B --> C[SanityProvider 能力提供者]
    C --> D[AttachCapabilitiesEvent 挂载到玩家]
    D --> E[PlayerEvent.Clone 数据继承]
    B --> F[serializeNBT / deserializeNBT]
    F --> G[自动存档/读档]
    B --> H[网络同步包（待实现）]
    H --> I[客户端更新]
    I --> J[SanityRender HUD渲染（待实现）]

