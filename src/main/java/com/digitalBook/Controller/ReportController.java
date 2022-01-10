package com.digitalBook.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.digitalBook.Entity.Report;
import com.digitalBook.Entity.ReportFile;
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
	
	// 과제 수정 페이지
	@RequestMapping("report/modify")
	public ModelAndView ReportModify(ModelAndView mv, @RequestParam("report_file_id") int report_file_id)
	{
		ReportFile report = service.SelectReportDetail(report_file_id);
		
		mv.addObject("report", report);
		
		mv.setViewName("report/report_modify");
		
		return mv;
	}
	
	// 과제 검색
	@ResponseBody
	@RequestMapping("report/searchReport")
	public Map<String, Object> SearchReport(@RequestParam("search_type") String search_type, @RequestParam("keyword") String keyword, @RequestParam("page_num") int page_num, @RequestParam("limit") int limit)
	{
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		
		int count = service.SelectReportFileCount(search_type, keyword);
		
		int offset = (page_num - 1) * limit;
		int start_page = ((page_num - 1) / 10) * 10 + 1;
		int end_page = (count + limit - 1) / limit;
		
		List<ReportFile> report = service.SearchReportFile(search_type, keyword, offset, limit);
		
		result.put("report", report);
		result.put("page_num", page_num);
		result.put("start_page", start_page);
		result.put("end_page", end_page);
		result.put("offset", offset);
		
		return result;
	}
	
	// 과제 등록
	@ResponseBody
	@RequestMapping("report/insertReport")
	public ModelAndView InsertReport(ModelAndView mv, Authentication auth, @ModelAttribute ReportFile reportFile, @RequestParam("report") String report, @RequestParam("file") MultipartFile file) throws IOException
	{
		User prin = (User)auth.getPrincipal();
		
		int result = UploadReport(prin, reportFile, report, file);
		
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
	
	// 과제 파일 다운로드
	@ResponseBody
	@RequestMapping("report/downloadFile")
	public ResponseEntity<Object> DownloadFile(@RequestParam("report_file") String report_file)
	{
		String path = "upload/" + report_file;
		
		try
		{
			Path filePath = Paths.get(path);
			Resource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기
			
			File file = new File(path);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getName()).build());  // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더
			
			return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	// 과제 수정
	@RequestMapping("report/updateReport")
	public ModelAndView UpdateReport(ModelAndView mv, Authentication auth, @ModelAttribute ReportFile report_file, @RequestParam("report") String report, @RequestParam("type") int type, @RequestParam("file") MultipartFile file) throws IOException
	{
		int result = 0;

		if(type == 0)
		{
			result = service.UpdateReportFile(report_file);
		}
		else
		{
			String path = "upload/" + report_file.getReport_file();
			File origin_file = new File(path);
			
			if(origin_file.delete())
			{
				int delete_file = service.DeleteReportFile(report_file.getReport_file_id());
				int delete_report = service.DeleteReport(report_file.getReport_file_id());
				
				if(delete_file != 0 && delete_report != 0)
				{
					User prin = (User)auth.getPrincipal();
					
					result = UploadReport(prin, report_file, report, file);
				}
			}
		}
		
		if(result == 0)
		{
			mv.setViewName("redirect:/data/report/modify?report_file_id=" + report_file.getReport_file_id());
		}
		else
		{
			mv.setViewName("redirect:/data/report");
		}
		
		return mv;
	}
	
	// 과제 등록
	public int UploadReport(User user, ReportFile reportFile, String report, MultipartFile file) throws IOException
	{
		int result = 0;
		
		String[] extension = file.getOriginalFilename().split("\\.");
		
		String report_file = fileController.ChangeFileName(extension[1]);
		String report_originFile = file.getOriginalFilename();

		String path = "upload";
		
		File filePath = new File(path);
		
        if (!filePath.exists())
            filePath.mkdirs();

       	Path fileLocation = Paths.get(path).toAbsolutePath().normalize();
       	Path targetLocation = fileLocation.resolve(report_file);
		
       	Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
       	
       	reportFile.setUser_id(user.getUser_id());
       	reportFile.setReport_file(report_file);
       	reportFile.setReport_originFile(report_originFile);
       	
       	int insert_file = service.InsertReportFile(reportFile);
       	
       	if(insert_file != 0)
       	{
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
       			item.setReport_year((String)obj.get("report_year"));
       			item.setReport_file_id(reportFile.getReport_file_id());
       			
       			reports.add(item);
       		}
       		
       		service.UpdateReportStatus(reports);
       		result = service.InsertReport(reports);
       	}
       	
       	return result;
	}
	
	//엑셀 다운로드
	@RequestMapping(value = "/report/exceldownload", produces = "application/vnd.ms-excel")
	public String ReportExceldownload() 
	{
		
		return "reportExcelView";
	}
	
}