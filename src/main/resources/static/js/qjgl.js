/**
 * 
 */
var url ;
var dlzh = $('.dlzh').html();
$('#qjglli').on('click',function(){
	/*启用layui 时间控件*/
	layui.use('laydate', function() {
		var qjksrq = layui.laydate;
		qjksrq.render({
			elem : '#qjksrq'//指定元素
		});
		var qjjsrq = layui.laydate;
		qjjsrq.render({
			elem : '#qjjsrq'//指定元素
		});
		var qjksrqcx = layui.laydate;
		qjksrqcx.render({
			elem : '#qjksrqcx', //指定元素
		});
		var qjjsrq = layui.laydate;
		qjjsrq.render({
			elem : '#qjjsrqcx', //指定元素
		});
	});
	var pageNumber=$('#qjgl').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#qjgl').datagrid('getPager').data("pagination").options.pageSize;
	pageActionqjgl(pageNumber,pageSize );
    var pg = $("#qjgl").datagrid("getPager");
    if(pg)
    {
    	 $(pg).pagination({
 	        onBeforeRefresh:function(){
 	    	},
            onRefresh:function(pageNumber,pageSize ){
            	pageActionqjgl(pageNumber,pageSize );
             },
            onChangePageSize:function(){
             },
            onSelectPage:function(pageNumber,pageSize){
            	pageActionqjgl(pageNumber,pageSize );
             }
        });
    }
 
});
function pageActionqjgl(pageNumber,pageSize ){
	var page = 0;
	if(pageNumber!=0){
		page = pageNumber-1;
	}
    var url="/ENOS/qjgl/find?page="+page+"&size="+pageSize +""
    $('#qjgl').datagrid('options').url = url;
    $("#qjgl").datagrid('reload'); 
}
/*查询工作记录*/
function  searchqjgl(){
	var pageNumber=$('#qjgl').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#qjgl').datagrid('getPager').data("pagination").options.pageSize;
	pageActionqjgl(pageNumber,pageSize ); 
}
/*新建信息发布*/
function newqjgl(){
    $('#qjgldlg').dialog('open').dialog('center').dialog('setTitle','新建请假管理');
    $('#qjglfm').form('clear');
    $("#saveqjgl"). removeClass("savedisplay");//让保存按钮显示
    /*设置发布人*/
    $('#qjr').combobox('setValue', dlzh);
    url = "/ENOS/qjgl/save";
}
 
/*编辑工作记录*/
function editqjgl(){ 
	var rows = $('#qjgl').datagrid('getSelections');
    if(rows.length==0){//沒有选择数据提示应该选择数据
	   	 $.messager.alert('提示框','请选择数据记录','info');
	   	 return;
    }
    if(rows.length>1){
    	 $.messager.alert('提示框','每次只能修改一条数据','info');
	   	 return;
    }
    if (rows){
        $('#qjgldlg').dialog('open').dialog('center').dialog('setTitle','修改请假管理');
        $('#qjglfm').form('load',rows[0]);
        $("#saveqjgl"). removeClass("savedisplay");//让保存按钮显示
//        $('#qjglrxm').combobox('setValue', dlzh);
        url = "/ENOS/qjgl/save?qj_id="+rows[0].qj_id;
    }
}
/*保存工作记录*/
function saveqjgl(){
	
	if($('input[name="qjksrq"]').val()==""){
		 $.messager.alert('提示框','请填写开始日期','info');
		 return;
	}
	if($('input[name="qjjsrq"]').val()==""){
		 $.messager.alert('提示框','请填写结束日期','info');
		 return;
	}
	if($('#qjr').combobox('getValue')!=dlzh){
		 $.messager.alert('提示框','外出人应选择当前登录人','info');
		 return;
	}
//	if($('#fjr').combobox('getValue')==$('#sjr').combobox('getValue')){
//		 $.messager.alert('提示框','发件人和收件人不能是同一个人','info');
//		 return;
//	}
	
    $('#qjglfm').form('submit',{
        url:url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            var result = eval('('+result+')');
            $.messager.alert('提示框',result.qjglmsg,'info');
            if (result.errorMsg){
                $.messager.show({
                    title: 'Error',
                    msg: result.errorMsg
                });
            } else {
                $('#qjgldlg').dialog('close');        // close the dialog
                searchqjgl();
            }
        }
    });
}
 
/*查询表单重置*/
$('.qjglcz').on('click',function(){
	 $('#qjrcx').combobox('setValue', ""); 
	 $('#qjrbmmccx').combobox('setValue', ""); 
	 $('#qjlxcx').combobox('setValue', ""); 
	 $('#qjshztcx').val("");
	 searchqjgl();
});

/*表单查询*/
$('.cxqjgl').on('click',function(){
	
	 var formData  = new FormData();
	 var pageNumber=$('#qjgl').datagrid('getPager').data("pagination").options.pageNumber;
     var pageSize=$('#qjgl').datagrid('getPager').data("pagination").options.pageSize;
     var page = 0;
 	 if(pageNumber!=0){
 		page = pageNumber-1;
 	 }
     
	 var qjrcx =  $('#qjrcx').combobox('getValue' ); 
	 var qjrbmmccx =  $('#qjrbmmccx').combobox('getValue' ); 
	 var qjlxcx =  $('#qjlxcx').combobox('getValue' ); 
	 var qjshztcx = $('#qjshztcx').val();
	 var qjksrqcx = $('#qjksrqcx').val();
	 var qjjsrqcx = $('#qjjsrqcx').val();
	 
	 formData.append("qjr",qjrcx); 
	 formData.append("qjrbmmc",qjrbmmccx);
  	 formData.append("qjlx",qjlxcx);
	 formData.append("qjshzt",qjshztcx);
  	 formData.append("qjksrq",qjksrqcx);
  	 formData.append("qjjsrq",qjjsrqcx);
  	  
   	 
   	 $.ajax({
         type:'post',
         url:"/ENOS/qjgl/find?page="+page+"&size="+pageSize+ "",
         processData: false,
         contentType: false,
         async:false,
         data:formData,
         success: function (data) {
        	   $("#qjgl").datagrid('loadData',data); 
         },
         error: function ( ) {
        		$.messager.alert('提示框',"查询请假管理出错",'info');
          }
      });
  	 
});

 












