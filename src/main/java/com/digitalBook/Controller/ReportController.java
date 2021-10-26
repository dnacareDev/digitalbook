package com.digitalBook.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.digitalBook.Entity.Report;
import com.digitalBook.Entity.User;
import com.digitalBook.Service.ReportService;

@Controller
@RequestMapping("/data")
// 기초정보 > 과제
public class ReportController
{
	@Autowired
	private ReportService service;
	
	@Autowired
	private FileController fileController;
	
	// 과제 목록
	@RequestMapping("report")
	public ModelAndView ReportList(ModelAndView mv)
	{
		mv.setViewName("report/report_list");
		
		return mv;
	}
	
	// 과제 등록 페이지
	@RequestMapping("report/insert")
	public ModelAndView ReportInsert(ModelAndView mv)
	{
		mv.setViewName("report/report_insert");
		
		return mv;
	}
	
	// 과제 등록
	@ResponseBody
	@RequestMapping("report/insertReport")
	public ModelAndView InsertReport(ModelAndView mv, Authentication auth, @RequestParam("report") String report, @RequestParam("file") MultipartFile file) throws IOException
	{
		User prin = (User)auth.getPrincipal();
		
		String[] extension = file.getOriginalFilename().split("\\.");
		
		String report_file = fileController.ChangeFileName(extension[1]);
		String report_origin_file = file.getOriginalFilename();

		String path = "upload";
		
		File filePath = new File(path);
		
        if (!filePath.exists())
            filePath.mkdirs();

       	Path fileLocation = Paths.get(path).toAbsolutePath().normalize();
       	Path targetLocation = fileLocation.resolve(report_file);
		
       	Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
       	
		JSONArray arr = new JSONArray(report);
		
		List<Report> reports = new ArrayList<Report>();
		
		for(int i = 0; i < arr.length(); i++)
		{
			Report item = new Report();
			
			JSONObject obj = arr.getJSONObject(i);
			
			String report_year = (String)obj.get("report_year");
			
			item.setUser_name((String)obj.get("user_name"));
			item.setReport_code((String)obj.get("report_code"));
			item.setReport_number((String)obj.get("report_number"));
			item.setReport_title((String)obj.get("report_title"));
			item.setReport_year(Integer.parseInt(report_year));
			item.setReport_contents((String)obj.get("report_contents"));
			item.setReport_file(report_file);
			item.setReport_origin_file(report_origin_file);
			item.setUser_id(prin.getUser_id());
			
			reports.add(item);
		}
		
		int result = service.InsertReport(reports);
		
		if(result == 0)
		{
			mv.setViewName("redirect:/data/report/insert");
		}
		else
		{
			mv.setViewName("redirect:/data/report");
		}
		
		return mv;
	}
}