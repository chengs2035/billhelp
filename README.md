# billhelp
基于腾讯云票据识别接口实现的PDF票据文件重命名工具
代码写的非常简陋,没有认真做优化,只要能达到目的就行.

# 使用
在运行目录下建立一个config.json文件,如下:
```json
{
"secretId":"你的secretId",
"secretKey":"你的secretKey",
"pdfpath":"你的pdf文件夹路径"
} 

```


使用`java -jar 打包产物.jar` 运行即可

# 可选配置
logpath:可用于指定运行日志存储的位置,如果没有指定,则默认为程序运行的目录

"addOutFileDate":此项配置后,会增加一个输出带日期的文件,可选值:"add_date"
示例如下:

```json

{
"secretId":"你的secretId",
"secretKey":"你的secretKey",
"pdfpath":"你的pdf文件夹路径",
"addOutFileDate":"add_date",
"logpath": "/home/ya/log/"
} 

```

# windows路径
windows路径默认采用` \ `,json处理的时候会当作转义字符,所以需要增加一个` \ `,变成`\\`,比如:
`"logpath":"D:\\output\\log\\"`

# 联系
[这里联系我][https://djc8.cn/archives/pdf-use-tencent-cloud-bill-recognition-interface-to-automatically-modify-the-pdf-file-name.html]
