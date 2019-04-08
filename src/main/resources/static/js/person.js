/**
 * 
 */
var url;
$(function(){
	var pageNumber=$('#dg').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#dg').datagrid('getPager').data("pagination").options.pageSize;
	pageAction(pageNumber,pageSize);
    var pg = $("#dg").datagrid("getPager");
    if(pg)
    {
       $(pg).pagination({
           onBeforeRefresh:function(){
//               alert('before refresh');
//        	   pageSize = pageSize+1;
//        	   $(this).pagination('loading'); 
    	},
           onRefresh:function(pageNumber,pageSize){
        	   pageAction(pageNumber,pageSize);
            },
           onChangePageSize:function(){
//               alert('pagesize changed');
            },
           onSelectPage:function(pageNumber,pageSize){
        	   pageAction(pageNumber,pageSize);
            }
       });
    }

});
function pageAction(pageNumber,pageSize){
    var url="/ENOS/person/findPersonByPage?page="+(pageNumber-1)+"&size="+pageSize+""
    $('#dg').datagrid('options').url = url;
    $("#dg").datagrid('reload'); 
}


function newUser(){
    $('#dlg').dialog('open').dialog('center').dialog('setTitle','新建用户');
    $('#fm').form('clear');
    url = "/ENOS/person/savePerson";
}
function editUser(){
    var row = $('#dg').datagrid('getSelected');
    if(row==null){//沒有选择数据提示应该选择数据
	   	 $.messager.alert('提示框','请选择数据记录','info');
	   	 return;
    }
    if (row){
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','修改用户');
        $('#fm').form('load',row);
        url = "/ENOS/person/savePerson?pid="+row.pid;
    }
}
function saveUser(){
    $('#fm').form('submit',{
        url:url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            var result = eval('('+result+')');
            if (result.errorMsg){
                $.messager.show({
                    title: 'Error',
                    msg: result.errorMsg
                });
            } else {
                $('#dlg').dialog('close');        // close the dialog
                searchUser();
            }
        }
    });
}
function destroyPerson(){
    var rows = $('#dg').datagrid('getSelections');
     if(rows.length==0){//沒有选择数据提示应该选择数据
    	 $.messager.alert('提示框','请选择数据记录','info');
    	 return;
     }
	var pidList = new Array();
	$.each(rows, function (i, n) {			
		pidList[i] = n.pid;
	});      
    if (rows.length!=0){
        $.messager.confirm('确认框','你确定要删除用户信息吗?',function(r){
            if (r){
                $.post('person/deletePerson?', {pidList: JSON.stringify(pidList)} ,function(result){                        	 
                    if (result){ 
                    	$.messager.alert('提示框','已经删除了'+rows.length+'数据','info');
                    	searchUser();                    
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
    
function  searchUser(){
	var pageNumber=$('#dg').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#dg').datagrid('getPager').data("pagination").options.pageSize;
	pageAction(pageNumber,pageSize); 
}
function  searchPerson(){
	var pageNumber=$('#dg').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#dg').datagrid('getPager').data("pagination").options.pageSize;
	var field = $('input[name="field"]').val();
//	if(field==""){
//		$.messager.alert('提示框','请输入查询条件','info');
//		return;
//	}
	var url="/ENOS/person/searchPerson?field="+field+ "&page="+(pageNumber-1)+"&size="+pageSize+"";
    $('#dg').datagrid('options').url = url;
    $("#dg").datagrid('reload');
//	$.get("/ENOS/person/searchPerson", { field:field },function(data){
//		 if (data){ 
//         	searchUser();                    
//         } else {
//             $.messager.show({    // show error message
//                 title: '删除数据时出错!',
//                 msg: "删除失败"
//             });
//         } 
//    });
}



