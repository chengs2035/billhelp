# billhelp
基于腾讯云票据识别接口实现的PDF票据文件重命名工具

# 使用
在运行目录下建立一个config.json文件,如下:
```json
{
"secretId":"你的secretId",
"secretKey":"你的secretKey",
"pdfpath":"你的pdf文件夹路径",
"addOutFileDate_desc":"此项配置后,会增加一个输出带日期的文件",
"addOutFileDate":"add_date"
} 

```


使用`java -jar 打包产物.jar` 运行即可
