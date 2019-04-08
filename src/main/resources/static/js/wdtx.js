/**
 * 
 */
var url;
var dlzh = $('.dlzh').html();
$('#wdtxli').on('click',function(){
//	var status = $("#wstxstatus").val();
//    var bcolumn=$('#wdtx').datagrid('getColumnOption','status'); 
//    if(status==1){
//	   bcolumn.title='已确定';
//	   bcolumn.text='已确定';
//       bcolumn.field='status';
//    }else{
//	   bcolumn.title='未确定';
//	   bcolumn.text='已确定';
//       bcolumn.field='status';
//    }
	/*启用layui 时间控件*/
	layui.use('laydate', function() {
		var laydate = layui.laydate;
		//执行一个laydate实例
		laydate.render({
			elem : '#riqi' //指定元素
//			type: 'datetime'
		});
	});
	var pageNumber=$('#wdtx').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#wdtx').datagrid('getPager').data("pagination").options.pageSize;
	var wdtxfield = $('input[name="wdtxfield"]').val();
	pageAction(pageNumber,pageSize,wdtxfield,dlzh);
    var pg = $("#wdtx").datagrid("getPager");
    if(pg)
    {
       $(pg).pagination({
	        onBeforeRefresh:function(){
	    	},
           onRefresh:function(pageNumber,pageSize){
        	   pageAction(pageNumber,pageSize,wdtxfield,dlzh);
            },
           onChangePageSize:function(){
            },
           onSelectPage:function(pageNumber,pageSize){
        	   pageAction(pageNumber,pageSize,wdtxfield,dlzh);
            }
       });
    }
    /*处理转态值0：未确定 1：已确定*/
//    var rows = $("#wdtx").datagrid("getRows"); //这段代码是获取当前页的所有行。
//    for(var i=0;i<rows.length;i++)
//    {
//    	if(rows[i].status=="已确定"){
//    		rows[i].status=="已确定";
//    	}else{
//    		rows[i].status=="未确定";
//    	}
//    }
//	getUserName();//给下拉框填充用户名
});
function pageAction(pageNumber,pageSize,wdtxfield,dlzh){
	var page = 0;
	if(pageNumber!=0){
		page = pageNumber-1;
	}
    var url="/ENOS/wdtx/find?page="+page+"&size="+pageSize+"&field="+wdtxfield+"&dlzh="+dlzh+""
    $('#wdtx').datagrid('options').url = url;
    $("#wdtx").datagrid('reload'); 
}

