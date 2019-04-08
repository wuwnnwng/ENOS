/**
 * 
 */
var url ;
var dlzh = $('.dlzh').html();
$('#wcglli').on('click',function(){
	/*启用layui 时间控件*/
//	layui.use('laydate', function() {
//		var wcksrq = layui.laydate;
//		wcksrq.render({
//			elem : '#wcksrq'//指定元素
//		});
//		var wcjsrq = layui.laydate;
//		wcjsrq.render({
//			elem : '#wcjsrq'//指定元素
//		});
//		var wcksrqcx = layui.laydate;
//		wcksrqcx.render({
//			elem : '#wcksrqcx', //指定元素
//		});
//		var wcjsrq = layui.laydate;
//		wcjsrq.render({
//			elem : '#wcjsrqcx', //指定元素
//		});
//	});
	var pageNumber=$('#wcgl').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#wcgl').datagrid('getPager').data("pagination").options.pageSize;
	pageActionwcgl(pageNumber,pageSize );
    var pg = $("#wcgl").datagrid("getPager");
    if(pg)
    {
    	 $(pg).pagination({
 	        onBeforeRefresh:function(){
 	    	},
            onRefresh:function(pageNumber,pageSize ){
            	pageActionwcgl(pageNumber,pageSize );
             },
            onChangePageSize:function(){
             },
            onSelectPage:function(pageNumber,pageSize){
            	pageActionwcgl(pageNumber,pageSize );
             }
        });
    }
 
});
function pageActionwcgl(pageNumber,pageSize ){
	var page = 0;
	if(pageNumber!=0){
		page = pageNumber-1;
	}
    var url="/ENOS/wcgl/find?page="+page+"&size="+pageSize +""
    $('#wcgl').datagrid('options').url = url;
    $("#wcgl").datagrid('reload'); 
}
/*查询外出管理*/
function  searchwcgl(){
	var pageNumber=$('#wcgl').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#wcgl').datagrid('getPager').data("pagination").options.pageSize;
	pageActionwcgl(pageNumber,pageSize ); 
}
/*新建信息发布*/
function newwcgl(){
    $('#wcgldlg').dialog('open').dialog('center').dialog('setTitle','新建外出管理');
    $('#wcglfm').form('clear');
    $("#savewcgl"). removeClass("savedisplay");//让保存按钮显示
    /*设置发布人*/
    $('#wcr').combobox('setValue', dlzh);
    url = "/ENOS/wcgl/save";
}
 
/*编辑外出管理*/
function editwcgl(){ 
	var rows = $('#wcgl').datagrid('getSelections');
    if(rows.length==0){//沒有选择数据提示应该选择数据
	   	 $.messager.alert('提示框','请选择数据记录','info');
	   	 return;
    }
    if(rows.length>1){
    	 $.messager.alert('提示框','每次只能修改一条数据','info');
	   	 return;
    }
    if (rows){
        $('#wcgldlg').dialog('open').dialog('center').dialog('setTitle','修改外出管理');
        $('#wcglfm').form('load',rows[0]);
        $("#savewcgl"). removeClass("savedisplay");//让保存按钮显示
//        $('#wcglrxm').combobox('setValue', dlzh);
        url = "/ENOS/wcgl/save?wc_id="+rows[0].wc_id;
    }
}

/*审核外出管理*/
function shwcgl(){ 
	var rows = $('#wcgl').datagrid('getSelections');
    if(rows.length==0){//沒有选择数据提示应该选择数据
	   	 $.messager.alert('提示框','请选择数据记录','info');
	   	 return;
    }
    if(rows.length>1){
    	 $.messager.alert('提示框','每次只能修改一条数据','info');
	   	 return;
    }
    if (rows){
        $('#wcglshdlg').dialog('open').dialog('center').dialog('setTitle','审核外出管理');
        $('#wcglshfm').form('load',rows[0]);
        $("#saveshwcgl"). removeClass("savedisplay");//让保存按钮显示
        $('#wcshr').textbox('setValue', dlzh);
        url = "/ENOS/wcgl/shwcgl?wc_id="+rows[0].wc_id;
    }
}

/*保存外出管理*/
function savewcgl(fm,dlg){
	
//	if($('input[name="wcksrq"]').val()==""){
//		 $.messager.alert('提示框','请填写开始日期','info');
//		 return;
//	}
//	if($('input[name="wcjsrq"]').val()==""){
//		 $.messager.alert('提示框','请填写结束日期','info');
//		 return;
//	}
//	if($('#wcr').combobox('getValue')!=dlzh){
//		 $.messager.alert('提示框','外出人应选择当前登录人','info');
//		 return;
//	}
//	if($('#fjr').combobox('getValue')==$('#sjr').combobox('getValue')){
//		 $.messager.alert('提示框','发件人和收件人不能是同一个人','info');
//		 return;
//	}
	
    $(fm).form('submit',{
        url:url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            var result = eval('('+result+')');
            $.messager.alert('提示框',result.wcglmsg,'info');
            if (result.errorMsg){
                $.messager.show({
                    title: 'Error',
                    msg: result.errorMsg
                });
            } else {
                $(dlg).dialog('close');        // close the dialog
                searchwcgl();
            }
        }
    });
}
 
/*查询表单重置*/
$('.wcglcz').on('click',function(){
	 $('#wcrcx').combobox('setValue', ""); 
	 $('#wcrbmmccx').combobox('setValue', ""); 
	 $('#wcglxmcx').combobox('setValue', ""); 
	 $('#wcksrqcx').datebox('setValue', ""); 
	 $('#wcjsrqcx').datebox('setValue', ""); 
	 $('#wcshztcx').val("");
	 searchwcgl();
});

/*表单查询*/
$('.cxwcgl').on('click',function(){
	
	 var formData  = new FormData();
	 var pageNumber=$('#wcgl').datagrid('getPager').data("pagination").options.pageNumber;
     var pageSize=$('#wcgl').datagrid('getPager').data("pagination").options.pageSize;
     var page = 0;
 	 if(pageNumber!=0){
 		page = pageNumber-1;
 	 }
     
	 var wcrcx =  $('#wcrcx').combobox('getValue' ); 
	 var wcrbmmccx =  $('#wcrbmmccx').combobox('getValue' ); 
	 var wcglxmcx =  $('#wcglxmcx').combobox('getValue' ); 
	 var wcshztcx = $('#wcshztcx').val();
	 var wcksrqcx = $('#wcksrqcx').datebox('getValue');
	 var wcjsrqcx = $('#wcjsrqcx').datebox('getValue');
	 
	 formData.append("wcr",wcrcx); 
	 formData.append("wcrbmmc",wcrbmmccx);
  	 formData.append("wcglxm",wcglxmcx);
	 formData.append("wcshzt",wcshztcx);
  	 formData.append("wcksrq",wcksrqcx);
  	 formData.append("wcjsrq",wcjsrqcx);
  	  
   	 
   	 $.ajax({
         type:'post',
         url:"/ENOS/wcgl/find?page="+page+"&size="+pageSize+ "",
         processData: false,
         contentType: false,
         async:false,
         data:formData,
         success: function (data) {
        	   $("#wcgl").datagrid('loadData',data); 
         },
         error: function ( ) {
        		$.messager.alert('提示框',"查询外出管理出错",'info');
          }
      });
  	 
});

 












