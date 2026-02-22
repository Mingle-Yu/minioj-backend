# minioj后端部署

远程服务器为Ubuntu24

### 本地项目打包

```shell
cd <项目根目录>
mvn clean package -DskipTests
```



### 本地打包前端项目

```
cd <你的项目根目录>
# 在执行下面一条命令时，如果遇到报错：error  in node_modules/@types/node/crypto.d.ts:534:86
# 需要降低一下@types/node的版本，比如：
# npm uninstall @types/node --save-dev
# npm install @types/node@14.18.63 --save-dev
npm run build
```



### 上传文件到远程服务器

将打包好的文件`jar`包上传到`/home/ubuntu`目录



### 启动项目

```shell
sudo apt install screen -y
screen -S app
java -jar minioj-backend-0.0.1-SNAPSHOT.jar
# 按Ctrl+A+D脱离会话
# 重新连接会话：screen -r app
```