# commoncurd
万能后台项目  
详情见文档：[commoncurd.docx]()

## 公共查询接口(支持分页、支持跨库)（/common/getDataByViewName）
接口调用：http://localhost:8080/common/getDataByViewName?viewName=COMMON_TEST

## 公共增加/修改接口(支持多表修改、支持事务、支持跨库)（/common/addOrUpdateDataByTableNames）
接口调用：http://localhost:8080/common/addOrUpdateDataByTableNames  
参数样例：  
{
	"paramList": [
		{
			"tableName": 'cuihui_test',
			"primaryKey": 'sid',
			"cols": {
				"sid": "983E3898F0DA452D8C585A73B7DC7962",
				"sname": "cudeletei123hui",
				"age": "123"
			},
			"seqName": ""
		},
		{
			"tableName": 'cuihui_test',
			"primaryKey": 'sid',
			"cols": {
				"sid": "",
				"sname": "cui123hui",
				"age": "123"
			},
			"seqName": "SEQ_RS"
		}
	]
}

## 公共删除接口(支持多表删除、支持事务、支持跨库)（/common/deleteDataByTableNames）
接口调用：http://localhost:8080/common/deleteDataByTableNames  
参数样例：  
{
	"deleteList":[
		{
			"tableName": 'cuihui_test',
			"primaryKey": 'sid',
			"primaryKeyVal": ['3533937','12'],
			"deleteType": "1",
			"flagCol": "age",
			"flagColVal": "11"
		},
		{
			"tableName": 'cuihui_test',
			"primaryKey": 'sid',
			"primaryKeyVal": ['85D4BFDB237D47A7BA471A39D4EDE607','12'],
			"deleteType": "1",
			"flagCol": "age",
			"flagColVal": "11"
		}
	]
}

## 公共增删改接口(支持多表、支持事务、支持跨库)（/common/addOrUpdateOrDeleteDataByTableNames）
接口调用：http://localhost:8080/common/addOrUpdateOrDeleteDataByTableNames  
参数样例：  
{
	"paramList": [
        {
            "tableName": 'cuihui_test',
            "primaryKey": 'sid',
            "cols": {
                "sid": "asdasdasdasd",
                "sname": "aaa",
                "age": "aaaaaaa"
            },
            "seqName": ""
        },
        {
            "tableName": 'cuihui_test',
            "primaryKey": 'sid',
            "cols": {
                "sid": "2222222",
                "sname": "bbb",
                "age": "bbbbbbbbbbbbbbbbbbbbbbbb"
            },
            "seqName": ""
        }
    ],
	"deleteList":[
		{
			"tableName": 'cuihui_test',
			"primaryKey": 'sid',
			"primaryKeyVal": ['2222222','12'],
			"deleteType": "1",
			"flagCol": "age",
			"flagColVal": "11"
		},
		{
			"tableName": 'cuihui_test',
			"primaryKey": 'sid',
			"primaryKeyVal": ['85D4BFDB237D47A7BA471A39D4EDE607','12'],
			"deleteType": "1",
			"flagCol": "age",
			"flagColVal": "11"
		}
	]
}

## 公共接口-外部接口调用(url支持get/post请求)（/common/executeExternalAPI）
接口调用：http://localhost:8080/common/executeExternalAPI  
参数样例：  
{
	"urlID" : "aoudbtn",
	"urlParam" : {
		// url对应的参数
	}
}
