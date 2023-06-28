
```
application {
    name: "MyApp"
    description: "A composable application for flexible business needs"
}

packagedBusinessCapability (pbc) {
    name: "AccountManagement"
    description: "Provides account management functionality"
    api {
        endpoints: ["https://api.myapp.com/accounts"]
        methods: ["GET", "POST", "PUT", "DELETE"]
        authentication: "Bearer Token"
    }
}

packagedBusinessCapability (pbc) {
    name: "OrderProcessing"
    description: "Handles order processing operations"
    api {
        endpoints: ["https://api.myapp.com/orders"]
        methods: ["GET", "POST", "PUT", "DELETE"]
        authentication: "API Key"
    }
}

packagedBusinessCapability (pbc) {
    name: "PaymentGateway"
    description: "Integration with payment gateway services"
    api {
        endpoints: ["https://api.paymentgateway.com"]
        methods: ["POST"]
        authentication: "API Key"
    }
}

assembly {
    name: "MyAssembly"
    description: "Assembly of components for MyApp"
    components {
        - "AccountManagement"
        - "OrderProcessing"
        - "PaymentGateway"
    }
}

integrationPlatform {
    name: "MyIntegrationPlatform"
    description: "Enterprise integration platform for data integration"
    connectors {
        - "Salesforce"
        - "SAP"
        - "Database"
    }
}

compose {
    name: "MyComposedApp"
    description: "Composed application using packaged business capabilities"
    assemblies {
        - "MyAssembly"
    }
    integrations {
        - "MyIntegrationPlatform"
    }
    userInterface {
        type: "Web"
        endpoints: ["https://mycomposedapp.com"]
    }
}
```

Sample two

```
enterpriseArchitecture {
  components {
    component CoreBusinessCapability
    component BusinessModule
    component OperationModule
  }
  
  relationships {
    relationship CoreBusinessCapability {
      uses BusinessModule
      uses OperationModule
    }
    
    relationship BusinessModule {
      dependsOn CoreBusinessCapability
    }
    
    relationship OperationModule {
      dependsOn CoreBusinessCapability
    }
  }
  
  packagedBusinessCapabilities {
    packagedBusinessCapability PBC {
      definition {
        // PBC definition goes here
      }
      interfaces {
        api
        eventChannel
      }
      optionalCapabilities {
        userInterface
      }
    }
  }
  
  assembly {
    assembledApplications {
      assembledApplication {
        name
        components {
          // List of components included in the assembled application
        }
        integration {
          // Integration configuration goes here
        }
        userExperience {
          // User experience configuration goes here
        }
      }
    }
  }
}
```

Sample 2:

你是一个世界一流的软件架构师，请参考最好的几家银行的架构，使用如下的 DSL 来描述你的架构，包括核心业务能力、业务模块、运营模块等。

要求：

1. DSL 中的注释（do some DSL design in here）需要你替换掉为你理解的设计。
2. DSL 可能有一些小问题，你需要自己思考如何改进 DSL。

```kotlin
assembly {
  components {
    component("BusinessModule2") {
      // do some DSL design in here
    }

    component("IntegrationModule") {
        // do some DSL design in here
    }
  }

  interactions {
    connect("BusinessModule1", "IntegrationModule")
    connect("BusinessModule2", "IntegrationModule")
    // ... Define more connections
  }

  capabilities {
    pbc("PackagedBusinessCapability2") {
        // do some DSL design in here
    }

    // ... Define more PBCs
  }

  teams {
    team("FusionTeam1") {
        // do some DSL design in here
    }
    // ... Define more fusion teams
  }

  applications {
    application("ComposableApp1") {
        // do some DSL design in here
    }
    // ... Define more composable applications
  }
}
```


Samples:

```
assembly {
  components {
    component("AccountManagement") {
      // 账户管理模块，处理账户开户、查询、修改等功能
      // ...
    }

    component("PaymentProcessing") {
      // 支付处理模块，处理支付请求、风控等功能
      // ...
    }

    // ... 添加更多的业务模块
  }

  interactions {
    connect("AccountManagement", "PaymentProcessing")
    // ... 定义更多的组件之间的连接关系
  }

  capabilities {
    pbc("FinancialServices") {
      // 金融服务能力，提供账户管理、支付处理等核心业务能力
      // ...
    }

    // ... 定义更多的打包业务能力（Packaged Business Capabilities）
  }

  teams {
    team("CoreBankingTeam") {
      // 核心银行团队，负责核心银行系统的开发和维护
      // ...
    }

    team("PaymentTeam") {
      // 支付团队，负责支付相关模块的开发和维护
      // ...
    }

    // ... 定义更多的团队
  }

  applications {
    application("InternetBankingApp") {
      // 互联网银行应用，提供在线银行服务给客户
      // ...
    }

    application("MobileBankingApp") {
      // 移动银行应用，提供移动设备上的银行服务
      // ...
    }

    // ... 定义更多的应用
  }
}
```