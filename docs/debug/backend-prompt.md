## Repository

如下是后端代码工程名的规范，分析下面的规范，编写对一个正则表达式和示例。

"""
- 代码工程名由小写英文字母、数字和 “-”（中横线） 组成。
- 业务实现类工程命名：系统-服务化中心-微服务。
- 公共实现类工程命名：系统-服务化中心-微服务-common。
"""

## Layered

你是一个程序语言设计专家，请根据如下的文档，设计一个 DSL，用于检查代码中的层次依赖。

"""
各层内部应按照聚合分包，包名就是聚合名字，通用部分放入 `common` 包。

1. 展现层对象（interface）：

- 通过各种协议对外暴露的接口，该层内包含 Request/Response 对象定义（强制定义此对象，避免直接对外暴露领域对象），命名使用 Request/Response 后缀，其可携带 Application 层定义的 DTO
  对象。暂不对 Request/Response 外的其它 DTO 对象定义形式有要求，推荐可使用inner class或定义DTO class。
- 基于异常数据尽早拒绝服务的原则，该层包含数据格式校验逻辑，
- 该层包含Converter对象定义，负责将Request/Response对象转换为应用层定义的DTO对象，装入应用层定义的命令中，应用层定义的 ApplicationService 为入口操作。

2. 应用层对象：

- 用于入参/出参的 DTO 类型定义，命名使用 DTO 后缀；
- 用于携带 DTO 数据的命令类型定义；
- 用于异常返回的异常类型定义；
- ApplicationService 类型定义，用于接收并处理命令；在 ApplicationService 中可使用 Domain 层定义的的 Entity、DomainService、Repository
  上的方法；Transaction 处理；发领域事件；

3. 领域层对象（Domain 示例：Hierarchy、Asset）：

- 聚合，实体，值对象类型定义，命名不使用后缀；
- 用于依赖反转的 Repository、Client 接口类型定义；
- DomainEvent 和其携带的DTO数据类型定义在共享包中，在领域层完成 DomainEvent 的产生；
- 领域层可定义 Domain Exception；

4. 基础设施层对象：

- 领域层定义的 Repository 的实现类 RepositoryImpl，其内部包括 JpaRepository；
- 对领域层定义的 Client 的实现类 ClientImpl，负责加载外部接口数据。
- 消息发送实现类；如 Kafka、RabbitMQ 等。
- Converter 类，用于将 DB，**第三方数据契约**等数据对象转换为领域层对象。
- 数据库实体对象定义，命名使用 PO（Persist Object）后缀。
"""

Example:

```dsl
layered {
  application {
    package {
      pattern ".*\\.dto" namingRule {
        should endWith "DTO"
      }
      pattern ".*\\.command" namingRule {
        should endWith "Command"
      }
      pattern ".*\\.exception" namingRule {
        should endWith "Exception"
      }
      pattern ".*\\.service" namingRule {
        should endWith "Service"
      }
    }
  }

  domain {
    package {
      pattern ".*\\.model" namingRule {
        should notEndWith "DTO", "Command", "Exception", "Service"
      }
      pattern ".*\\.repository" namingRule {
        should endWith "Repository"
      }
      pattern ".*\\.client" namingRule {
        should endWith "Client"
      }
      pattern ".*\\.event" namingRule {
        should endWith "Event"
      }
    }
  }

  infrastructure {
    package {
      pattern ".*\\.impl" namingRule {
        should endWith "Impl"
      }
      pattern ".*\\.converter" namingRule {
        should endWith "Converter"
      }
      pattern ".*\\.entity" namingRule {
        should endWith "PO"
      }
    }
  }

  interface {
    package {
      pattern ".*\\.interface" namingRule {
        should endWith "Request", "Response"
      }
    }
  }

  // 检查层次依赖规则
  check DependencyRule {
    from interface to application, domain, infrastructure
    from application to domain, infrastructure
    from domain to infrastructure
  }
}
```

