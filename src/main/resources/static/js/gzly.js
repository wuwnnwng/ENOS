/**
 * 
 */
var url ;
var dlzh = $('.dlzh').html();
$('#gzlyli').on('click',function(){
	/*启用layui 时间控件*/
	layui.use('laydate', function() {
		var laydate = layui.laydate;
		laydate.render({
			elem : '#fjrq'//指定元素
		});
		var laydate2 = layui.laydate;
		laydate2.render({
			elem : '#fjrqcx', //指定元素
		});
	});
	var pageNumber=$('#gzly').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#gzly').datagrid('getPager').data("pagination").options.pageSize;
	pageActiongzly(pageNumber,pageSize,dlzh);
    var pg = $("#gzly").datagrid("getPager");
    if(pg)
    {
    	 $(pg).pagination({
 	        onBeforeRefresh:function(){
 	    	},
            onRefresh:function(pageNumber,pageSize ){
            	pageActiongzly(pageNumber,pageSize,dlzh);
             },
            onChangePageSize:function(){
             },
            onSelectPage:function(pageNumber,pageSize){
            	pageActiongzly(pageNumber,pageSize,dlzh);
             }
        });
    }
 
});
function pageActiongzly(pageNumber,pageSize,dlzh){
	var page = 0;
	if(pageNumber!=0){
		page = pageNumber-1;
	}
    var url="/ENOS/gzly/find?page="+page+"&size="+pageSize+"&dlzh="+dlzh+""
    $('#gzly').datagrid('options').url = url;
    $("#gzly").datagrid('reload'); 
}
/*查询工作记录*/
function  searchgzly(){
	var pageNumber=$('#gzly').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#gzly').datagrid('getPager').data("pagination").options.pageSize;
	pageActiongzly(pageNumber,pageSize,dlzh); 
}
/*新建信息发布*/
function newgzly(){
    $('#gzlydlg').dialog('open').dialog('center').dialog('setTitle','新建工作留言');
    $('#gzlyfm').form('clear');
    $("#savegzly"). removeClass("savedisplay");//让保存按钮显示
    /*设置发布人*/
    $('#fjr').combobox('setValue', dlzh);
    url = "/ENOS/gzly/save";
}
 
/*编辑工作记录*/
function editgzly(){ 
	var rows = $('#gzly').datagrid('getSelections');
    if(rows.length==0){//沒有选择数据提示应该选择数据
	   	 $.messager.alert('提示框','请选择数据记录','info');
	   	 return;
    }
    if(rows.length>1){
    	 $.messager.alert('提示框','每次只能修改一条数据','info');
	   	 return;
    }
    if (rows){
        $('#gzlydlg').dialog('open').dialog('center').dialog('setTitle','修改工作留言');
        $('#gzlyfm').form('load',rows[0]);
        $("#savegzly"). removeClass("savedisplay");//让保存按钮显示
//        $('#gzlyrxm').combobox('setValue', dlzh);
        url = "/ENOS/gzly/save?gl_id="+rows[0].gl_id;
    }
}
/*保存工作记录*/
function savegzly(){
	
	if($('input[name="fjrq"]').val()==""){
		 $.messager.alert('提示框','请填写日期','info');
		 return;
	}
	if($('#fjr').combobox('getValue')!=dlzh){
		 $.messager.alert('提示框','记录人应选择当前登录人','info');
		 return;
	}
	if($('#fjr').combobox('getValue')==$('#sjr').combobox('getValue')){
		 $.messager.alert('提示框','发件人和收件人不能是同一个人','info');
		 return;
	}
	
    $('#gzlyfm').form('submit',{
        url:url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            var result = eval('('+result+')');
            $.messager.alert('提示框',result.gzlymsg,'info');
            if (result.errorMsg){
                $.messager.show({
                    title: 'Error',
                    msg: result.errorMsg
                });
            } else {
                $('#gzlydlg').dialog('close');        // close the dialog
                searchgzly();
            }
        }
    });
}
/*删除工作记录*/
//function destroygzly(gzly,fjr,gl_id,url){
//	var rows = $(gzly).datagrid('getSelections');
//	if(rows.length==0){//沒有选择数据提示应该选择数据
//		 $.messager.alert('提示框','请选择数据记录','info');
//		 return;
//	 }
//	var gl_idList = new Array();
//	var flag = 0;
//	$.each(rows, function (i, n) {			
//		gl_idList[i] = n.gl_id;
//		if(n.fjr!=dlzh){
//			flag = 1;
//			 $.messager.alert('提示框','第'+(i+1)+'条数据不是你创建的不能删除','info');
//			 return;
//		}
//	});  
//	if(flag==1){
//		 return;
//	}
//	if (rows.length!=0){
//	    $.messager.confirm('确认框','你确定要删除吗?',function(r){
//	        if (r){
//	            $.post(url, {gl_idList: JSON.stringify(gl_idList),fjr:dlzh} ,function(result){                        	 
//	                if (result){ 
//	                	$.messager.alert('提示框', result.gzlymsg,'info');
//	                	searchgzly();                    
//	                } else {
//	                    $.messager.show({    // show error message
//	                        title: '删除数据时出错!',
//	                        msg: "删除失败"
//	                    });
//	                }
//	            },'json');
//	            }
//	        });
//	    }
//}
/*查询表单重置*/
$('.gzlycz').on('click',function(){
	 $('#fjrcx').combobox('setValue', "");//会议项目名称查询
	 $('#lystatuscx').val("");
	 searchgzly();
});

