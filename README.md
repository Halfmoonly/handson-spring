# handson_miniFramework
我希望你能够抵达山顶，一览众山小
## mini_SpringSeries

开发环境：
- javax.servlet-api
- tomcat10-
- Idea 2023.2
- jdk8 or jdk17

Tomcat 10是第一个不再使用javax.servlet和相关包的版本，mini_SpringhasMvc没有对Tomcat 10做适配。
> 在Tomcat 10+中，Servlet API已经迁移到了Jakarta EE命名空间（jakarta.servlet）。这是因为Java EE已经转移到了Eclipse基金会，并更名为Jakarta EE。因此，Servlet API也需要进行相应的更改

采用快速迭代的开发模式，从一个最简单的程序开始，一步步堆积演化，每写一小段代码，都是一个可运行的程度。在不断迭代中完善框架功能，最终实现 Spring 框架的核心：IOC、MVC、JDBC Template 和 AOP。在一步步的迭代过程中，将 Spring 的底层原理融入代码中，一层层对照 Spring 框架的现有结构，让原理理解起来不再困难。
1. 第一部分：IoC 容器
    IoC 容器是 Spring 核心中的核心，Spring 抽象出 Bean 这个概念，用一个容器管理所有的 Bean，并解决上层应用的业务对象之间的耦合问题。后面所有的特性都依赖于 Bean 的概念和这个容器。因此即使我们简单地说 Spring 框架就是一个 IoC 容器也未尝不可。这个部分我们会从一个极简容器开始，逐步扩展增强，最终实现一个完整的 IoC 容器，包含 Spring 框架对应的核心功能，实现 Bean 的管理。基于这个核心，逐步扩展到 MiniSpring 的其他特性。打好这个基础，后面的学习会事半功倍。
   
2. 第二部分：MVC
    MVC 是 Spring 支持 Web 模式的程序结构，它是基于 Servlet 技术实现的。基本思路是利用 Servlet 机制，用一个单一的 Servlet 拦截所有请求，然后根据请求里面的的信息把任务分派给不同的业务类处理，实现原始的 MVC 结构。在这一部分，我们还会将 MVC 与第一部分的 IoC 容器结合起来，构成一个更大、更完整的框架。在一步步的构造过程中，我们会重点讲解大师们怎么逐步拆解这个 Servlet 的功能，把专业的事情交给专门的部件去做，最后构建成一个完整的体系。

3. 第三部分：JDBC Template
   JDBC Tempalte 是 Spring 对数据访问的一个实现，我们会重点分析 Spring 的实现方法，体现 Rodd Johnson 对简洁实用原则的把握。这一部分，我们会学习如何提取出一个 JDBC 访问的模板，来固化访问数据库的流程，怎么自动绑定参数值，简化上层应用程序。在此基础之上，我们还将了解到如何通过数据库连接池提高访问性能，以及模仿 MyBatis 将 SQL 语句配置到外部文件中。通过这部分的学习，我们可以了解到，整个 JDBC Template 的实现都是运用了前面 IoC 管理 Bean 的方式，将数据的访问抽象成一个个 Bean，注入到系统中。由此，更能深刻体会到 IoC 容器的功用。

4. 第四部分：AOP
   AOP 是 Spring 框架中实践面向切面编程的探索。面向对象和面向切面，两者一纵一横，编织成完整的程序结构。在这一部分，我们将了解到 Spring AOP 所采用的一个实现方式：JDK 动态代理。我们会学习动态代理的原理，以及如何用这个技术动态插入业务逻辑，实现切面的特性。最后我们将再一次看到 AOP 与 IoC 的结合，使用 BeanPostProcessor 自动生成动态代理。这时你就会体会到，我前面说的“IoC 是 Spring 框架核心中的核心”。

