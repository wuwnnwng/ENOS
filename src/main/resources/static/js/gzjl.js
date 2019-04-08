/**
 * 
 */
var url;
var dlzh = $('.dlzh').html();
$('#gzjlli').on('click',function(){
	/*启用layui 时间控件*/
//	layui.use('laydate', function() {
//		var laydate = layui.laydate;
//		//执行一个laydate实例
//		laydate.render({
//			elem : '#gzrq' //指定元素
////			type: 'datetime'
//		});
//	});
	var pageNumber=$('#gzjl').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#gzjl').datagrid('getPager').data("pagination").options.pageSize;
//	var gzjlfield = $('input[name="gzjlfield"]').val();
	pageActiongzjl(pageNumber,pageSize);
    var pg = $("#gzjl").datagrid("getPager");
    if(pg)
    {
    	 $(pg).pagination({
 	        onBeforeRefresh:function(){
 	    	},
            onRefresh:function(pageNumber,pageSize){
            	pageActiongzjl(pageNumber,pageSize);
             },
            onChangePageSize:function(){
             },
            onSelectPage:function(pageNumber,pageSize){
            	pageActiongzjl(pageNumber,pageSize);
             }
        });
    }
 
});
function pageActiongzjl(pageNumber,pageSize){
	var page = 0;
	if(pageNumber!=0){
		page = pageNumber-1;
	}
    var url="/ENOS/gzjl/find?page="+page+"&size="+pageSize+""
    $('#gzjl').datagrid('options').url = url;
    $("#gzjl").datagrid('reload'); 
}
/*查询工作记录*/
function  searchGzjl(){
	var pageNumber=$('#gzjl').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#gzjl').datagrid('getPager').data("pagination").options.pageSize;
//	var gzjlfield = $('input[name="gzjlfield"]').val();
	pageActiongzjl(pageNumber,pageSize); 
}
/*新建工作记录*/
function newGzjl(){
    $('#gzjldlg').dialog('open').dialog('center').dialog('setTitle','新建工作记录');
    $('#gzjlfm').form('clear');
    $("#saveGzjl"). removeClass("savedisplay");//让保存按钮显示
    /*设置发布人*/
    $('#jlrxm').combobox('setValue', dlzh);
    url = "/ENOS/gzjl/save";
}

/*审核工作记录*/
function shGzjl(){
	var rows = $('#gzjl').datagrid('getSelections');
    if(rows.length==0){//沒有选择数据提示应该选择数据
	   	 $.messager.alert('提示框','请选择数据记录','info');
	   	 return;
    }
    if(rows.length>1){
    	 $.messager.alert('提示框','每次只能修改一条数据','info');
	   	 return;
    }
    if (rows){
        $('#gzjlshdlg').dialog('open').dialog('center').dialog('setTitle','审核工作记录');
        $('#gzjlshfm').form('load',rows[0]);
        $("#saveshGzjl"). removeClass("savedisplay");//让保存按钮显示
        $('#psrxm').textbox('setValue', dlzh);//设置评审人为登录人
        url = "/ENOS/gzjl/shenhe?gj_id="+rows[0].gj_id;
    }
}
 
/*编辑工作记录*/
function editGzjl(){ 
	var rows = $('#gzjl').datagrid('getSelections');
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
        $('#gzjldlg').dialog('open').dialog('center').dialog('setTitle','修改工作记录');
        $('#gzjlfm').form('load',rows[0]);
        $("#saveGzjl"). removeClass("savedisplay");//让保存按钮显示
        url = "/ENOS/gzjl/save?gj_id="+rows[0].gj_id;
    }
}
/*保存工作记录*/
function saveGzjl(fm,dlg){
	
//	if($('input[name="gzrq"]').val()==""){
//		 $.messager.alert('提示框','请填写日期','info');
//		 return;
//	}
//	if($('#jlrxm').combobox('getValue')!=dlzh){
//		 $.messager.alert('提示框','创建人应选择当前登录人','info');
//		 return;
//	}
    $(fm).form('submit',{
        url:url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            var result = eval('('+result+')');
            $.messager.alert('提示框',result.gzjlmsg,'info');
            if (result.errorMsg){
                $.messager.show({
                    title: 'Error',
                    msg: result.errorMsg
                });
            } else {
                $(dlg).dialog('close');        // close the dialog
                searchGzjl();
            }
        }
    });
}
 
/*查询表单重置*/
$('.gzjlcz').on('click',function(){
	 $('#bmmccx').combobox('setValue', "");
	 $('#xmmccx').combobox('setValue', "");
	 $('#jlrxmcx').combobox('setValue', "");
	 $('#gznrcx').textbox('setValue', "");
	 searchGzjl();
});

/*表单查询*/
$('.cxgzjl').on('click',function(){
	
	 var formData  = new FormData();
	 var pageNumber=$('#gzjl').datagrid('getPager').data("pagination").options.pageNumber;
     var pageSize=$('#gzjl').datagrid('getPager').data("pagination").options.pageSize;
     var page = 0;
 	 if(pageNumber!=0){
 		page = pageNumber-1;
 	 }
     
	 var bmmccx =  $('#bmmccx').combobox('getValue' );
	 var xmmccx = $('#xmmccx').combobox('getValue' );
	 var jlrxmcx = $('#jlrxmcx').combobox('getValue' );
	 var gznrcx = $('#gznrcx').textbox('getValue' );
	 
	 formData.append("bmmc",bmmccx);
  	 formData.append("xmmc",xmmccx);
     formData.append("jlrxm",jlrxmcx);
  	 formData.append("gznr",gznrcx);
//  	 formData.append("page",pageNumber);
//   	 formData.append("size",pageSize);
   	 
   	 $.ajax({
         type:'post',
         url:"/ENOS/gzjl/find?page="+page+"&size="+pageSize+"",
         processData: false,
         contentType: false,
         async:false,
         data:formData,
         success: function (data) {
//        	  $('#gzjl').datagrid('options').url = url;
        	   $("#gzjl").datagrid('loadData',data); 
         },
         error: function ( ) {
        		$.messager.alert('提示框',"查询工作记录出错",'info');
          }
      });
  	 
});
/*查看工作记录*/
//function chakanGzjl(){
//	var rows = $('#gzjl').datagrid('getSelections');
//    if(rows.length==0){//沒有选择数据提示应该选择数据
//	   	 $.messager.alert('提示框','请选择数据记录','info');
//	   	 return;
//    }
//    if(rows.length>1){
//    	 $.messager.alert('提示框','每次只能查看一条数据','info');
//	   	 return;
//    }
//    if (rows){
//    	$("#saveGzjl").addClass("savedisplay");
////    	 $("#sjr").attr("readOnly",true);
//        $('#gzjldlg').dialog('open').dialog('center').dialog('setTitle','查看工作记录');
//        $('#gzjlfm').form('load',rows[0]);
//    }
//}


















