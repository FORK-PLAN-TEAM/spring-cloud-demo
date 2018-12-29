
swagger导出手册

1.cd到项目目录运行 mvn clean test
2.运行 mvn asciidoctor:process-asciidoc
3.把 asciidoctorj-pdf-1.5.0-alpha-zh.16.jar包 \gems\asciidoctor-pdf-1.5.0.alpha.zh.16\data\ 文件夹下的
  fonts、themes复制到生成项目的docs\asciidoc\generated目录
  下载地址：https://download.csdn.net/download/zhuyu19911016520/10855848
4.运行 mvn generate-resources

