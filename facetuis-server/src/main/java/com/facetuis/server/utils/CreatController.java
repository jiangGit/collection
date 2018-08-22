package com.facetuis.server.utils;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CreatController {
	public static void main(String[] args) {
		try {			
			List<String> classNames = GetBeanNameUtil.getClassName("com.facetuis.server.model.admin", false);
	        List<Class<?>> cla =new ArrayList<Class<?>>();
	        if (classNames != null) { 
	            for (String className : classNames) { 
	                cla.add(Class.forName(className));
	            } 
	        } 
	        File targetFile = new File("C:\\Users\\mayn\\Desktop/java/controller");
        	if(!targetFile.exists()){  
                targetFile.mkdirs();  
            } 
        	PrintStream ps;
	        for (Class<?> cc : cla) {		
    			ps = new PrintStream("C:/Users/mayn/Desktop/java/controller/"+cc.getSimpleName()+"Controller.java");
    			System.setOut(ps);//把创建的打印输出流赋给系统。即系统下次向 ps输出				
	        	getController(cc);//输出的内容
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void getController(Class<?> cc) {
		Field[] field = cc.getDeclaredFields();
	    String str=cc.getName().substring(0,cc.getName().lastIndexOf("."));
	    String str1=str.substring(0,str.lastIndexOf("."));
	    String str2=str1+".controller;";
	    System.out.println("package "+str2);
	    System.out.println(
	    		"import java.io.File;\r\n" + 
	    		"import java.io.FileNotFoundException;\r\n" + 
	    		"import java.io.FileOutputStream;\r\n" + 
	    		"import java.io.IOException;\r\n" + 
	    		"import java.io.OutputStream;\r\n" + 
	    		"import java.util.ArrayList;\r\n" + 
	    		"import java.util.Date;\r\n" + 
	    		"import java.util.List;\r\n" + 
	    		"\r\n" + 
	    		"import javax.annotation.Resource;\r\n" + 
	    		"import javax.servlet.http.HttpServletRequest;\r\n" + 
	    		"import javax.servlet.http.HttpServletResponse;\r\n" + 
	    		"\r\n" + 
	    		"import org.springframework.stereotype.Controller;\r\n" + 
	    		"import org.springframework.ui.ModelMap;\r\n" + 
	    		"import org.springframework.web.bind.annotation.RequestMapping;\r\n" + 
	    		"import org.springframework.web.bind.annotation.RequestParam;\r\n" + 
	    		"import org.springframework.web.multipart.MultipartFile;\r\n" + 
	    		"\r\n" + 
	    		"import jxl.Sheet;\r\n" + 
	    		"import jxl.Workbook;\r\n" + 
	    		"import jxl.write.Label;\r\n" + 
	    		"import jxl.write.WritableSheet;\r\n" + 
	    		"import jxl.write.WritableWorkbook;\r\n" + 
	    		"import jxl.write.WriteException;\r\n" + 
	    		"import jxl.write.biff.RowsExceededException;\r\n");
	    System.out.println(
	    		"@Controller\r\n" + 
	    		"@RequestMapping(\"/"+CreatUtil.getOneClass(cc.getSimpleName())+"\")\r\n" + 
	    		"public class "+cc.getSimpleName()+"Controller{ \r\n" + 
	    		"		@Resource\r\n" + 
	    		"		private "+cc.getSimpleName()+"Biz "+CreatUtil.toLowerOne(cc.getSimpleName())+"Biz;");
/**
 * 获取集合
 */
    	System.out.println("    /**\r\n" + 
    			"     * 获取集合\r\n" + 
    			"     */\r\n" + 
    			"    @RequestMapping(\"get"+cc.getSimpleName()+"List.do\")\r\n" + 
    			"    @RequiresPermissions({\"get"+cc.getSimpleName()+"List\"})\r\n"+
    			"    public String get"+cc.getSimpleName()+"List("+cc.getSimpleName()+" u,ModelMap m) {\r\n" + 
    			"	    //分页和排序\r\n" + 
    			"	    u.setIndex(u.getNowPage(), u.getRows());//设置分页参数	\r\n" + 
    			"	    int count="+CreatUtil.toLowerOne(cc.getSimpleName())+"Biz.get"+cc.getSimpleName()+"Count(u);//总条数\r\n" + 
    			"	    m.put(\"rows\", u.getRows());		\r\n" + 
    			"		m.put(\"nowPage\", u.getNowPage());\r\n" + 
    			"	    m.put(\"sumPage\", u.getSumPage(count));\r\n" + 
    			"		m.put(\"count\", count);\r\n" + 
    			"		m.put(\"sort\", u.getSort());\r\n" + 
    			"		m.put(\"sortVal\", u.getSortVal());\r\n" + 
    			"		//搜索用"); 
    			for (Field fe : field) {
    				if("java.lang.String".equals(fe.getType().getName())) {
    					System.out.println("		m.put(\""+fe.getName()+"\", u.get"+CreatUtil.toUpperOne(fe.getName())+"());");
    				}    				
    			}
    			System.out.println("		//以下搜索项不是string类型");
    			for (Field fe : field) {
    				if(!"java.lang.String".equals(fe.getType().getName())) {
    					System.out.println("		m.put(\""+fe.getName()+"\", u.get"+CreatUtil.toUpperOne(fe.getName())+"());");
    				}    				
    			}
    	System.out.println(" 		m.put(\"get"+cc.getSimpleName()+"List\","+CreatUtil.toLowerOne(cc.getSimpleName())+"Biz.get"+cc.getSimpleName()+"List(u));\r\n" + 
    			"		return \"get"+cc.getSimpleName()+"List\"; \r\n" + 
    			"    }");
/**
 * 跳转的页面
 */
    	System.out.println(
    			"    /**\r\n" + 
    			"     * 跳转至修改的页面\r\n" + 
    			"     */\r\n" + 
    			"    @RequestMapping(\"red"+cc.getSimpleName()+".do\")\r\n" + 
    			"    @RequiresPermissions({\"red"+cc.getSimpleName()+"\"})\r\n"+
    			"    public String redEcsGoods("+cc.getSimpleName()+" u,ModelMap m) {\r\n" + 
    			"	    if(u.getKeyword() != null && \"update\".equals(u.getKeyword())){\r\n" + 
    			"	    	m.put(\""+cc.getSimpleName()+"\", "+CreatUtil.toLowerOne(cc.getSimpleName())+"Biz.get"+cc.getSimpleName()+"One(u.get"+CreatUtil.toUpperOne(field[0].getName())+"()));\r\n"+
    			"	    }\r\n" + 
    			"	    return \"red"+cc.getSimpleName()+"\";\r\n" + 
    			"    }");
/**
 * 添加和修改    	
 */
    	System.out.println("    /**\r\n" + 
    			"     * 添加或修改\r\n" + 
    			"     */\r\n" + 
    			"    @RequestMapping(\"save"+cc.getSimpleName()+".do\")\r\n" + 
    			"    @RequiresPermissions({\"save"+cc.getSimpleName()+"\"})\r\n"+
    			"    public String addEcsGoods("+cc.getSimpleName()+" u) {\r\n" + 
    			"        if(u.getStringId().isEmpty()) {\r\n" + 
    			"        	"+CreatUtil.toLowerOne(cc.getSimpleName())+"Biz.add"+cc.getSimpleName()+"(u);\r\n" + 
    			"        }else {\r\n" + 
    			"        	"+CreatUtil.toLowerOne(cc.getSimpleName())+"Biz.update"+cc.getSimpleName()+"ById(u);\r\n" + 
    			"        }\r\n"+
    			"		return \"redirect:get"+cc.getSimpleName()+"List.do\";\r\n" + 
    			"    }");
/**
 * 删除
 */
    	System.out.println(
    			"    /**\r\n" + 
    			"     * 删除\r\n" + 
    			"     */\r\n" + 
    			"    @RequestMapping(\"del"+cc.getSimpleName()+".do\")\r\n" + 
    			"    @RequiresPermissions({\"del"+cc.getSimpleName()+"\"})\r\n"+
    			"    public String delEcsGoods("+cc.getSimpleName()+" u,ModelMap m) {\r\n" + 
    			"	    "+CreatUtil.toLowerOne(cc.getSimpleName())+"Biz.del"+cc.getSimpleName()+"ById(u);\r\n" + 
    			"		return \"redirect:get"+cc.getSimpleName()+"List.do\"; \r\n" + 
    			"    }");
/**
 * 导出	
 */
    	System.out.print("	    /**\r\n" + 
    			"	     * 导出\r\n" + 
    			"	     */\r\n" + 
    			"	    @RequestMapping(\"downLoad"+cc.getSimpleName()+".do\")\r\n" + 
    			"    	@RequiresPermissions({\"downLoad"+cc.getSimpleName()+"\"})\r\n"+
    			"	    public String downLoad"+cc.getSimpleName()+"("+cc.getSimpleName()+" u,HttpServletRequest request,ModelMap m) {\r\n" + 
    			"			//1.导出Excle文件路径\r\n" + 
    			"			//获取服务器的路径	\r\n" + 
    			"		    String path = request.getSession().getServletContext().getRealPath(\"\"); \r\n" + 
    			"	        //设置文件夹名字\r\n" + 
    			"	        long s=new Date().getTime();	        \r\n" + 
    			"	        String newpath=path+\"\\\\\"+s+\".xlsx\";	\r\n" + 
    			"			//2创建输出流\r\n" + 
    			"			try {\r\n" + 
    			"				OutputStream os=new FileOutputStream(newpath);\r\n" + 
    			"			//3创建工作簿对象\r\n" + 
    			"				WritableWorkbook wwb=Workbook.createWorkbook(os);\r\n" + 
    			"			//4 获取sheet对象	\r\n" + 
    			"				WritableSheet sheet=wwb.createSheet(\"随便\", 0);\r\n" + 
    			"				//5声明标题数组\r\n" + 
    			"				String[] title={");
    			for (Field fe : field) {
    		    	if(fe==field[field.length-1]) {
    		    		System.out.print("\""+fe.getName()+"\"");
    		    	}else {
    		    		System.out.print("\""+fe.getName()+"\",");
    		    	}
    			}	   			
    			System.out.println(
    			"};\r\n" + 
    			"				Label l;\r\n" + 
    			"				for(int i=0;i<title.length;i++){\r\n" + 
    			"				// 6创建title表格对象\r\n" + 
    			"					l=new Label(i, 0, title[i]);\r\n" + 
    			"					//7添加表格\r\n" + 
    			"					sheet.addCell(l);\r\n" + 
    			"				}");
    	System.out.println("				List<"+cc.getSimpleName()+"> list="+CreatUtil.toLowerOne(cc.getSimpleName())+"Biz.get"+cc.getSimpleName()+"List(u);\r\n"
    			+ "				//添加具体数据\r\n" + 
    			"				for(int i=0;i<list.size();i++){\r\n" + 
    			"					"+cc.getSimpleName()+" stu=list.get(i);");
    			int i=0;
    			for (Field fe : field) {
    				if("java.lang.String".equals(fe.getType().getName())) {
						System.out.println("					//第"+(i+1)+"列"+fe.getName()+"\r\n" + 
								"					l=new Label("+i+",i+1,stu.get"+CreatUtil.toUpperOne(fe.getName())+"());\r\n" + 
								"					sheet.addCell(l);");
    				}else{
    					if("java.util.Date".equals(fe.getType().getName())) {
    						System.out.println("					//第"+(i+1)+"列"+fe.getName()+"\r\n" + 
    								"					l=new Label("+i+",i+1,stu.get"+CreatUtil.toUpperOne(fe.getName())+"()==null?null:new SimpleDateFormat(\"yyyy-MM-dd\").format(stu.get"+CreatUtil.toUpperOne(fe.getName())+"()));\r\n" + 
    								"					sheet.addCell(l);");
    					}else {
    						System.out.println("					//第"+(i+1)+"列"+fe.getName()+"\r\n" + 
    								"					l=new Label("+i+",i+1,stu.get"+CreatUtil.toUpperOne(fe.getName())+"()==null?null:String.valueOf(stu.get"+CreatUtil.toUpperOne(fe.getName())+"()));\r\n" + 
    								"					sheet.addCell(l);");
    					}
    				}
					i++;
    			}
    	System.out.println("				}										\r\n" + 
    			"				wwb.write();\r\n" + 
    			"				wwb.close();\r\n" + 
    			"				os.close();//关闭输出流\r\n" + 
    			"			} catch (FileNotFoundException e) {\r\n" + 
    			"				e.printStackTrace();\r\n" + 
    			"			} catch (IOException e) {\r\n" + 
    			"				e.printStackTrace();\r\n" + 
    			"			} catch (RowsExceededException e) {\r\n" + 
    			"				e.printStackTrace();\r\n" + 
    			"			} catch (WriteException e) {\r\n" + 
    			"				e.printStackTrace();\r\n" + 
    			"			}\r\n" + 
    			"			m.put(\"success\", \"\\\\\"+s+\".xlsx\");\r\n" + 
    			"			return \"success\";\r\n" + 
    			"	    }");
/**
 * 导入    	
 */
    	System.out.println("	    /**\r\n" + 
    			"	     * 导入\r\n" + 
    			"	     */\r\n" + 
    			"	    @RequestMapping(\"upLoad"+cc.getSimpleName()+".do\")\r\n" + 
    			"    	@RequiresPermissions({\"upLoad"+cc.getSimpleName()+"\"})\r\n"+
    			"		public String upload"+cc.getSimpleName()+"(@RequestParam(value=\"uploadfile\") MultipartFile[] file,HttpServletRequest request,ModelMap m){\r\n" + 
    			"				//设置要上传的文件路径  在服务器创建 文件夹					\r\n" + 
    			"		    String path = request.getSession().getServletContext().getRealPath(\"\"); \r\n" + 
    			"	        path=path.substring(0, path.lastIndexOf(\"\\\\\"));\r\n" + 
    			"	        String newpath=path+\"\\\\youxian\\\\xlsx\";\r\n" + 
    			"	        \r\n" + 
    			"	        //保存路径\r\n" + 
    			"	        File images=new File(newpath);	  \r\n" + 
    			"	        //设置集合储存上传的文件路径和名字\r\n" + 
    			"	        List<String> li=new ArrayList<String>();\r\n" + 
    			"		       for(int i=0;i<file.length;i++){\r\n" + 
    			"		    	   	//当前时间的毫秒值和原文件名拼接\r\n" + 
    			"			        //如果原文件名过长则截取后10位        \r\n" + 
    			"			        long s = new Date().getTime(); \r\n" + 
    			"			        String p=file[i].getOriginalFilename(); \r\n" + 
    			"			        if(p.length()>10){\r\n" + 
    			"			        	p= p.substring(p.length()-10, p.length());\r\n" + 
    			"			        }\r\n" + 
    			"			        String fileName =s+ p;  \r\n" + 
    			"			        //拼接字符串\r\n" + 
    			"			        File targetFile = new File(images, fileName);\r\n" + 
    			"			        li.add(images+\"\\\\\"+fileName);\r\n" + 
    			"			        //文件夹不存在则创建\r\n" + 
    			"			        if(!targetFile.exists()){  \r\n" + 
    			"			            targetFile.mkdirs();  \r\n" + 
    			"			        }  	  	        \r\n" + 
    			"			        //保存  \r\n" + 
    			"			        try {  \r\n" + 
    			"			            file[i].transferTo(targetFile);  \r\n" + 
    			"			        } catch (Exception e) {  \r\n" + 
    			"			            e.printStackTrace();  \r\n" + 
    			"			        } 			        \r\n" + 
    			"		       }\r\n" + 
    			"		       int a=0;\r\n" + 
    			"		       for (String str : li) {\r\n" + 
    			"			        // 开始导入数据库		");
    	System.out.println("			        List<"+cc.getSimpleName()+"> list=new ArrayList<"+cc.getSimpleName()+">();\r\n" + 
    			"					//获取要到如的文件路径\r\n" + 
    			"					try {\r\n" + 
    			"						//获取工作簿\r\n" + 
    			"						Workbook wb=Workbook.getWorkbook(new File(str));\r\n" + 
    			"						Sheet ss=wb.getSheet(0);						\r\n" + 
    			"						String temp;\r\n" + 
    			"						for(int i=1;i<ss.getRows();i++){\r\n" + 
    			"							"+cc.getSimpleName()+" stu=new "+cc.getSimpleName()+"();");
		int j=0;
		for (Field fe : field) {
			if("java.lang.String".equals(fe.getType().getName())) {
				System.out.println("							//第"+(j+1)+"列"+fe.getName()+"\r\n" + 
						"							temp=ss.getCell("+j+",i).getContents();\r\n" + 
						"							stu.set"+CreatUtil.toUpperOne(fe.getName())+"(temp);");
			}else {
				if("java.lang.Integer".equals(fe.getType().getName())) {
					System.out.println("							//第"+(j+1)+"列"+fe.getName()+"\r\n" + 
							"							temp=ss.getCell("+j+",i).getContents();\r\n" + 
							"							stu.set"+CreatUtil.toUpperOne(fe.getName())+"(Integer.parseInt(temp));");	
				}else if("java.lang.Double".equals(fe.getType().getName())) {
					System.out.println("							//第"+(j+1)+"列"+fe.getName()+"\r\n" + 
							"							temp=ss.getCell("+j+",i).getContents();\r\n" + 
							"							stu.set"+CreatUtil.toUpperOne(fe.getName())+"(Double.parseDouble(temp));");	
				}else if("java.util.Date".equals(fe.getType().getName())) {
					System.out.println("							//第"+(j+1)+"列"+fe.getName()+"\r\n" + 
							"							temp=ss.getCell("+j+",i).getContents();\r\n" + 
							"							stu.set"+CreatUtil.toUpperOne(fe.getName())+"(new SimpleDateFormat(\"yyyy-MM-dd\").parse(temp));");	
				}else if("java.math.BigDecimal".equals(fe.getType().getName())){
					System.out.println("							//第"+(j+1)+"列"+fe.getName()+"\r\n" + 
							"							temp=ss.getCell("+j+",i).getContents();\r\n" + 
							"							stu.set"+CreatUtil.toUpperOne(fe.getName())+"(new BigDecimal(temp));");	
				}else {
					System.out.println("							//第"+(j+1)+"列"+fe.getName()+"\r\n" + 
							"							temp=ss.getCell("+j+",i).getContents();\r\n" + 
							"							stu.set"+CreatUtil.toUpperOne(fe.getName())+"(temp);");	
				}
			}
			j++;
		}
    	System.out.println("							list.add(stu);\r\n" + 
    			"						}												\r\n" + 
    			"					} catch (Exception e) {\r\n" + 
    			"						e.printStackTrace();\r\n" + 
    			"					}						\r\n" + 
    			"					  //开始导入数据库\r\n" + 
    			"					for ("+cc.getSimpleName()+" ss : list) {\r\n" + 
    			"						if("+CreatUtil.toLowerOne(cc.getSimpleName())+"Biz.add"+cc.getSimpleName()+"(ss)) {\r\n" + 
    			"							a++;\r\n" + 
    			"						}\r\n" + 
    			"					}					\r\n" + 
    			"			}\r\n" + 
    			"		     m.put(\"success\", \"共成功导入\"+a+\"条数据\");\r\n" + 
    			"		     return \"upLoad"+cc.getSimpleName()+"\";\r\n" + 
    			"		}");
    	System.out.println("}");
	}
}
