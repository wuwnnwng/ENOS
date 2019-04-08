/**
 * 
 */
var url ;
var dlzh = $('.dlzh').html();
$('#xmdsjli').on('click',function(){
	/*启用layui 时间控件*/
//	layui.use('laydate', function() {
//		var laydate = layui.laydate;
//		laydate.render({
//			elem : '#xdrq'//指定元素
//		});
//		var laydate2 = layui.laydate;
//		laydate2.render({
//			elem : '#xdrqcx', //指定元素
//		});
//	});
	var pageNumber=$('#xmdsj').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#xmdsj').datagrid('getPager').data("pagination").options.pageSize;
	pageActionxmdsj(pageNumber,pageSize );
    var pg = $("#xmdsj").datagrid("getPager");
    if(pg)
    {
    	 $(pg).pagination({
 	        onBeforeRefresh:function(){
 	    	},
            onRefresh:function(pageNumber,pageSize ){
            	pageActionxmdsj(pageNumber,pageSize );
             },
            onChangePageSize:function(){
             },
            onSelectPage:function(pageNumber,pageSize){
            	pageActionxmdsj(pageNumber,pageSize );
             }
        });
    }
 
});
function pageActionxmdsj(pageNumber,pageSize ){
	var page = 0;
	if(pageNumber!=0){
		page = pageNumber-1;
	}
    var url="/ENOS/xmdsj/find?page="+page+"&size="+pageSize +""
    $('#xmdsj').datagrid('options').url = url;
    $("#xmdsj").datagrid('reload'); 
}
/*查询项目大事记*/
function  searchxmdsj(){
	var pageNumber=$('#xmdsj').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#xmdsj').datagrid('getPager').data("pagination").options.pageSize;
	pageActionxmdsj(pageNumber,pageSize ); 
}
/*新建信息发布*/
function newxmdsj(){
    $('#xmdsjdlg').dialog('open').dialog('center').dialog('setTitle','新建项目大事记');
    $('#xmdsjfm').form('clear');
    $("#savexmdsj"). removeClass("savedisplay");//让保存按钮显示
    /*设置发布人*/
    $('#xdjlr').combobox('setValue', dlzh);
    url = "/ENOS/xmdsj/save";
}
 
/*编辑项目大事记*/
function editxmdsj(){ 
	var rows = $('#xmdsj').datagrid('getSelections');
    if(rows.length==0){//沒有选择数据提示应该选择数据
	   	 $.messager.alert('提示框','请选择数据记录','info');
	   	 return;
    }
    if(rows.length>1){
    	 $.messager.alert('提示框','每次只能修改一条数据','info');
	   	 return;
    }
    if (rows){
        $('#xmdsjdlg').dialog('open').dialog('center').dialog('setTitle','修改项目大事记');
        $('#xmdsjfm').form('load',rows[0]);
        $("#savexmdsj"). removeClass("savedisplay");//让保存按钮显示
//        $('#xmdsjrxm').combobox('setValue', dlzh);
        url = "/ENOS/xmdsj/save?xd_id="+rows[0].xd_id;
    }
}
/*审核项目大事记*/
function shxmdsj(){ 
	var rows = $('#xmdsj').datagrid('getSelections');
    if(rows.length==0){//沒有选择数据提示应该选择数据
	   	 $.messager.alert('提示框','请选择数据记录','info');
	   	 return;
    }
    if(rows.length>1){
    	 $.messager.alert('提示框','每次只能修改一条数据','info');
	   	 return;
    }
    if (rows){
        $('#xmdsjshdlg').dialog('open').dialog('center').dialog('setTitle','修改项目大事记');
        $('#xmdsjshfm').form('load',rows[0]);
        $("#saveshxmdsj"). removeClass("savedisplay");//让保存按钮显示
        $('#xdshr').textbox('setValue', dlzh);
        url = "/ENOS/xmdsj/shxmdsj?xd_id="+rows[0].xd_id;
    }
}
/*保存项目大事记*/
function savexmdsj(fm,dlg){
	
//	if($('input[name="xdrq"]').val()==""){
//		 $.messager.alert('提示框','请填写日期','info');
//		 return;
//	}
//	if($('#xdjlr').combobox('getValue')!=dlzh){
//		 $.messager.alert('提示框','记录人应选择当前登录人','info');
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
            $.messager.alert('提示框',result.xmdsjmsg,'info');
            if (result.errorMsg){
                $.messager.show({
                    title: 'Error',
                    msg: result.errorMsg
                });
            } else {
                $(dlg).dialog('close');        // close the dialog
                searchxmdsj();
            }
        }
    });
}
 
/*查询表单重置*/
$('.xmdsjcz').on('click',function(){
	 $('#xdxmmccx').combobox('setValue', ""); 
	 $('#xdjlrcx').combobox('setValue', ""); 
	 $('#xdshrcx').combobox('setValue', ""); 
	 $('#xdrqcx').datebox('setValue', "");
	 $('#xdshztcx').val("");
	 searchxmdsj();
});

/*表单查询*/
$('.cxxmdsj').on('click',function(){
	
	 var formData  = new FormData();
	 var pageNumber=$('#xmdsj').datagrid('getPager').data("pagination").options.pageNumber;
     var pageSize=$('#xmdsj').datagrid('getPager').data("pagination").options.pageSize;
     var page = 0;
 	 if(pageNumber!=0){
 		page = pageNumber-1;
 	 }
     
	 var xdxmmccx =  $('#xdxmmccx').combobox('getValue' ); 
	 var xdjlrcx =  $('#xdjlrcx').combobox('getValue' ); 
	 var xdshrcx =  $('#xdshrcx').combobox('getValue' ); 
	 var xdshztcx = $('#xdshztcx').val();
	 var xdrqcx = $('#xdrqcx').datebox('getValue');
	 
	 formData.append("xdxmmc",xdxmmccx);
  	 formData.append("xdjlr",xdjlrcx);
  	 formData.append("xdshr",xdshrcx);
	 formData.append("xdshzt",xdshztcx);
  	 formData.append("xdrq",xdrqcx);
  	  
   	 
   	 $.ajax({
         type:'post',
         url:"/ENOS/xmdsj/find?page="+page+"&size="+pageSize+ "",
         processData: false,
         contentType: false,
         async:false,
         data:formData,
         success: function (data) {
        	   $("#xmdsj").datagrid('loadData',data); 
         },
         error: function ( ) {
        		$.messager.alert('提示框',"查询项目大事记出错",'info');
          }
      });
  	 
});

 












