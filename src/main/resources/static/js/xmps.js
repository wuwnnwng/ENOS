/**
 * 
 */
var url ;
var dlzh = $('.dlzh').html();
$('#xmpsli').on('click',function(){
	/*启用layui 时间控件*/
	layui.use('laydate', function() {
		var laydate = layui.laydate;
		laydate.render({
			elem : '#xprq'//指定元素
		});
		var laydate2 = layui.laydate;
		laydate2.render({
			elem : '#xprqcx', //指定元素
		});
	});
	var pageNumber=$('#xmps').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#xmps').datagrid('getPager').data("pagination").options.pageSize;
	pageActionxmps(pageNumber,pageSize );
    var pg = $("#xmps").datagrid("getPager");
    if(pg)
    {
    	 $(pg).pagination({
 	        onBeforeRefresh:function(){
 	    	},
            onRefresh:function(pageNumber,pageSize ){
            	pageActionxmps(pageNumber,pageSize );
             },
            onChangePageSize:function(){
             },
            onSelectPage:function(pageNumber,pageSize){
            	pageActionxmps(pageNumber,pageSize );
             }
        });
    }
 
});
function pageActionxmps(pageNumber,pageSize ){
	var page = 0;
	if(pageNumber!=0){
		page = pageNumber-1;
	}
    var url="/ENOS/xmps/find?page="+page+"&size="+pageSize +""
    $('#xmps').datagrid('options').url = url;
    $("#xmps").datagrid('reload'); 
}
/*查询工作记录*/
function  searchxmps(){
	var pageNumber=$('#xmps').datagrid('getPager').data("pagination").options.pageNumber;
	var pageSize=$('#xmps').datagrid('getPager').data("pagination").options.pageSize;
	pageActionxmps(pageNumber,pageSize ); 
}
/*新建信息发布*/
function newxmps(){
    $('#xmpsdlg').dialog('open').dialog('center').dialog('setTitle','新建项目评审');
    $('#xmpsfm').form('clear');
    $("#savexmps"). removeClass("savedisplay");//让保存按钮显示
    /*设置发布人*/
    $('#xpr').combobox('setValue', dlzh);
    url = "/ENOS/xmps/save";
}
 
/*编辑工作记录*/
function editxmps(){ 
	var rows = $('#xmps').datagrid('getSelections');
    if(rows.length==0){//沒有选择数据提示应该选择数据
	   	 $.messager.alert('提示框','请选择数据记录','info');
	   	 return;
    }
    if(rows.length>1){
    	 $.messager.alert('提示框','每次只能修改一条数据','info');
	   	 return;
    }
//    if($('#xpr').combobox('getValue')!=dlzh){
//		 $.messager.alert('提示框','只能对自己发布信息进行修改','info');
//		 return;
//	}
    if (rows){
        $('#xmpsdlg').dialog('open').dialog('center').dialog('setTitle','修改项目评审');
        $('#xmpsfm').form('load',rows[0]);
        $("#savexmps"). removeClass("savedisplay");//让保存按钮显示
//        $('#xmpsrxm').combobox('setValue', dlzh);
        url = "/ENOS/xmps/save?xp_id="+rows[0].xp_id;
    }
}
/*保存工作记录*/
function savexmps(){
	
	if($('input[name="xprq"]').val()==""){
		 $.messager.alert('提示框','请填写日期','info');
		 return;
	}
	if($('#xpr').combobox('getValue')!=dlzh){
		 $.messager.alert('提示框','指派人应选择当前登录人','info');
		 return;
	}
//	if($('#xpr').combobox('getValue')==$('#rqcxxm').combobox('getValue')){
//		 $.messager.alert('提示框','指派人和参项人不能是同一个人','info');
//		 return;
//	}
	
    $('#xmpsfm').form('submit',{
        url:url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            var result = eval('('+result+')');
            $.messager.alert('提示框',result.xmpsmsg,'info');
            if (result.errorMsg){
                $.messager.show({
                    title: 'Error',
                    msg: result.errorMsg
                });
            } else {
                $('#xmpsdlg').dialog('close');        // close the dialog
                searchxmps();
            }
        }
    });
}
 
/*查询表单重置*/
$('.xmpscz').on('click',function(){
	 $('#xpxmmccx').combobox('setValue', ""); 
	 $('#xprcx').combobox('setValue', ""); 
	 searchxmps();
});

/*表单查询*/
$('.cxxmps').on('click',function(){
	
	 var formData  = new FormData();
	 var pageNumber=$('#xmps').datagrid('getPager').data("pagination").options.pageNumber;
     var pageSize=$('#xmps').datagrid('getPager').data("pagination").options.pageSize;
     var page = 0;
 	 if(pageNumber!=0){
 		page = pageNumber-1;
 	 }
     
	 var xpxmmccx =  $('#xpxmmccx').combobox('getValue' ); 
	 var xprcx =  $('#xprcx').combobox('getValue' ); 
	 var xprqcx = $('#xprqcx').val();
	 
	 formData.append("xpxmmc",xpxmmccx);
  	 formData.append("xpr",xprcx);
  	 formData.append("xprq",xprqcx);
  	  
   	 
   	 $.ajax({
         type:'post',
         url:"/ENOS/xmps/find?page="+page+"&size="+pageSize+ "",
         processData: false,
         contentType: false,
         async:false,
         data:formData,
         success: function (data) {
        	   $("#xmps").datagrid('loadData',data); 
         },
         error: function ( ) {
        		$.messager.alert('提示框',"查询项目评审出错",'info');
          }
      });
  	 
});

 












