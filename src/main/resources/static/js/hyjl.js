/**
 * 
 */
var url;
var dlzh = $('.dlzh').html();
$('#hyjlli').on('click',function(){
//	/*启用layui 时间控件*/
//	layui.use('laydate', function() {
//		var laydate = layui.laydate;
//		laydate.render({
//			elem : '#hyrq'//指定元素
//		});
//		var laydate2 = layui.laydate;
//		laydate2.render({
//			elem : '#hyrqcx', //指定元素
//		});
//	});
	var pageNumber=$('#hyjl').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#hyjl').datagrid('getPager').data("pagination").options.pageSize;
	pageActionhyjl(pageNumber,pageSize);
    var pg = $("#hyjl").datagrid("getPager");
    if(pg)
    {
    	 $(pg).pagination({
 	        onBeforeRefresh:function(){
 	    	},
            onRefresh:function(pageNumber,pageSize){
            	pageActionhyjl(pageNumber,pageSize);
             },
            onChangePageSize:function(){
             },
            onSelectPage:function(pageNumber,pageSize){
            	pageActionhyjl(pageNumber,pageSize);
             }
        });
    }
 
});
function pageActionhyjl(pageNumber,pageSize){
	var page = 0;
	if(pageNumber!=0){
		page = pageNumber-1;
	}
    var url="/ENOS/hyjl/find?page="+page+"&size="+pageSize+""
    $('#hyjl').datagrid('options').url = url;
    $("#hyjl").datagrid('reload'); 
}
/*查询会议记录*/
function  searchhyjl(){
	var pageNumber=$('#hyjl').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#hyjl').datagrid('getPager').data("pagination").options.pageSize;
//	var hyjlfield = $('input[name="hyjlfield"]').val();
	pageActionhyjl(pageNumber,pageSize); 
}
/*新建信息发布*/
function newhyjl(){
    $('#hyjldlg').dialog('open').dialog('center').dialog('setTitle','新建会议记录');
    $('#hyjlfm').form('clear');
    $("#savehyjl"). removeClass("savedisplay");//让保存按钮显示
    /*设置发布人*/
    $('#hyjlrxm').combobox('setValue', dlzh);
    url = "/ENOS/hyjl/save";
}
 
/*编辑会议记录*/
function edithyjl(){ 
	var rows = $('#hyjl').datagrid('getSelections');
    if(rows.length==0){//沒有选择数据提示应该选择数据
	   	 $.messager.alert('提示框','请选择数据记录','info');
	   	 return;
    }
    if(rows.length>1){
    	 $.messager.alert('提示框','每次只能修改一条数据','info');
	   	 return;
    }
    if (rows){
        $('#hyjldlg').dialog('open').dialog('center').dialog('setTitle','修改会议记录');
        $('#hyjlfm').form('load',rows[0]);
        $("#savehyjl"). removeClass("savedisplay");//让保存按钮显示
//        $('#hyjlrxm').combobox('setValue', dlzh);
        url = "/ENOS/hyjl/save?hj_id="+rows[0].hj_id;
    }
}

/*留言会议记录*/
function lyhyjl(){ 
	var rows = $('#hyjl').datagrid('getSelections');
    if(rows.length==0){//沒有选择数据提示应该选择数据
	   	 $.messager.alert('提示框','请选择数据记录','info');
	   	 return;
    }
    if(rows.length>1){
    	 $.messager.alert('提示框','每次只能修改一条数据','info');
	   	 return;
    }
    if (rows){
        $('#hyjllydlg').dialog('open').dialog('center').dialog('setTitle','留言');
        $('#hyjllyfm').form('load',rows[0]);
        $("#savelyhyjl"). removeClass("savedisplay");//让保存按钮显示
        $('#hylyrxm').textbox('setValue', dlzh);
        url = "/ENOS/hyjl/liuyan?hj_id="+rows[0].hj_id;
    }
}

/*保存会议记录*/
function savehyjl(fm,dlg){
	
//	if($('input[name="hyrq"]').val()==""){
//		 $.messager.alert('提示框','请填写日期','info');
//		 return;
//	}
//	if($('#hyjlrxm').combobox('getValue')!=dlzh){
//		 $.messager.alert('提示框','记录人应选择当前登录人','info');
//		 return;
//	}
    $(fm).form('submit',{
        url:url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            var result = eval('('+result+')');
            $.messager.alert('提示框',result.hyjlmsg,'info');
            if (result.errorMsg){
                $.messager.show({
                    title: 'Error',
                    msg: result.errorMsg
                });
            } else {
                $(dlg).dialog('close');        // close the dialog
                searchhyjl();
            }
        }
    });
}
 
/*查询表单重置*/
$('.hyjlcz').on('click',function(){
	 $('#hybmmccx').combobox('setValue', "");//会议项目名称查询
	 $('#hyxmmccx').combobox('setValue', "");
	 $('#hyjlrxmcx').combobox('setValue', "");
	 $('#hycyrycx').textbox('setValue', "");
	 $('#hyytcx').textbox('setValue', "");
	 $('#hyrqcx').datebox('setValue', "");
	 $(".laydate-btns-clear").trigger("click")
	 searchhyjl();
});

/*表单查询*/
$('.cxhyjl').on('click',function(){
	
	 var formData  = new FormData();
	 var pageNumber=$('#hyjl').datagrid('getPager').data("pagination").options.pageNumber;
     var pageSize=$('#hyjl').datagrid('getPager').data("pagination").options.pageSize;
     var page = 0;
 	 if(pageNumber!=0){
 		page = pageNumber-1;
 	 }
     
	 var hybmmccx =  $('#hybmmccx').combobox('getValue' );//会议项目名称查询
	 var hyxmmccx = $('#hyxmmccx').combobox('getValue' );
	 var hyjlrxmcx = $('#hyjlrxmcx').combobox('getValue' );
	 var hycyrycx = $('#hycyrycx').combobox('getValue' );
	 var hyytcx = $('#hyytcx').textbox('getValue' );
	 var hyrqcx = $('#hyrqcx').datebox('getValue');
	 
	 formData.append("hybmmccx",hybmmccx);
  	 formData.append("hyxmmccx",hyxmmccx);
     formData.append("hyjlrxmcx",hyjlrxmcx);
  	 formData.append("hycyrycx",hycyrycx);
  	 formData.append("hyytcx",hyytcx);
  	 formData.append("hyrqcx",hyrqcx);
   	 
   	 $.ajax({
         type:'post',
         url:"/ENOS/hyjl/find?page="+page+"&size="+pageSize+"",
         processData: false,
         contentType: false,
         async:false,
         data:formData,
         success: function (data) {
        	   $("#hyjl").datagrid('loadData',data); 
         },
         error: function ( ) {
        		$.messager.alert('提示框',"查询会议记录出错",'info');
          }
      });
  	 
});
/*查看会议记录*/
//function chakan(){
//	var rows = $('#hyjl').datagrid('getSelections');
//    if(rows.length==0){//沒有选择数据提示应该选择数据
//	   	 $.messager.alert('提示框','请选择数据记录','info');
//	   	 return;
//    }
//    if(rows.length>1){
//    	 $.messager.alert('提示框','每次只能查看一条数据','info');
//	   	 return;
//    }
//    if (rows){
//    	$("#savehyjl").addClass("savedisplay");
////    	 $("#sjr").attr("readOnly",true);
//        $('#hyjldlg').dialog('open').dialog('center').dialog('setTitle','查看会议记录');
//        $('#hyjlfm').form('load',rows[0]);
//    }
//}


















