//$(document).ready(function() {
// $('#yzxgmmform').bootstrapValidator({
//	 message: '验证失败',
//	 feedbackIcons: {
//	     valid: 'glyphicon glyphicon-ok',
//         invalid: 'glyphicon glyphicon-remove',
//         validating: 'glyphicon glyphicon-refresh'
//	   },
//	   fields: {
//		   username: {
//		       message: '用户名验证失败',
//	           validators: {
//	              notEmpty: {
//		             message: '用户名不能为空'
//		              }
//	           }       
//	    },
//	    userpassword: {
//	           validators: {
//	                 notEmpty: {
//	                   message: '密码不能为空'
//	                },
//	                identical: {
//	    	           field: 'confirmassword',
//	                   message: '两次密码不一致'
//	               }
//	               
//	           }
//	      },
//	      confirmPassword: {
//	           validators: {
//	              notEmpty: {
//	                message: '确认密码不能空'
//	              },
//	               identical: {
//	                 field: 'userpassword',
//	                 message: '两次密码不一致'
//	             }
//	       }
//      },
//	}  
// });
//});
$('input[name="password"]').blur(function(){ 
   var  password = $('input[name="password"]').val();
   if(password.length<6){
	   $("#checkma").html("密码不能少于6位!") 
   }else{
	   $("#checkma").html(""); 
	   var aa = $.base64.encode(password);
	  $('input[name="password"]').val($.base64.encode(password)); //对密码进行加密
   }
});
/*当点击忘记密码的时候可以弹出弹框*/
$("#wjmm").on('click',function(){
	$('#xgmm').window('open');
});
/*当点击注册的时候可以弹出弹框*/
$(".register").on('click',function(){
	$('#register').window('open');
});
//$(".quxiaoxiugai").on('click',function(){
//	$(".panel-tool-close").trigger("click")
//});

/*点击修改密码提交按钮进行表单验证和数据提交*/
$("#xgmmsubmit").on('click',function(){
	 var username = $('input[name="username"]').val();
	 var userpassword = $('input[name="userpassword"]').val();
	 var confirmassword = $('input[name="confirmassword"]').val();
	 if(username.length<=0||username==""){
		 $.messager.alert('提示框','请输入用户名','info');
		 return;
	 }
	 if(userpassword.length<6||confirmassword.length<6){
		 $.messager.alert('提示框','密码长度必须大于六位','info');
		 return;
	 }
	 if(userpassword!=confirmassword){
		 $.messager.alert('提示框','两次密码不一致','info');
		 return;
	 }
	 $.ajax({
         type:'post',
         url:'/ENOS/wjmm',
//         contentType:'application/json;charset=utf-8',
//         processData: false,
//         contentType: false,
         async:false,
         data:{username:username,userpassword:$.base64.encode(userpassword)},
         success: function (data) {
             if(data){
            	 $.messager.alert('提示框',data.xgmsg,'info');
//            	 $(".panel-tool-close").trigger("click")//提交成功自动关闭
             }else{
            	 $.messager.alert('提示框',data.xgmsg,'info');
             }
         },
         error: function () {
        	 $.messager.alert('提示框','修改密码出错,	请联系管理员','info');
         }
     });
		
//	 $.messager.alert('提示框','请选择数据记录','info');
});
 
/*注册表单验证*/
$("#zcsubmit").on('click',function(){
	/*
	 * 验证密码与确认密码是否一致
	 * */
   var  zcpassword = $('input[name="zcpassword"]').val();
   var  zcconfirmPassword = $('input[name="zcconfirmassword"]').val();
   if(zcpassword!=zcconfirmPassword){
	   $.messager.alert('提示框','两次密码不一致','info');
	   return false;
	   e.preventDefault();
   }else{
	   $('input[name="zcpassword"]').val($.base64.encode(zcpassword));
   }
	/*
	 * 验证手机号
	 * */
	   var  tel = $('input[name="phone"]').val();
	   if(!(/^1[34578]\d{9}$/.test(tel))){ 
		   $.messager.alert('提示框','请输入正确的手机号!','info');
		   return false;
		   e.preventDefault();
		} 
	/*
	 * 验证邮箱
	 * */
//   var  email = $('input[name="email"]').val();
//   if(!(/^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/.test(email))){ 
//	   $(".yzyx").html("") 
//	   $.messager.alert('提示框','请输入正确的邮箱!','info');
//	   return false;
//	   e.preventDefault();
//	} 
});

//$('#zcregister').form('submit',{
//    onSubmit:function(){
//        return $(this).form('enableValidation').form('validate');
//   }
//});
//function submitForm(){
//	url:'/ENOS/register',
//	$('#zcregister').form('submit',{
//		onSubmit:function(){
//			return $(this).form('enableValidation').form('validate');
//		}
//	});
//}













