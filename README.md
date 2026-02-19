# imitate_bilibili
ALL OF
# imitate_bilibili
ALL OF
# imitate_bilibili
## 关于该文档
![图片](https://docimg8.docs.qq.com/image/AgAAEd2RRaBYpWH02LhBra7-JWoG81hq.png?w=217&h=453)
点击左侧分块内容，就是要做的每一部分，有多少个分块就要做多少内容。子分块为细节、步骤。

### Category组件
开始制作category板块，删除占位的轮播图。category放在body部分。
左侧展示一个热门图标，中间的分类采用宫格布局，每一种分类占一个格子，如果分类有子分类，当鼠标悬停在主分类上时，子分类会气泡展示，根据主分类所在的行的位置，子分类气泡向上或向下展示。category内容分为两行，一行10个，如果主分类的个数超过20个，那么第二行的最后一个显示为更多，悬浮在更多上面，气泡展示剩余的主分类。同时向数据库中添加主分类（已存在的不要重复）和子分类。（主分类和子分类你随机添加到数据库中）
D：关于数据库部分，由于您要求"向数据库中添加主分类（已存在的不要重复）和子分类"，我需要修改数据库结构，添加子分类表。让我先检查一下数据库的结构，然后进行相应的修改。
启动后端服务，开始连接前后端，向后端数据库中添加数据
D：现在您可以通过访问 http://localhost:5174/ 来查看Category板块的效果，测试分类功能是否正常工作。
删掉轮播图，现在我们不需要它。前端页面报错：:5173/api/category/all:1  Failed to load resource: the server responded with a status of 404 (Not Found) Category.vue:130 Error fetching categories: Error: Failed to fetch categories
at fetchCategories (Category.vue:121:13)  还有一个问题：子分类要和主分类放在一起，用标号区别他们是主分类还是子分类即可。向category表中添加10个新的主分类数据，20个新的子分类数据（子分类你随意指定是哪个主分类的都可以）。刚才建表貌似没有成功，数据库没有发现新内容。
我采用的是navicat+redis！你再思考一下！你之前创建成功了。之前采用的什么方式？
D:现在我需要告诉用户如何通过 navicat 手动执行 SQL 语句来更新数据库表结构，然后重新启动后端服务。
1. 打开 navicat
2. 连接到 MySQL 数据库
3. 选择 bilibili 数据库
4. 打开查询编辑器
5. 复制 schema.sql 文件中的所有内容
6. 执行 SQL 语句
    这样就可以删除旧的 category 表，然后创建新的 category 表，并插入初始数据。
然后我需要重新启动后端服务，让它使用更新后的数据库结构
出错了！错误内容：xxxxxxx
用户报告了一个 SQL 错误，但从输出可以看到，我们的 SQL 语句实际上都执行成功了：删除分类表、创建分类表、插入初始分类数据（88行）、添加10个新的主分类和20个新的子分类。这个错误是 Navicat 工具在执行 SQL 语句后的内部操作导致的，与我们的 SQL 语句本身无关。现在我需要检查后端服务的状态，看看它是否已经成功启动，并且是否能够正常处理 /api/category/all 请求。
分类显示了。我需要你将气泡中的子分类横向排布，每行2到3个，避免气泡出现在header下面，无法显示分类子分类剩余内容的问题
还有一个问题，header部分的background图像，我们把它高度变大一些，高度变为现在的3倍
我需要你把header中的left、content、right都重新放在一个header-bar中，与现在的header背景图像分离。同样的，我们原本的fixed-header也变为fixed-header-bar。这样就达成了一个效果：页面打开时，header部分中的header-bar，永远固定在顶部，当页面下滑超过我们定义的高度时，fixed-header-bar出现。还有一个问题：category部分被现在的header覆盖了，这不正确，不应该让body部分的内容被遮盖！header、body、footer他们之间是互相不遮盖的！

### 推荐板块recommend组件

![图片](https://docimg3.docs.qq.com/image/AgAAEd2RRaCpD3v3RXpBiqY_mqYfJUlX.png?w=1773&h=574)
分析一下b站原版的布局。左侧为轮播图，右侧为推荐的视频。

#### 需求：左侧放轮播图，右侧根据数据库视频的info（information）来进行选择，是否为推荐视频？
左侧轮播图采用elementplus组件（告诉ai就行，具体这是什么点击链接查看https://doc-archive.element-plus.org/#/zh-CN/component/dialog），或者其它组件库的组件（让AI来推荐）
右侧依然为宫格布局，单个视频宫格（命名为VideoItem，长啥样，如下图所示。为什么要这样？因为很多页面都长这样，所以写一次，复用即可）内包含的内容：视频封面，视频信息，发布信息、时间
![图片](https://docimg1.docs.qq.com/image/AgAAEd2RRaDeGzbBDzVIvJ4Mg84lZcK4.png?w=335&h=264)


#### 需要准备的内容：
![图片](https://docimg9.docs.qq.com/image/AgAAEd2RRaD0tTh5wx9HYKvpA55dTzka.png?w=195&h=41)
在数据库中创建这两个表。问AI，使用查询语句。这是我问的，仅供参考。
video_recommend.txt
sql.txt
视频数据，创建video数据库表，video_recommend数据库表，区分普通视频和推荐视频，按需调用数据库表。
视频、视频封面。问问AI，如何将本地的多条视频数据存放到数据库中？或者直接读取（隐患，更换盘符时可能导致拿不到数据，前端无法显示相关内容！前端是由数据驱动的视图）？
#### 如何让视频来源变为本地数据库中的数据？
##### 着重修改后端，让后端读取数据的方式改为本地读取（上线到服务端之后，那就要改为本地or服务端读取（按需选择））。后端逻辑通顺之后，前端只需要修改请求的路径即可（告诉AI先修改后端，我要从本地的xx文件夹作为数据库读取的路径）。
##### 如何上传数据？
让AI给你生成一个投稿页面（投稿页面的参考代码附上，放在该文件夹中，作为一个页面，由于腾讯文档无法上传vue文件，所以自行改为vue结尾的格式。
![图片](https://docimg9.docs.qq.com/image/AgAAEd2RRaD76l22O0dMP6rU-IRONkMs.png?w=276&h=151)

），也就是该按钮的功能
![图片](https://docimg2.docs.qq.com/image/AgAAEd2RRaC6DYpF-6FA2ZLwV7ioEbCQ.png?w=112&h=68)
Upload.txt
根据当前登录的用户，来附上投稿人信息。
投稿页面功能如下。
![图片](https://docimg5.docs.qq.com/image/AgAAEd2RRaDqZDaY8-RM6JRIILVYTVdf.png?w=717&h=778)


#### 推荐视频的显示问题
所有的video先存放在一个大的video数据库表中，video_recommed单独拿出来，这样后端查询数据的时候，只需要读取video_recommend数据库表。
PS：我们现在是上传所有的视频到video表中，让代码随机的在我们上传视频时，改为推荐视频。
#### 轮播图图像来源
和取视频的思路一样。

### VideoList组件
也就是bilibili首页中，轮播图下面的部分。
### 点击category任意分类，跳转的页面
也就是随便点击一个分类之后，显示的页面内容，因为每个分类显示样式一致，所以只写一次，架构复用。
#### 这里我们命名为category_detail
观察原版b站的布局，顶部和之前主页的内容一样，只是更换了背景，分类依旧显示，但是在此页面，被选中的分类高亮。
![图片](https://docimg8.docs.qq.com/image/AgAAEd2RRaBpcTgPspJKQLErBbFu-26H.png?w=1905&h=911)
实现的逻辑，我告诉AI的话：
现在我们准备做category_detail板块。也就是点击任意一个分类所跳转的页面。无论点击哪个分类，我们的header部分都显示。只是header-background的图像不一样（留个接口，等完善我们所有的功能之后，单独从数据库表读取数据header-background的不同图像），category部分的显示方式变了（我们命名为newcategory），变为了没有图片的宫格布局（这是我的需要变！你的不需要）（其它和旧的category一模一样）。在新category左侧，热门图标变小了。和新category贴的更近了。新category下面，视频部分，展示的是宫格布局的videoitem（上面提到过），一行4个videoitem，显示该分类的所有视频（主分类显示所有，子分类只显示子分类）。用户滑倒当前页最低端，如果没有视频可以加载了，那就显示一个图标，图标旁边写着“没有更多数据了”（这个小footer也可以写为一个组件，因为其他页面也要使用）。
由于该页面是由之前的工作拼接而成的，实际难度并不大，所以在此进行几个细节的强调。
❗细节1：点击主分类，显示该分类下的全部视频
❗细节2：点击子分类，只显示子分类的视频（显示逻辑由主分类的ID决定）。
❗❗❗❗❗❗强烈注意：直接拿ID数值，可能会导致前端ID显示与后端ID不匹配，原因是：我们的ID定义时是long类型。
### VideoDetails组件
可以播放视频的页面。

### 评论板块的逻辑
1.
