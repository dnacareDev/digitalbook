package com.digitalBook.Controller;

import java.util.Date;

import org.springframework.stereotype.Controller;

@Controller
public class FileController
{
	   public String ChangeFileName(String extension)
	   {
		   String fileName = "";
		   
		   Date date = new Date();
		   
		   fileName = Long.toString(date.getTime()) +"." + extension;
		   
		   return fileName;
	   }
}