function  searchWdtx(){
	var pageNumber=$('#wdtx').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#wdtx').datagrid('getPager').data("pagination").options.pageSize;
	var wdtxfield = $('input[name="wdtxfield"]').val();
	pageAction(pageNumber,pageSize,wdtxfield,dlzh); 
}
/*新建我的提醒*/
function newWdtx(){
    $('#wdtxdlg').dialog('open').dialog('center').dialog('setTitle','新建提醒');
    $('#wdtxfm').form('clear');
    /*设置发布人*/
    $('#fbr').combobox('setValue', dlzh);
//    $('input[name="fbr"]').val(dlzh);
//    var fbr =  $('input[name="fbr"]').val();
//    $('input[name="fbr"]').attr("value", dlzh);
//    var fbr1 =  $('input[name="fbr"]').val();
    url = "/ENOS/wdtx/save";
}
/*确认我的提醒*/
function qrWdtx(){
    var rows = $('#wdtx').datagrid('getSelections');
    if(rows.length==0){//沒有选择数据提示应该选择数据
	   	 $.messager.alert('提示框','请选择数据记录','info');
	   	 return;
    } 
    /*前端验证，只能删除我发布的提醒，不能删除别人发布的提醒*/
//    var fbr = $('input[name="fbr"]').val();
//    if(fbr!=dlzh){
//       $.messager.alert('提示框','只能确认你自己发布的提醒','info');
//       return;
//    }
    var wt_id = new Array();
    var flag = 0;
    $.each(rows, function (i, n) {	
    	wt_id[i] = n.wt_id;
    	if(n.status==1){
    	   flag = 1;	
    	   $.messager.alert('提示框','第'+(i+1)+'条数据已经确认不能再次确认','info');
    	   return;
    	}
	}); 
    if(flag==1)
    	return;
    if (rows){
    	$.post('/ENOS/wdtx/qrstatus?', {wt_id: JSON.stringify(wt_id)} ,function(result){                        	 
            if (result){ 
            	$.messager.alert('提示框','已确认'+result.rows+'条数据!','info');
            	searchWdtx()             
            } else {
                $.messager.show({    // show error message
                    title: '确认状态时出错!',
                    msg: "确认失败"
                });
            }
        },'json');
    }
}
/*编辑我的提醒*/
function editWdtx(){ 
//    var row = $('#wdtx').datagrid('getSelected');
	var rows = $('#wdtx').datagrid('getSelections');
    if(rows.length==0){//沒有选择数据提示应该选择数据
	   	 $.messager.alert('提示框','请选择数据记录','info');
	   	 return;
    }
    if(rows.length>1){
    	 $.messager.alert('提示框','每次只能修改一条数据','info');
	   	 return;
    }
    if (rows){
        $('#wdtxdlg').dialog('open').dialog('center').dialog('setTitle','修改提醒');
        $('#wdtxfm').form('load',rows[0]);
        url = "/ENOS/wdtx/save?wt_id="+rows[0].wt_id;
    }
}
/*保存我的提醒*/
function saveWdtx(){
	
	if($('#fbr').combobox('getValue')!=dlzh){
		 $.messager.alert('提示框','发布人应选择当前登录人','info');
		 return;
	}
	if($('#fbr').combobox('getValue')==$('#zdr').combobox('getValue')){
		 $.messager.alert('提示框','发布人和指定人不能是同一个人','info');
		 return;
	}
    $('#wdtxfm').form('submit',{
        url:url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            var result = eval('('+result+')');
            $.messager.alert('提示框',result.wdtxmsg,'info');
            if (result.errorMsg){
                $.messager.show({
                    title: 'Error',
                    msg: result.errorMsg
                });
            } else {
                $('#wdtxdlg').dialog('close');        // close the dialog
                searchWdtx();
            }
        }
    });
}
/*删除我的提醒*/
function destroyWdtx(){
	var rows = $('#wdtx').datagrid('getSelections');
	/*前端验证，只能删除我发布的提醒，不能删除别人发布的提醒*/
//	var fbr = $('#fbr').combobox('getValue');
	//if(fbr!=dlzh){
	//   $.messager.alert('提示框','只能删除你自己发布的提醒','info');
	//   return;
	//}
	if(rows.length==0){//沒有选择数据提示应该选择数据
		 $.messager.alert('提示框','请选择数据记录','info');
		 return;
	 }
	var wt_idList = new Array();
	var flag = 0;
	$.each(rows, function (i, n) {			
		wt_idList[i] = n.wt_id;
		if(n.fbr!=dlzh){
			flag = 1;
			 $.messager.alert('提示框','第'+(i+1)+'条数据不是你发布的不能删除','info');
			 return;
		}
	});  
	if(flag==1){
//		 $.messager.alert('提示框','第'+(i+1)+'条数据不是你发布的不能删除','info');
		 return;
	}
	if (rows.length!=0){
	    $.messager.confirm('确认框','你确定要删除用户信息吗?',function(r){
	        if (r){
	            $.post('/ENOS/wdtx/delete?', {wt_idList: JSON.stringify(wt_idList),fbr:dlzh} ,function(result){                        	 
	                if (result){ 
	                	$.messager.alert('提示框', result.wdtxmsg,'info');
	                	searchWdtx();                    
	                } else {
	                    $.messager.show({    // show error message
	                        title: '删除数据时出错!',
	                        msg: "删除失败"
	                    });
	                }
	            },'json');
	            }
	        });
	    }
}
/*ajax请求用户名*/
function getUserName(){
	 $.ajax({
         type:'post',
         url:'/ENOS/wdtx/getUserName',
         async:true,
         success: function (data) {
        	 alert( $('input[name="zdr"]').val());
//        	 $('input[name="zdr"]').combobox('setValues',data);
    	     $('#zdr').combobox('loadData',data);  
         },
         error: function () {
        	 $.messager.alert('提示框','查询用户名失败请联系管理员','info');
         }
     });		 
}