/*表单查询*/
$('.cxgzly').on('click',function(){
	
	 var formData  = new FormData();
	 var pageNumber=$('#gzly').datagrid('getPager').data("pagination").options.pageNumber;
     var pageSize=$('#gzly').datagrid('getPager').data("pagination").options.pageSize;
     var page = 0;
 	 if(pageNumber!=0){
 		page = pageNumber-1;
 	 }
     
	 var fjrcx =  $('#fjrcx').combobox('getValue' );//会议项目名称查询
	 var lystatuscx = $('#lystatuscx').val();
	 var fjrqcx = $('#fjrqcx').val();
	 
	 formData.append("fjrcx",fjrcx);
  	 formData.append("lystatuscx",lystatuscx);
  	 formData.append("fjrqcx",fjrqcx);
  	  
   	 
   	 $.ajax({
         type:'post',
         url:"/ENOS/gzly/find?page="+page+"&size="+pageSize+"&dlzh="+dlzh+"",
         processData: false,
         contentType: false,
         async:false,
         data:formData,
         success: function (data) {
        	   $("#gzly").datagrid('loadData',data); 
         },
         error: function ( ) {
        		$.messager.alert('提示框',"查询工作留言出错",'info');
          }
      });
  	 
});

/*确认我的提醒*/
//function qrgzly(){
//    var rows = $('#gzly').datagrid('getSelections');
//    if(rows.length==0){//沒有选择数据提示应该选择数据
//	   	 $.messager.alert('提示框','请选择数据记录','info');
//	   	 return;
//    } 
//    var gl_id_list = new Array();
//    var flag = 0;
//    $.each(rows, function (i, n) {	
//    	gl_id_list[i] = n.gl_id;
//    	if(n.lystatus=='已确认'){
//    	   flag = 1;	
//    	   $.messager.alert('提示框','第'+(i+1)+'条数据已经确认不能再次确认','info');
//    	   return;
//    	}
//	}); 
//    if(flag==1)
//    	return;
//    if (rows){
//    	$.post('/ENOS/gzly/qrstatus?', {gl_id_list: JSON.stringify(gl_id_list)} ,function(result){                        	 
//            if (result){ 
//            	$.messager.alert('提示框','已确认'+result.rows+'条数据!','info');
//            	searchgzly();          
//            } else {
//                $.messager.show({    // show error message
//                    title: '确认状态时出错!',
//                    msg: "确认失败"
//                });
//            }
//        },'json');
//    }
//}

///*查看 */
//function chakangzly(gzly,savegzly,gzlydlg,gzlyfm,chakan){
//	
//	var rows = $(gzly).datagrid('getSelections');
//    if(rows.length==0){//沒有选择数据提示应该选择数据
//	   	 $.messager.alert('提示框','请选择数据记录','info');
//	   	 return;
//    }
//    if(rows.length>1){
//    	 $.messager.alert('提示框','每次只能查看一条数据','info');
//	   	 return;
//    }
//    if (rows){
//    	$(savegzly).addClass("savedisplay");
////    	 $("#sjr").attr("readOnly",true);
//        $(gzlydlg).dialog('open').dialog('center').dialog('setTitle',chakan);
//        $(gzlyfm).form('load',rows[0]);
//    }
//}














