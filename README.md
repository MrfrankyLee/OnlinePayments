# OnlinePayments
##### JavaWEB支付宝第三方支付的对接
##### 关于支付宝的对接流程可参考本人这篇[博客](https://blog.csdn.net/lele52080/article/details/88842639)此处不再赘述


#### 关于该DEMO结构说明
1. 项目架构 springboot+maven+jsp
2. 项目可以直接在本地启动,默认启动地址http://localhost:8080
3. 如遇端口冲突仅需修改application.properties文件中的8080端口地址即可本地启动
4. 该项目主要对接支付宝和连连支付两家三方支付公司
5. 关于订单号,因三方支付请求时传入的订单编号必须为唯一不可重复所以本人直接在后段Controller层自动生成唯一订单号
6. 关于代码结构：
![代码结构](https://img-blog.csdnimg.cn/2019032721474390.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlbGU1MjA4MA==,size_16,color_FFFFFF,t_70#pic_center)
AliPay包下的代码为支付宝接入相关代码
LianLianPay包下的代码为支付宝接入相关代码
Resource包下为支付相关接口,具体可察阅代码
7. gotoPlainPay.jsp 为连连支付跳转到其收银台时页面
 因连连支付提交数据返回的是一个带有token的网关地址需要二次跳转
 支付宝支付提交数据返回的时一个f支付宝form页面可直接写入页面
8. 单独引入支付宝三方支付仅需在pom.xml文件中引入下面两个依赖
  ```pom.xml
		<!--支付宝支付jar包 -->
        <dependency>
            <groupId>com.alipay.sdk</groupId>
            <artifactId>alipay-sdk-java</artifactId>
            <version>3.4.49.ALL</version>
        </dependency>
        <!--fastjson 引入 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.29</version>
        </dependency>
```
9.单独引入连连支付
 由于[官方DEMO](https://github.com/LianLianPay/LLP-M-Pay-Java)相关jar包版本略低所以本人自动导包后发现有部分代码过期
如需要可以自己单独编写发送post请求的方法即可,无需引入本项目中http相关依赖文件
