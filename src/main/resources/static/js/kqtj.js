/**
 * 
 */
 
$('#kqtjli').on('click',function(){
	//年度下拉框初始化
	$("#yearcx").combobox({   
		valueField:'year',    
		textField:'year',  
		panelHeight:'200' ,
		async:false, 
	    async:false,
		width: 188,
//	    height: 32,
		editable:false
	});
	var data = [];//创建年度数组
	var startYear;//起始年份
	var thisYear=new Date().getUTCFullYear();//今年
	var endYear=thisYear+1;//结束年份
	//数组添加值（2012-2016）//根据情况自己修改
	for(startYear=endYear-4;startYear<endYear;startYear++){
	    data.push({"year":startYear});
	 }
	$("#yearcx").combobox("loadData", data);//下拉框加载数据
	$("#yearcx").combobox("setValue",thisYear);//设置默认值为今年
	//月度下拉框初始化
	$("#monthcx").combobox({
		valueField:'month',    
		textField:'month',  
		panelHeight:'200' ,
		async:false ,
		async:false,
		width: 188,
		editable:false
	});
	var data1 = [];//创建月份数组
	var startMonth=1;//起始月份
	var thisMonth=new Date().getUTCMonth()+1;//本月
	//数组添加值（1-12月）为固定值
	for(startMonth;startMonth<13;startMonth++){
	data1.push({"month":startMonth});
	}
	$("#monthcx").combobox("loadData", data1);//下拉框加载数据
	$("#monthcx").combobox("setValue",thisMonth);// 设置默认值为本月
}); 
/*查询表单重置*/
$('.kqtjcz').on('click',function(){
	 $('#yearcx').combobox('setValue', "");
	 $('#monthcx').combobox('setValue', "");
//	 searchkqtj();
});

/*表单查询*/
$('.cxkqtj').on('click',function(){
	
	 var formData  = new FormData();
	 var pageNumber=$('#kqtj').datagrid('getPager').data("pagination").options.pageNumber;
     var pageSize=$('#kqtj').datagrid('getPager').data("pagination").options.pageSize;
     var page = 0;
 	 if(pageNumber!=0){
 		page = pageNumber-1;
 	 }
     
	 var yearcx =  $('#yearcx').combobox('getValue' );
	 var monthcx = $('#monthcx').combobox('getValue' );
	 
	 formData.append("year",yearcx);
  	 formData.append("month",monthcx);
   	 
   	 $.ajax({
         type:'post',
         url:"/ENOS/kqtj/find?page="+page+"&size="+pageSize+"",
         processData: false,
         contentType: false,
         async:false,
         data:formData,
         success: function (data) {
//        	  $('#kqtj').datagrid('options').url = url;
        	  $.messager.alert('提示框',data.kqtjmsg,'info');
        	  $("#kqtj").datagrid('loadData',data); 
         },
         error: function ( ) {
        		$.messager.alert('提示框',"查询考情统计出错",'info');
          }
      });
  	 
});
 
















