
$(function(){//初始化全部隐藏
	$('.main').hide();
})
$('.navbar-nav').children().children().children().on('click',function(){
	  $('.main').hide();
	  var _id = $(this).parent().attr("id");
	  switch  (_id){
       case 'sydown'://主页
    	   $('#sy').show();
//    	   $("#wdtx").datagrid('reload'); 
//    	   $("#xxfb").datagrid('reload'); 
    	   $('#sy').children().siblings('').hide().eq($(this).index()).show();
           break;
       case 'gzgldown'://工作管理
    	   $('#gzgl').show();
    	   $('#gzgl').children().siblings('').hide().eq($(this).index()).show();
           break;
       case 'xmgldown'://项目管理
    	   $('#xmgl').show();
    	   $('#xmgl').children().siblings('').hide().eq($(this).index()).show();
           break;
       case 'zhgldown'://综合管理
    	   $('#zhgl').show();
    	   $('#zhgl').children().siblings('').hide().eq($(this).index()).show();
           break;
       case 'rsgldown'://人事管理 
    	   $('#rsgl').show();
    	   $('#rsgl').children().siblings('').hide().eq($(this).index()).show();
           break;
       case 'ywgldown'://客户管理
    	   $('#ywgl').show();
    	   $('#ywgl').children().siblings('').hide().eq($(this).index()).show();
           break;
     }
});
 
/*查看 */
function chakan(table,saveid,dlg,fm,chakanInfo){
	var rows = $(table).datagrid('getSelections');
    if(rows.length==0){//沒有选择数据提示应该选择数据
	   	 $.messager.alert('提示框','请选择数据记录','info');
	   	 return;
    }
    if(rows.length>1){
    	 $.messager.alert('提示框','每次只能查看一条数据','info');
	   	 return;
    }
    if (rows){
    	$(saveid).addClass("savedisplay");
        $(dlg).dialog('open').dialog('center').dialog('setTitle',chakanInfo);
        $(fm).form('load',rows[0]);
    }
}

/*删除*/
function destroy(table,name,id,delurl,fyurl){
	var rows = $(table).datagrid('getSelections');
	if(rows.length==0){//沒有选择数据提示应该选择数据
		 $.messager.alert('提示框','请选择数据记录','info');
		 return;
	 }
	var idList = new Array();
	var flag = 0;
	$.each(rows, function (i, n) {
		idList[i] = n[id];
		if(n[name]!=dlzh){
			flag = 1;
			 $.messager.alert('提示框','第'+(i+1)+'条数据不是你创建的不能删除','info');
			 return;
		}
	});  
	if(flag==1){
		 return;
	}
	if (rows.length!=0){
	    $.messager.confirm('确认框','你确定要删除吗?',function(r){
	        if (r){
	            $.post(delurl, {idList: JSON.stringify(idList),name:dlzh} ,function(result){                        	 
	                if (result){ 
	                	$.messager.alert('提示框', result.msg,'info');
	                	search(table,fyurl);                    
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

/*查询*/
function  search(table,fyurl){
	var pageNumber=$(table).datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$(table).datagrid('getPager').data("pagination").options.pageSize;
	pageActionMain(pageNumber,pageSize,table,fyurl); 
}
/*分页*/
function pageActionMain(pageNumber,pageSize,table,fyurl){
	var page = 0;
	if(pageNumber!=0){
		page = pageNumber-1;
	}
    var url=fyurl+"page="+page+"&size="+pageSize+"&dlzh="+dlzh+""
    $(table).datagrid('options').url = url;
    $(table).datagrid('reload'); 
}

/*编辑*/
//function edit(table,dlg,fm,saveurl,name,id,title){ 
//	var rows = $(table).datagrid('getSelections');
//	var editid = null;
//    if(rows.length==0){//沒有选择数据提示应该选择数据
//	   	 $.messager.alert('提示框','请选择数据记录','info');
//	   	 return;
//    }
//    if(rows.length>1){
//    	 $.messager.alert('提示框','每次只能修改一条数据','info');
//	   	 return;
//    }
//    
//    var flag = 0;
//    $.each(rows, function (i, n) {
//    	editid = n[id];;
//		if(n[name]!=dlzh){
//			flag = 1;
//			 $.messager.alert('提示框','第'+(i+1)+'条数据不是你创建的不能编辑','info');
//			 return;
//		}
//	}); 
//    if(flag==1){
//    	return;
//    }
//    
//    if (rows){
//        $(dlg).dialog('open').dialog('center').dialog('setTitle',title);
//        $(fm).form('load',rows[0]);
//        url = saveurl+"editid="+editid;
//    }
//}
//
///*新增*/
//function New(dlg,fm,name,url,title){
//    $(dlg).dialog('open').dialog('center').dialog('setTitle',title);
//    $(fm).form('clear');
//    /*设置发布人*/
//    $(name).combobox('setValue', dlzh);
//    var dd=url;
//    url = dd;
//}

/*确认*/
function queren(table,status,id,url,fyurl){
    var rows = $(table).datagrid('getSelections');
    if(rows.length==0){//沒有选择数据提示应该选择数据
	   	 $.messager.alert('提示框','请选择数据记录','info');
	   	 return;
    } 
    var qr_id_list = new Array();
    var flag = 0;
    $.each(rows, function (i, n) {	
    	
    	qr_id_list[i] = n[id];
    	if(n[status]=='已确认'){
    	   flag = 1;	
    	   $.messager.alert('提示框','第'+(i+1)+'条数据已经确认不能再次确认','info');
    	   return;
    	}
	}); 
    if(flag==1)
    	return;
    if (rows){
    	$.post(url, {qr_id_list: JSON.stringify(qr_id_list)} ,function(result){                        	 
            if (result){ 
            	$.messager.alert('提示框','已确认'+result.rows+'条数据!','info');
            	search(table,fyurl);          
            } else {
                $.messager.show({    // show error message
                    title: '确认状态时出错!',
                    msg: "确认失败"
                });
            }
        },'json');
    }
}

/*保存工作记录*/
function savegzly(table,fyurl){
	
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
            	search(table,fyurl); 
            }
        }
    });
} 
