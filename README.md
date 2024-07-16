# 博客系统实现

### 技术框架
- Java 17
- Spring Boot 3.3.1
- MySQL
- MyBatis 3.0.3

### 技术要点
- 自定义全局拦截器来判断是否登录
- ThreadLocal保存用户ID，实现线程内部的数据隔离
- 使用BCrypt对用户密码进行加密处理
- 实现统一返回类和统一异常处理类
- 使用AOP方式实现日志记录
- 使用自定义注解+AOP的方式实现权限认证
- 使用Swagger生成接口文档

### 要点实现思路
- 自定义全局拦截器来判断是否登录
  - 在全局拦截器中判断用户是否处于登录,如果未登录则返回状态码401,对于无需登录即可访问的接口不进行拦截,如GET /api/posts/
```java
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        log.info("请求地址:{}", uri);
        String method = request.getMethod();
        // 根据请求方法和URI进行处理
        if (uri.startsWith("/api/posts/") && "GET".equalsIgnoreCase(method)) {
            // 对于GET请求并且是/api/posts/，不进行拦截
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            response.setStatus(401);
            return false;
        }

        Map<String, Integer> map = JWTUtil.verifyJWT(token);
        if (map != null && map.isEmpty()) {
            response.setStatus(401);
            return false;
        }

        Integer userID = map.get("userID");
        UserHolder.setUser(userID);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }

}
```
- ThreadLocal保存用户ID，实现线程内部的数据隔离
  - 在ThreadLocal中保存用户ID,需要注意的是在拦截器重写的afterCompletion方法中要手动删除,不然有内存泄漏风险
- 使用BCrypt对用户密码进行加密处理
- 实现统一返回类和统一异常处理类
    ```java
    @ControllerAdvice
    public class AllExceptionHandler {
        //进行异常处理
        @ExceptionHandler(Exception.class)
        @ResponseBody //返回JSON数据
        public Result<Object> exception(Exception e) {
            e.printStackTrace();
            return Result.fail(ResultEnum.INTERCEPTOR_ERROR);
        }
    }
    ```
    ```java
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Result<T> {
        private int code;
        private String msg;
        private T data;
  
        public static <T> Result<T> success(T data) {
            return new Result<T>(200, "操作成功", data);
        }
  
        public static <T> Result<T> success() {
            return new Result<T>(200, "操作成功", null);
        }
  
        public static <T> Result<T> fail(ResultEnum resultEnum) {
            return new Result<T>(resultEnum.getCode(), resultEnum.getMsg(), null);
        }
  
        public static <T> Result<T> fail(String msg) {
            return new Result<T>(404, msg, null);
        }
  
    }
    ```
  ```java
  @Getter
  @AllArgsConstructor
  public enum ResultEnum {
  
      SUCCESS(200, "成功"),
      INSERT_ARTICLE_ERROR(501, "新增文章失败"),
      GET_ARTICLE_ERROR(502, "未找到指定文章"),
      UPDATE_ARTICLE_ERROR_1(503, "更新文章失败"),
      REGISTER_ERROR_1(505, "注册失败，填写项不正确"),
      REGISTER_ERROR_2(506, "注册失败，已有用户注册"),
      REGISTER_ERROR_3(507, "注册失败，请稍后重试"),
      LOGIN_ERROR_1(508, "邮箱或密码错误"),
      LOGIN_ERROR_2(509, "登录失败，填写项不正确"),
      INTERCEPTOR_ERROR(510, "系统异常，请稍后重试"),
      DELETE_ARTICLE_ERROR_2(512, "删除失败"),
      PAGE_SIZE_ERROR(513, "页数或者页码出错");
  
      private final int code;
      private final String msg;
  }
  ```

