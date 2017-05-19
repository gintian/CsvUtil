# CsvUtil
csv格式文件数据读取工具类

使用方法：
打jar包后，调用src.com.hp.csv.GameDataReader类的genBean()方法 填入对应参数 可以根据对应的配置文件获取对应csv文件中数据的封装类列表
如果是web项目中调用genBean()方法，配置文件必须放置在web路径的csvConfig文件夹下，该配置文件名称必须是file.xml
csv数据文件请放置在项目一级目录下的csv文件夹中

生成csv文件对应的封装类需要调用src.com.hp.csv.GenJavaFile类的gen()方法

csv文件格式可参考csv路径下的csv文件格式
配置文件格式可参考src.dbConfig.file.xml的具体格式
