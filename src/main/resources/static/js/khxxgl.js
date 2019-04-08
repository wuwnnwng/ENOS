/**
 * 
 */
var url ;
var dlzh = $('.dlzh').html();
$('#khxxglli').on('click',function(){
	/*启用layui 时间控件*/
//	layui.use('laydate', function() {
//		var khhzkssq = layui.laydate;
//		khhzkssq.render({
//			elem : '#khhzkssq'//指定元素
//		});
//		var khhzjsrq = layui.laydate;
//		khhzjsrq.render({
//			elem : '#khhzjsrq'//指定元素
//		});
//		var khhzkssqcx = layui.laydate;
//		khhzkssqcx.render({
//			elem : '#khhzkssqcx', //指定元素
//		});
//		var khhzjsrq = layui.laydate;
//		khhzjsrq.render({
//			elem : '#khhzjsrqcx', //指定元素
//		});
//	});
	var pageNumber=$('#khxxgl').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#khxxgl').datagrid('getPager').data("pagination").options.pageSize;
	pageActionkhxxgl(pageNumber,pageSize );
    var pg = $("#khxxgl").datagrid("getPager");
    if(pg)
    {
    	 $(pg).pagination({
 	        onBeforeRefresh:function(){
 	    	},
            onRefresh:function(pageNumber,pageSize ){
            	pageActionkhxxgl(pageNumber,pageSize );
             },
            onChangePageSize:function(){
             },
            onSelectPage:function(pageNumber,pageSize){
            	pageActionkhxxgl(pageNumber,pageSize );
             }
        });
    }
 
});
function pageActionkhxxgl(pageNumber,pageSize ){
	var page = 0;
	if(pageNumber!=0){
		page = pageNumber-1;
	}
    var url="/ENOS/khxxgl/find?page="+page+"&size="+pageSize +""
    $('#khxxgl').datagrid('options').url = url;
    $("#khxxgl").datagrid('reload'); 
}
/*查询工作记录*/
function  searchkhxxgl(){
	var pageNumber=$('#khxxgl').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#khxxgl').datagrid('getPager').data("pagination").options.pageSize;
	pageActionkhxxgl(pageNumber,pageSize ); 
}
/*新建信息发布*/
function newkhxxgl(){
    $('#khxxgldlg').dialog('open').dialog('center').dialog('setTitle','新建客户信息管理');
    $('#khxxglfm').form('clear');
    $("#savekhxxgl"). removeClass("savedisplay");//让保存按钮显示
    /*设置发布人*/
    $('#khcjr').combobox('setValue', dlzh);
    url = "/ENOS/khxxgl/save";
}
 
/*编辑工作记录*/
function editkhxxgl(){ 
	var rows = $('#khxxgl').datagrid('getSelections');
    if(rows.length==0){//沒有选择数据提示应该选择数据
	   	 $.messager.alert('提示框','请选择数据记录','info');
	   	 return;
    }
    if(rows.length>1){
    	 $.messager.alert('提示框','每次只能修改一条数据','info');
	   	 return;
    }
    
    if (rows){
        $('#khxxgldlg').dialog('open').dialog('center').dialog('setTitle','修改客户信息管理');
        $('#khxxglfm').form('load',rows[0]);
        $("#savekhxxgl"). removeClass("savedisplay");//让保存按钮显示
//        $('#khxxglrxm').combobox('setValue', dlzh);
        url = "/ENOS/khxxgl/save?kh_id="+rows[0].kh_id;
    }
}
/*保存工作记录*/
function savekhxxgl(){
	
	if($('input[name="khhzkssq"]').val()==""){
		 $.messager.alert('提示框','请填写开始日期','info');
		 return;
	}
//	if($('input[name="khhzjsrq"]').val()==""){
//		 $.messager.alert('提示框','请填写结束日期','info');
//		 return;
//	}
	if($('#khcjr').combobox('getValue')!=dlzh){
		 $.messager.alert('提示框','创建人应选择当前登录人','info');
		 return;
	}
	 var  tel = $('input[name="khdh"]').val();
	 if(!(/^1[34578]\d{9}$/.test(tel))){ 
		   $.messager.alert('提示框','请输入正确的手机号!','info');
		   return  ;
//		   e.preventDefault();
	} 
	
    $('#khxxglfm').form('submit',{
        url:url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            var result = eval('('+result+')');
            $.messager.alert('提示框',result.khxxglmsg,'info');
            if (result.errorMsg){
                $.messager.show({
                    title: 'Error',
                    msg: result.errorMsg
                });
            } else {
                $('#khxxgldlg').dialog('close');        // close the dialog
                searchkhxxgl();
            }
        }
    });
}
 
/*查询表单重置*/
$('.khxxglcz').on('click',function(){
	 $('#khnamecx').val(); 
	 $('#khhzkssqcx').datebox('setValue', "");
	 $('#khhzjsrqcx').datebox('setValue', "");
	 $('#khhzztcx').val("");
	 searchkhxxgl();
});

/*表单查询*/
$('.cxkhxxgl').on('click',function(){
	
	 var formData  = new FormData();
	 var pageNumber=$('#khxxgl').datagrid('getPager').data("pagination").options.pageNumber;
     var pageSize=$('#khxxgl').datagrid('getPager').data("pagination").options.pageSize;
     var page = 0;
 	 if(pageNumber!=0){
 		page = pageNumber-1;
 	 }
    
	 var khnamecx =  $('#khnamecx').val(); 
	 var khhzkssqcx =  $('#khhzkssqcx').datebox('getValue');
	 var khhzjsrqcx = $('#khhzjsrqcx').datebox('getValue');
	 var khhzztcx = $('#khhzztcx').val();
	 
	 formData.append("khname",khnamecx); 
	 formData.append("khhzkssq",khhzkssqcx);
  	 formData.append("khhzjsrq",khhzjsrqcx);
	 formData.append("khhzzt",khhzztcx);
   	 $.ajax({
         type:'post',
         url:"/ENOS/khxxgl/find?page="+page+"&size="+pageSize+ "",
         processData: false,
         contentType: false,
         async:false,
         data:formData,
         success: function (data) {
        	   $("#khxxgl").datagrid('loadData',data); 
         },
         error: function ( ) {
        		$.messager.alert('提示框',"查询客户信息管理出错",'info');
          }
      });
  	 
});

 












