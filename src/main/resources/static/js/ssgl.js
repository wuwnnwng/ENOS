/**
 * 
 */
var url ;
var dlzh = $('.dlzh').html();
$('#ssglli').on('click',function(){
	/*启用layui 时间控件*/
//	layui.use('laydate', function() {
//		var sgksrq = layui.laydate;
//		sgksrq.render({
//			elem : '#sgksrq'//指定元素
//		});
//		var sgjsrq = layui.laydate;
//		sgjsrq.render({
//			elem : '#sgjsrq'//指定元素
//		});
//		var sgksrqcx = layui.laydate;
//		sgksrqcx.render({
//			elem : '#sgksrqcx', //指定元素
//		});
//		var sgjsrq = layui.laydate;
//		sgjsrq.render({
//			elem : '#sgjsrqcx', //指定元素
//		});
//	});
	var pageNumber=$('#ssgl').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#ssgl').datagrid('getPager').data("pagination").options.pageSize;
	pageActionssgl(pageNumber,pageSize );
    var pg = $("#ssgl").datagrid("getPager");
    if(pg)
    {
    	 $(pg).pagination({
 	        onBeforeRefresh:function(){
 	    	},
            onRefresh:function(pageNumber,pageSize ){
            	pageActionssgl(pageNumber,pageSize );
             },
            onChangePageSize:function(){
             },
            onSelectPage:function(pageNumber,pageSize){
            	pageActionssgl(pageNumber,pageSize );
             }
        });
    }
 
});
function pageActionssgl(pageNumber,pageSize ){
	var page = 0;
	if(pageNumber!=0){
		page = pageNumber-1;
	}
    var url="/ENOS/ssgl/find?page="+page+"&size="+pageSize +""
    $('#ssgl').datagrid('options').url = url;
    $("#ssgl").datagrid('reload'); 
}
/*查询宿舍管理*/
function  searchssgl(){
	var pageNumber=$('#ssgl').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#ssgl').datagrid('getPager').data("pagination").options.pageSize;
	pageActionssgl(pageNumber,pageSize ); 
}
/*新建信息发布*/
function newssgl(){
    $('#ssgldlg').dialog('open').dialog('center').dialog('setTitle','新建宿舍管理');
    $('#ssglfm').form('clear');
    $("#savessgl"). removeClass("savedisplay");//让保存按钮显示
    /*设置发布人*/
    $('#sgsqr').combobox('setValue', dlzh);
    url = "/ENOS/ssgl/save";
}
 
/*编辑宿舍管理*/
function editssgl(){ 
	var rows = $('#ssgl').datagrid('getSelections');
    if(rows.length==0){//沒有选择数据提示应该选择数据
	   	 $.messager.alert('提示框','请选择数据记录','info');
	   	 return;
    }
    if(rows.length>1){
    	 $.messager.alert('提示框','每次只能修改一条数据','info');
	   	 return;
    }
    if (rows){
        $('#ssgldlg').dialog('open').dialog('center').dialog('setTitle','修改宿舍管理');
        $('#ssglfm').form('load',rows[0]);
        $("#savessgl"). removeClass("savedisplay");//让保存按钮显示
//        $('#ssglrxm').combobox('setValue', dlzh);
        url = "/ENOS/ssgl/save?sg_id="+rows[0].sg_id;
    }
}

/*审核宿舍管理*/
function shxssgl(){ 
	var rows = $('#ssgl').datagrid('getSelections');
    if(rows.length==0){//沒有选择数据提示应该选择数据
	   	 $.messager.alert('提示框','请选择数据记录','info');
	   	 return;
    }
    if(rows.length>1){
    	 $.messager.alert('提示框','每次只能审核一条数据','info');
	   	 return;
    }
    if (rows){
        $('#ssglshdlg').dialog('open').dialog('center').dialog('setTitle','审核宿舍管理');
        $('#ssglshfm').form('load',rows[0]);
        $("#saveshssgl"). removeClass("savedisplay");//让保存按钮显示
        $('#sgjbr').textbox('setValue', dlzh);
        url = "/ENOS/ssgl/shxssgl?sg_id="+rows[0].sg_id;
    }
}

/*保存宿舍管理*/
function savessgl(fm,dlg){
	
//	if($('input[name="sgksrq"]').val()==""){
//		 $.messager.alert('提示框','请填写开始日期','info');
//		 return;
//	}
//	if($('input[name="sgjsrq"]').val()==""){
//		 $.messager.alert('提示框','请填写结束日期','info');
//		 return;
//	}
//	if($('#sgsqr').combobox('getValue')!=dlzh){
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
            $.messager.alert('提示框',result.ssglmsg,'info');
            if (result.errorMsg){
                $.messager.show({
                    title: 'Error',
                    msg: result.errorMsg
                });
            } else {
                $(dlg).dialog('close');        // close the dialog
                searchssgl();
            }
        }
    });
}
 
/*查询表单重置*/
$('.ssglcz').on('click',function(){
	 $('#sgsqrcx').combobox('setValue', ""); 
	 $('#sgjbrcx').combobox('setValue', ""); 
	 $('#sgssdzcx').textbox('setValue', "");
	 $('#sgssmccx').textbox('setValue', "");
	 $('#sgjbztcx').val("");
	 $('#sgksrqcx').datebox('setValue', "");
	 $('#sgjsrqcx').datebox('setValue', "");
	 searchssgl();
});

/*表单查询*/
$('.cxssgl').on('click',function(){
	
	 var formData  = new FormData();
	 var pageNumber=$('#ssgl').datagrid('getPager').data("pagination").options.pageNumber;
     var pageSize=$('#ssgl').datagrid('getPager').data("pagination").options.pageSize;
     var page = 0;
 	 if(pageNumber!=0){
 		page = pageNumber-1;
 	 }
    
	 var sgsqrcx =  $('#sgsqrcx').combobox('getValue' ); 
	 var sgjbrcx =  $('#sgjbrcx').combobox('getValue' ); 
	 var sgssdzcx = $('#sgssdzcx').textbox('getValue');
	 var sgssmccx = $('#sgssmccx').textbox('getValue');
	 var sgjbztcx = $('#sgjbztcx').val();
	 var sgksrqcx = $('#sgksrqcx').datebox('getValue');
	 var sgjsrqcx = $('#sgjsrqcx').datebox('getValue');
	 
	 formData.append("sgsqr",sgsqrcx); 
	 formData.append("sgjbr",sgjbrcx);
  	 formData.append("sgssdz",sgssdzcx);
	 formData.append("sgssmc",sgssmccx);
  	 formData.append("sgjbzt",sgjbztcx);
  	 formData.append("sgksrq",sgksrqcx);
  	 formData.append("sgjsrq",sgjsrqcx);
   	 
   	 $.ajax({
         type:'post',
         url:"/ENOS/ssgl/find?page="+page+"&size="+pageSize+ "",
         processData: false,
         contentType: false,
         async:false,
         data:formData,
         success: function (data) {
        	   $("#ssgl").datagrid('loadData',data); 
         },
         error: function ( ) {
        		$.messager.alert('提示框',"查询宿舍管理出错",'info');
          }
      });
  	 
});

 












