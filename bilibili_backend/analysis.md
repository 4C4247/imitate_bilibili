# 前端功能和数据需求分析

## 1. 用户相关功能
- **登录/注册**：账号、密码、邮箱、验证码
- **用户信息**：用户头像、昵称、个人资料
- **登录状态管理**：token管理、会话管理

## 2. 视频相关功能
- **视频列表**：首页推荐、分类视频
- **视频详情**：视频信息、播放地址
- **视频上传**：视频文件、封面、标题、描述、标签
- **视频管理**：用户上传的视频管理

## 3. 互动相关功能
- **评论**：视频评论、回复评论
- **收藏**：用户收藏的视频
- **动态**：用户关注的动态、系统通知
- **消息**：私信、系统消息

## 4. 会员相关功能
- **会员状态**：大会员状态管理
- **会员特权**：会员专属内容、免广告等

## 5. 其他功能
- **轮播图**：首页轮播图管理
- **搜索**：视频搜索、用户搜索

# 后端架构设计

## 技术栈
- **语言**：Java
- **框架**：Spring Boot
- **ORM**：MyBatis Plus
- **数据库**：MySQL
- **认证**：JWT
- **缓存**：Redis
- **文件存储**：本地存储/对象存储

## 模块划分
- **api**：Controller层，处理HTTP请求
- **service**：Service层，业务逻辑处理
- **dao**：Mapper层，数据库操作
- **entity**：实体类，对应数据库表
- **dto**：数据传输对象，用于前后端数据交互
- **config**：配置类
- **utils**：工具类
- **exception**：异常处理

# 数据库表设计

## 1. 用户表（user）
- id：主键
- username：账号
- password：密码
- email：邮箱
- nickname：昵称
- avatar：头像
- vip_status：会员状态
- vip_expire_time：会员过期时间
- create_time：创建时间
- update_time：更新时间

## 2. 视频表（video）
- id：主键
- user_id：用户id
- title：标题
- description：描述
- cover：封面
- url：视频地址
- duration：时长
- view_count：观看次数
- like_count：点赞次数
- comment_count：评论次数
- category_id：分类id
- tags：标签
- status：状态
- create_time：创建时间
- update_time：更新时间

## 3. 评论表（comment）
- id：主键
- user_id：用户id
- video_id：视频id
- content：内容
- parent_id：父评论id
- like_count：点赞次数
- create_time：创建时间
- update_time：更新时间

## 4. 收藏表（collection）
- id：主键
- user_id：用户id
- video_id：视频id
- create_time：创建时间

## 5. 动态表（activity）
- id：主键
- user_id：用户id
- type：动态类型
- content：内容
- related_id：关联id
- create_time：创建时间

## 6. 消息表（message）
- id：主键
- sender_id：发送者id
- receiver_id：接收者id
- content：内容
- status：状态
- create_time：创建时间

## 7. 轮播图表（carousel）
- id：主键
- title：标题
- image：图片
- url：链接
- order：排序
- status：状态
- create_time：创建时间
- update_time：更新时间

## 8. 分类表（category）
- id：主键
- name：分类名称
- icon：分类图标
- order：排序
- create_time：创建时间
- update_time：更新时间

# API接口设计

## 1. 用户相关接口
- **POST /api/user/login**：用户登录
- **POST /api/user/register**：用户注册
- **GET /api/user/info**：获取用户信息
- **PUT /api/user/info**：更新用户信息
- **POST /api/user/logout**：用户登出

## 2. 视频相关接口
- **GET /api/video/list**：获取视频列表
- **GET /api/video/detail**：获取视频详情
- **POST /api/video/upload**：上传视频
- **PUT /api/video/update**：更新视频信息
- **DELETE /api/video/delete**：删除视频
- **GET /api/video/category**：获取视频分类

## 3. 评论相关接口
- **GET /api/comment/list**：获取评论列表
- **POST /api/comment/add**：添加评论
- **PUT /api/comment/update**：更新评论
- **DELETE /api/comment/delete**：删除评论

## 4. 收藏相关接口
- **GET /api/collection/list**：获取收藏列表
- **POST /api/collection/add**：添加收藏
- **DELETE /api/collection/delete**：删除收藏

## 5. 动态相关接口
- **GET /api/activity/list**：获取动态列表

## 6. 消息相关接口
- **GET /api/message/list**：获取消息列表
- **POST /api/message/send**：发送消息
- **PUT /api/message/read**：标记消息已读

## 7. 轮播图相关接口
- **GET /api/carousel/list**：获取轮播图列表

## 8. 搜索相关接口
- **GET /api/search/video**：搜索视频
- **GET /api/search/user**：搜索用户
