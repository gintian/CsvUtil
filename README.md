# CsvUtil
csv格式文件数据读取工具类

使用方法：
生成csv文件对应数据封装类：生成csv文件对应的封装类需要调用src.com.hp.csv.GenJavaFile类的gen()方法
获取数据方式：打jar包后，调用src.com.hp.csv.GameDataReader类的genBean()方法，填入对应参数，就获取对应csv文件中的数据封装列表

文件格式：
csv文件格式可参考csv路径下的csv文件格式
配置文件格式可参考src.dbConfig.file.xml的具体格式

文件放置说明：
csv文件请放置在项目的一级目录名为csv的文件夹下
file.xml文件在非web项目可随意放置，在web项目中请放置于一级目录名为csvConfig的文件夹下，