在这一步一步的演化过程中，我们对 Spring 的模仿逐渐成型。我坚持一个原则，就是每一步都是可以运行的，都会有看得见的收获，你不需要辛辛苦苦等到最后才能看到成果。当然，自己动手模仿 Spring，是一个难度较大的工作，风景虽好，但过程也是充满艰辛的，最后的果实属于不断探索的人。任何一个技术领域都是这样，不断练习，反复琢磨，最后才能站在山顶。
《诗经》有云：“有匪君子，如切如磋，如琢如磨”。虽然中途会遇到困难，但我希望你可以坚持学习，站到山顶，跟我一起领略 Spring 的风采！

工程结构如下图所示：
- geekA_ioc01_nativeClassPathXmlApplicationContext：实现了一个简易的IOC容器ClassPathXmlApplicationContext，耦合了Resource，耦合了Reader，耦合了BeanFactory的功能
- geekA_ioc02_expandBeanDefinition：扩展了BeanDefinition的定义信息，支持Setter注入，支持构造注入，维护单实例Bean，并且解耦了ClassPathXmlApplicationContext
- geekA_ioc03_realizeDIAndCircleDI：实现了依赖注入，引入二级缓存，解决了循环依赖
- geekA_ioc04_AutowireCapableBeanFactoryAndProcessor：定义BeanPostProcessor接口，实现了AutowireCapableBeanFactoryAndProcessor支持@Autowired注入
- geekA_ioc05_iocengineDefaultListableBeanFactory：构建完整的工厂体系DefaultListableBeanFactory
- geekA_ioc06_fullContextSystemAndEvent：构建完整的ApplicationContext上下文体系并添加容器事件
- geekB_mvc01_xmlMapping：基于原生Servlet和xml路由配置，实现一种最简单的请求响应，通过response.getWriter().append(objResult.toString());来返回请求结果
- geekB_mvc02_AnnoatationRequestMapping：实现基于@RequestMapping注解的mvc
- geekB_mvc03_Integratedioc：将springmvc与spring容器进行整合
- geekB_mvc04_uncouplingWAC：对WebApplicationContext进行解耦，实现springmvc父子容器XmlWebApplicationContext和AnnotationConfigWebApplicationContext
- geekB_mvc05_HandlerMappingAndAdapter：实现HandlerMapping和HandlerAdapter，把请求解析和请求执行两步操作解耦
- geekB_mvc06_requestDataBinder：实现请求路径参数与请求方法参数对象进行绑定
- geekB_mvc07_ModelAndView：实现springmvc的响应值Response处理，新增两种处理方式：1.@ResponseBody（返回json串，用于前后端分离）2.拼串寻找jsp页面（已淘汰）
- geekC_jdbc01_IntegratediocJdbcTemplateAndDataSource：实现一个JdbcTemplate，并且封装数据源DataSource，最终将JdbcTemplate和DataSource与IOC容器做了整合
- geekC_jdbc02_expandTemplateAndSingleResponsibilitPrinciple：扩展了JdbcTemplate，根据单一职责，抽取出关于SQL输入参数处理的组件ArgumentPreparedStatementSetter，抽取出处理SQL返回结果与对象的绑定的组件RowMapper和ResultSetExtractor
- geekC_jdbc03_PooledDataSource：实现了数据库（源）连接池PooledDataSource，并与IOC整合，替换SingleConnectionDataSource
- geekC_jdbc04_realizeBatisSqlSession：仿写了mybatis框架，实现了配置化SQL语句
- geekD_aop01_IntegratediocDynamicProxyByFactoryBean：原生的jdk动态代理还是会暴露代码，因为我们完成了零代码侵入的动态代理的框架封装
- geekD_aop02_uncouplingByInterceptorAndInvocation：解耦Interceptor和Invocation，实现三种Interceptor（默认环绕，前置，后置）
- geekD_aop03_realizePointcutToMatchmethods：实现配置化的切入点表达式，批量增强目标方法
- geekD_aop04_AutoProxyCreatorToMatchclasss：添加配置化的AutoProxyCreator后置处理器，批量代理目标类

## mini_tomcat敬请期待....
