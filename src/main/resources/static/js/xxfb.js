/**
 * 
 */
var url;
var dlzh = $('.dlzh').html();
$('#xxfbli').on('click',function(){
	/*启用layui 时间控件*/
	layui.use('laydate', function() {
		var laydate = layui.laydate;
		//执行一个laydate实例
		laydate.render({
			elem : '#fbrq' //指定元素
//			type: 'datetime'
		});
	});
	var pageNumber=$('#xxfb').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#xxfb').datagrid('getPager').data("pagination").options.pageSize;
	var xxfbfield = $('input[name="xxfbfield"]').val();
	pageActionXxfb(pageNumber,pageSize,xxfbfield);
    var pg = $("#xxfb").datagrid("getPager");
    if(pg)
    {
       $(pg).pagination({
	        onBeforeRefresh:function(){
	    	},
           onRefresh:function(pageNumber,pageSize){
        	   pageActionXxfb(pageNumber,pageSize,xxfbfield);
            },
           onChangePageSize:function(){
            },
           onSelectPage:function(pageNumber,pageSize){
        	   pageActionXxfb(pageNumber,pageSize,xxfbfield);
            }
       });
    }
 
});
function pageActionXxfb(pageNumber,pageSize,xxfbfield){
	var page = 0;
	if(pageNumber!=0){
		page = pageNumber-1;
	}
    var url="/ENOS/xxfb/find?page="+page+"&size="+pageSize+"&field="+xxfbfield+""
    $('#xxfb').datagrid('options').url = url;
    $("#xxfb").datagrid('reload'); 
}
/*查询信息发布*/
function  searchXxfb(){
	var pageNumber=$('#xxfb').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#xxfb').datagrid('getPager').data("pagination").options.pageSize;
	var xxfbfield = $('input[name="xxfbfield"]').val();
	pageActionXxfb(pageNumber,pageSize,xxfbfield); 
}
/*新建信息发布*/
function newXxfb(){
    $('#xxfbdlg').dialog('open').dialog('center').dialog('setTitle','新建信息发布');
    $('#xxfbfm').form('clear');
    /*设置发布人*/
    $('#ggfbr').combobox('setValue', dlzh);
    url = "/ENOS/xxfb/save";
}
 
/*编辑信息发布*/
function editXxfb(){ 
	var rows = $('#xxfb').datagrid('getSelections');
    if(rows.length==0){//沒有选择数据提示应该选择数据
	   	 $.messager.alert('提示框','请选择数据记录','info');
	   	 return;
    }
    if(rows.length>1){
    	 $.messager.alert('提示框','每次只能修改一条数据','info');
	   	 return;
    }
//    var dd = rows[0].ggfj;
//    $('input[name="ggfjsc"]').val(rows[0].ggfj)//给附件框赋值
    if (rows){
        $('#xxfbdlg').dialog('open').dialog('center').dialog('setTitle','修改信息发布');
        $('#xxfbfm').form('load',rows[0]);
        url = "/ENOS/xxfb/save?xf_id="+rows[0].xf_id;
    }
}
/*保存我的提醒*/
function saveXxfb(){
	
	if($('#ggfbr').combobox('getValue')!=dlzh){
		 $.messager.alert('提示框','发布人应选择当前登录人','info');
		 return;
	}
    $('#xxfbfm').form('submit',{
        url:url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            var result = eval('('+result+')');
            $.messager.alert('提示框',result.xxfbmsg,'info');
            if (result.errorMsg){
                $.messager.show({
                    title: 'Error',
                    msg: result.errorMsg
                });
            } else {
                $('#xxfbdlg').dialog('close');        // close the dialog
                searchXxfb();
            }
        }
    });
}
/*删除信息发布*/
function destroyXxfb(){
	var rows = $('#xxfb').datagrid('getSelections');
	if(rows.length==0){//沒有选择数据提示应该选择数据
		 $.messager.alert('提示框','请选择数据记录','info');
		 return;
	 }
	var xf_idList = new Array();
	var flag = 0;
	$.each(rows, function (i, n) {			
		xf_idList[i] = n.xf_id;
		if(n.ggfbr!=dlzh){
			flag = 1;
			 $.messager.alert('提示框','第'+(i+1)+'条数据不是你发布的不能删除','info');
			 return;
		}
	});  
	if(flag==1){
		 return;
	}
	if (rows.length!=0){
	    $.messager.confirm('确认框','你确定要删除用户信息吗?',function(r){
	        if (r){
	            $.post('/ENOS/xxfb/delete?', {xf_idList: JSON.stringify(xf_idList),ggfbr:dlzh} ,function(result){                        	 
	                if (result){ 
	                	$.messager.alert('提示框', result.xxfbmsg,'info');
	                	searchXxfb();                    
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
/*附件下载*/
function downXxfbfj(){
	var rows = $('#xxfb').datagrid('getSelections');
	if(rows.length==0){//沒有选择数据提示应该选择数据
		  $('#xffjxz').attr('href','#');
		 $.messager.alert('提示框','请选择附件所在行','info');
		 return;
	 }
	if(rows.length>1){
     $('#xffjxz').attr('href','#');
   	 $.messager.alert('提示框','每次只能下载一个附件','info');
	 return;
   }
   if(rows[0].ggfj==null){
	     $('#xffjxz').attr('href','#');
	   	 $.messager.alert('提示框','该记录没有附件可供下载','info');
	   	 return;
   }else{
	   var fjdownxxfb = rows[0].ggfj;
		fjdownxxfb = fjdownxxfb.replace(/\\/g,"/");//将\转为/
		$('#xffjxz').attr('href','/ENOS/xxfb/download?fjml='+fjdownxxfb+''); 
   }
	 
}




