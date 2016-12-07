#Servlet的发布

需要将servlet项目的Webcontent复制到Apache Tomcat的安装目录下的webapps中，得到的URL为[http://localhost:端口/PigeonServlet/](http://localhost:端口/PigeonServlet/):

![](https://github.com/GBXU/Pigeon/raw/master/doc/images/PigeonServlet_0.jpg)

其中servlet项目中的build文件夹下编译好的classes也要复制到上图的WEB-INF中
![classes](https://github.com/GBXU/Pigeon/raw/master/doc/images/PigeonServlet_2.png)

![WEB-INF](https://github.com/GBXU/Pigeon/raw/master/doc/images/PigeonServlet_1.png)

上传到服务器:
windows上cmd
进入等待上传的文件所在目录

	cd  所在目录

pscp上传

	pscp -i A:\Users\用户名\.ssh\Putty\PuttyGenSSH_private.ppk 文件.zip root@服务器IP地址:/home