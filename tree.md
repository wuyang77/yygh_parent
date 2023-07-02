卷 项目 的文件夹 PATH 列表
卷序列号为 1C6D-52CB

yygh_parent 114北京市预约挂号平台（前后端分离）
├─coder_generator 代码生成器
├─common 公共模块
│  ├─model 实体类
│  ├─rabbit_util rabbitmq工具
│  ├─service_utils 微服务工具类
│  │  ├─config 配置类
│  │  ├─exception 自定义异常
│  │  ├─handler 全局异常
│  │  ├─result 统一返回结果
│  │  ├─utils 工具类
├─hospital-manage 医院系统（上传数据使用，前后端不分离）
├─service 微服务模块，包含所有的子服务模块
│  ├─service_cmn 数据字典
│  ├─service_hosp 医院模块
│  ├─service_msm 短信模块
│  ├─service_orders 订单模块
│  ├─service_oss 对象存储模块
│  ├─service_statistics 数据统计
│  ├─service_task 定时任务
│  └─service_user 用户模块 
├─service_client 远程调用（OpenFeign的客户端，命名：service_被调用模块对象名_client）
│  ├─service_cmn_client
│  ├─service_hosp_client
│  ├─service_order_client
│  └─service_user_client 
├─service_gateway 网关模块（跨域请求，yml文件设置断言）
├─sql 项目所用的数据
│  ├─yygh_cmn.sql
│  ├─yygh_hosp.sql
│  ├─yygh_manage.sql
│  ├─yygh_order.sql
│  ├─yygh_user.sql
├─vue-admin-template 医院管理后台系统（前端）
└─yygh-sitedemo 医院用户平台系统（前端）
  