- 使用AOP方式实现日志记录
    - **定义日志切面类**：使用@Aspect注解定义一个切面类，并通过@Pointcut注解定义切点，指定哪些方法需要进行日志记录。在本demo中指定所有的controller中方法都进行日志记录
    - **使用环绕通知**：使用@Around注解，在目标方法执行前后记录方法名称、参数、返回值以及异常信息。
  ```java
  @Order(1)
  @Aspect
  @Component
  @Slf4j
  public class MyAop {
  
      @Pointcut("execution(* org.lfh.blog_demo.controller.*.*(..))")
      public void classLog() {
      }
  
      @Around("classLog()")
      public Object around(ProceedingJoinPoint jp) throws Throwable {
          // 方法执行前
          // 接收到请求，记录请求内容
          ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
          HttpServletRequest request = attributes.getRequest();
          // 记录下请求内容
          System.out.println("URL : " + request.getRequestURL().toString());
          System.out.println("HTTP_METHOD : " + request.getMethod());
          System.out.println("CLASS_METHOD : " + jp);
          System.out.println("ARGS : " + Arrays.toString(jp.getArgs()));
  
          MethodSignature methodSignature = (MethodSignature) jp.getSignature();
  
          // 执行目标方法
          Object result;
          try {
              result = jp.proceed();  // 继续执行目标方法
              System.out.println("返回通知：方法的返回值 : " + result);
          } catch (Throwable throwable) {
              // 异常处理逻辑
              System.out.println("异常通知：方法异常时执行.....");
              System.out.println("产生异常的方法：" + jp);
              System.out.println("异常种类：" + throwable);
              throw throwable;  // 重新抛出异常
          }
  
          // 方法执行后
          System.out.println("后置通知：最后且一定执行.....");
          // 返回结果
          return result;
      }
  }
  ```
- 使用自定义注解+AOP的方式实现权限认证
    - **定义自定义注解**：定义自定义注解@PermissionAnnotation

  ``` java
  @Target({ElementType.METHOD}) //这里的METHOD是指作用在方法上，而TYPE指的是作用到类上
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  public @interface PermissionAnnotation {
  String value() default "";
  }
  ```

    - **定义日志切面类**：定义切点,使用@Before注解，在目标方法执行前进行权限认证,将文章所属的用户ID与ThreadLocal中保存的用户ID进行比较,不一致则抛出异常,交给全局异常处理器

  ```java
  
  @Order(2)
  @Aspect
  @Component
  public class SecurityAspect {
      @Resource
      private ArticleMapper articleMapper;
  
      @Pointcut("@annotation(org.lfh.blog_demo.aop.PermissionAnnotation)")
      public void SecurityCheck() {
      }
  
      //前置通知
      @Before("SecurityCheck()")
      public void deBefore(JoinPoint jp) {
          Object[] args = jp.getArgs();
          Integer id = (Integer) args[0];
          Integer userId = UserHolder.getUser();
          Article article = articleMapper.getArticleById(id);
  
          if (article == null || article.getUserId().intValue() != userId.intValue()) {
              throw new RuntimeException(ResultEnum.INTERCEPTOR_ERROR.getMsg());
          }
      }
  }
  ```
- 使用Swagger生成接口文档
  - 导入相关依赖,并在实体参数类和controller的方法上添加指定的注解. 注意:需要在WebConfig中配置相关路径,防止被拦截器拦截
  ```java
  @Operation(summary = "获取某个用户的所有文章列表", description = "获取某个用户的所有文章列表 支持分页/按创建时间正/倒叙")
      @Parameters({
              @Parameter(name = "uid", description = "用户ID", required = true),
              @Parameter(name = "page", description = "页数,默认为1"),
              @Parameter(name = "size", description = "每页大小,默认为10"),
              @Parameter(name = "sort", description = "1代表降序,0代表升序;默认为1"),
      })
      @ApiResponses({
              @ApiResponse(responseCode = "200", description = "查询成功"),
              @ApiResponse(responseCode = "513", description = "页数或者页码出错")
      })
      @GetMapping
      public Result listArticleById(@RequestParam Integer uid,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "1") Integer sort) {
          return articleService.listArticleById(uid, page, size, sort);
      }
  ```