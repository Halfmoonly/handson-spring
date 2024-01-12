# handson_miniFramework
## mini_SpringhasMvc
环境：
- tomcat10以下

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
- geek_ioc01
- geek_ioc02
- geek_ioc03
- geek_ioc04
- geek_ioc05
- geek_mvc01_xmlMapping：基于原生Servlet和xml路由配置，实现一种最简单的请求响应，通过response.getWriter().append(objResult.toString());来返回请求结果
- geek_mvc02_AnnoatationRequestMapping：实现基于@RequestMapping注解的mvc
- geek_mvc03_Integratedioc：将springmvc与spring容器进行整合
- geek_mvc04_uncouplingWAC：对WebApplicationContext进行解耦，实现springmvc父子容器XmlWebApplicationContext和AnnotationConfigWebApplicationContext
- geek_mvc05_HandlerMappingAndAdapter：实现HandlerMapping和HandlerAdapter，把请求解析和请求执行两步操作解耦
- geek_mvc06_requestDataBinder：实现请求路径参数与请求方法参数对象进行绑定
- geek_mvc07_ModelAndView：实现springmvc的响应值Response处理，新增两种处理方式：
  - @ResponseBody（返回json串，用于前后端分离）
  - 拼串寻找jsp页面（已淘汰）
  - tips：，如果不处理Response，那么Response返回return值

## mini_tomcat敬请期待....