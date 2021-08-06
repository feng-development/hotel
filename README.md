# 项目介绍

## 项目背景

本项目本来采用微服务的思想搭建，每个模块一个项目，后不符合公司规定。改为以model的方式放在一起。
如若后期需要，可将bom-pom.xml中的各个服务拆分，每个人负责一个模块，开发互不影响。提高开发效率。

### 模块介绍

+ bom bom定义了公用依赖及版本
+ zhilian-core 定义最基础的配置和工具类
+ zhilian-common 定义了写公用配置、工具类配置等
+ zhilian-api 服务之间调用的fegin 传输对象等
+ zhilian-processor 事件服务 对消息队列的封装

## 项目注意事项 为了接手你工作的人不骂你 请遵守一下规则
+ 非特殊情况 不允许返回前端map
+ 不允许返回数据库对象给前端
+ 项目中 不允许出现魔法值 请定义常量或枚举
+ 多项目共用变量放在 common 项目中 不允许两个系统中定义两遍
+ 所有接口 必须有完整的注释，私有方法必须有完整的注释
+ 尽量不要出现 for if多层嵌套
+ 不同逻辑的多行代码 多空一行
+ 未完待续 欢迎补充

### 3.事件服务客户端如何使用

#### 3.1添加项目依赖

    <dependency>
         <groupId>com.zhilian</groupId>
         <artifactId>event-client</artifactId>
         <version>1.0-SNAPSHOT</version>
    </dependency>

#### 3.2将SendService注入到当前的spring容器中

将**EventSendService**注入到Spring的容器中

通过**SendServiceFactory**进行，这种方式是基于event-client中的三种配置而加载的
event-client中有dev、test、prod三个环境默认的配置信息，但是因为这个配置文件是在jar文件中的，因此
当我们不实用这三种模式的时候，我们不能用这个方式来注入

        @Configuration
        public class EventConfiguration {

            @Bean
            public SendEventService eventSendService() {
            return SendServiceFactory.create("dev", SendServiceFactory.RABBIT_MQ);
            }
        
        }


#### 3.3事件的定义

事件统一定义在event包下，所有的事件都需要继承AbstractEvent. 用例如下

    public class OrderCreatedEvent extent AbstractEvent {
    
        private String orderNo;
        
        private String productName;
    
        public OrderCreatedEvent(String orderNo, String productName) {
            this.orderNo = orderNo;
            this.producetName = producetName;
        }
    
    }

#### 3.4事件的发送

刚才已经定了事件和事件发送服务，接下来发送事件 用例如下

    @Service
    public class XXX {
        
        private EventSendService eventSendService;
        
        function() {
      
            ...
            
            eventSendService.sendAsync(new OrderCreatedEvent("123", "this is product"), new DefaultEventFuture());
            
            ...
            
        }
    }


### 4.事件处理

事件的处理目前在event-processor系统中

系统已经为开发人员提供了异步、重试、以及多次失败后的提醒。但是相应的业务需要用户自己来书写。用例如下

    public class OrderCreatedEventProcessor extend AbstractEventProcessor<OrderCreatedEvent> {
    
        public void process0(OrderCreatedEvent event) {
            System.out.println(event.content());
        }
    
    }

到此，你的一个事件处理就完成了



   
    
