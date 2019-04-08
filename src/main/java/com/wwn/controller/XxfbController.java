package com.wwn.controller;
 
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wwn.jpa.dao.XxfbRepository;
import com.wwn.jpa.entity.UserInfo;
import com.wwn.jpa.entity.Xxfb;
import com.wwn.service.XxfbService;

@RestController
@RequestMapping("/xxfb")
public class XxfbController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired  
	private XxfbService xxfbService;
	@Autowired  
	private XxfbRepository xxfbRepository;
	 
	@RequestMapping("/find")
	public Map<String ,Object> find(Integer page ,Integer size,String field ) {
	    
		logger.info("XxfbController ->find begin");
		Map< String,Object> map = new HashMap<String, Object>();
		Page<Xxfb> Xxfb = xxfbService.find(page, size, field);
		map.put("total", Xxfb.getTotalElements());
		map.put("rows", Xxfb.getContent());
		return map;
	}
	 
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Map<String ,Object> deleteXxfb(HttpServletRequest req) {
	    logger.info("XxfbController ->delete begin");
	    Map< String,Object> map = new HashMap<String, Object>();
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
        UserInfo uf= (UserInfo) session.getAttribute("userInfo");
		String xf_idList =  req.getParameter("xf_idList");
		String ggfbr =  req.getParameter("ggfbr");
		String name = uf.getName();
		if(xf_idList==null||ggfbr==null) {
			logger.info("请输入xf_id号和发布人");
			map.put("xxfbmsg", "请输入xf_id号和发布人");
			return map;
		}
		if(!name.equals(ggfbr)) {
			logger.info("只能删除你自己发布的提醒");
			map.put("xxfbmsg", "只能删除你自己发布的提醒");
			return map;
		}
		List<Integer> wtidIntArr  = new ArrayList<Integer>();
		String[] wtidStrArr = xf_idList.substring(1, xf_idList.length()-1).split(",");
		for (int i = 0; i < wtidStrArr.length; i++) {
			wtidIntArr.add(Integer.parseInt(wtidStrArr[i]));
		}
		Integer delete;
		try {
			delete = xxfbService.deleteXxfb(wtidIntArr,ggfbr);
			map.put("xxfbmsg", "已删除"+delete+"数据");
			return map;
		} catch (Exception e) {
			map.put("xxfbmsg", "发布信息删除出错,请联系管理员!");
			e.printStackTrace();
			return map;
		}
		
	}
	/*
	 * 保存或者更新信息发布
	 * data 2019/03/29
	 * 吴万能
	 * return Map<String, Object>
	 * 
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String,Object> save(Xxfb Xxfb ,HttpServletRequest req ,
		                        	@RequestParam(name="ggfjsc",required=false) MultipartFile file) {
		
		logger.info("XxfbController ->savePerson begin");
		Map<String, Object> map = new HashMap<>();
	    File dest = null;
	    Integer xf_id = Xxfb.getXf_id(); 
	    if(xf_id!=null) {//xf_id不为空，说明是修改操作，可能涉及到上传附件或者不上传附件
	    	//需要从新上传附件 dbfj 数据库中的附件
	    	String dbfj = xxfbRepository.findFj(xf_id);
	    	if(!file.isEmpty()&&!dbfj.equals(file)) {
	    		dest = uploadUtil( file);
	    	}else {
	    		Xxfb.setGgfj(dbfj);//设置附件路径与数据库保持一致
	    	}
	    }else {//新增
	    	dest = uploadUtil( file);
	    }
	    HttpSession session=req.getSession();//获取session 中当前登录的用户的信息
	    UserInfo uf= (UserInfo) session.getAttribute("userInfo");
	    String name = uf.getName();
	    String ggfbr = Xxfb.getGgfbr();
	    if(!name.equals(ggfbr)) {
			logger.info("发布人应选择当前登录人");
			map.put("xxfbmsg", "发布人应选择当前登录人");
			return map;
		}
	    if(dest!=null)
	       Xxfb.setGgfj(dest.toString());//设置附件路劲
		if(xf_id!=null)//如果是修改可以传一个id 过来，save 能自动检测是该新增还是修改
			Xxfb.setXf_id(xf_id);
	    map = xxfbService.save(Xxfb);
		return map; 
	}
	 
    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
    
    /**
	 * <p> 附件上传</p>    
	 * @param request
	 * @param response
	 * @return Map
	 * @author wwn 
	 * @date 2019年03月29日 
	 *  
	 */
    @RequestMapping("/fileUpload")
    public Map<String, Object> fileUpload(@RequestParam("ggfjsc") MultipartFile file){
    	
    	Map<String, Object> map = new HashMap<>();
    	if(file.isEmpty()){
    		map.put("xxfbfjmsg",  "请选择文件");
            return map;
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);
        
        String path = "c:/fileUpload" ;
        File dest = new File(path + "/" + fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
        	map.put("xxfbfjmsg",  "文件上传成功");
            return map;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            map.put("xxfbfjmsg",  "文件上传失败");
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("xxfbfjmsg",  "文件上传失败");
            return map;
        }
    }


	/**
	 * <p> 附件下载</p>    
	 * @param request
	 * @param response
	 * @return Map
	 * @author wwn 
	 * @date 2019年03月29日 
	 *  
	 */
	 @RequestMapping(value="/download",method=RequestMethod.GET) //匹配的是href中的download请求
     public ResponseEntity<byte[]> download(HttpServletRequest request ,@RequestParam(value="fjml",required=false) String fjml ) throws IOException{
        
    	logger.info("XxfbController ->download begin"); 
    	if(fjml==null||fjml.equals(""))
    		return null;
        File file = new File(fjml);//新建一个文件
        HttpHeaders headers = new HttpHeaders();//http头信息
        String downloadFileName = new String(fjml.getBytes("UTF-8"),"iso-8859-1");//设置编码
        headers.setContentDispositionFormData("attachment", downloadFileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity<byte[]> responseEntity;
		try {
			responseEntity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);
			 return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
       
    }
     
    /*上传附件的工具类
     * date 2019/03/29
     * return Map<String,Object>
     * */
     public File uploadUtil(MultipartFile file ){
       
        logger.info("XxfbController ->uploadUtil begin");
        File dest = null; 
     	if(file.isEmpty()){
     		 logger.info("上传文件为空");
             return dest;
         }
         String fileName = file.getOriginalFilename();
         int size = (int) file.getSize();
         System.out.println(fileName + "-->" + size);
         
         String path = "c:/fileUpload" ;
         dest = new File(path + "/" + fileName);
         if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
             dest.getParentFile().mkdir();
         }
         try {
             file.transferTo(dest); //保存文件
             logger.info(" 上传文件成功");
             return dest;
         } catch (IllegalStateException e) {
             e.printStackTrace();
             logger.info(" 文件上传失败");
             return dest;
         } catch (IOException e) {
             e.printStackTrace();
             logger.info(" 文件上传失败");
             return dest;
         }
     }
}
