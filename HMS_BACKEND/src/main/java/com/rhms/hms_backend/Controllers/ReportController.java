package com.rhms.hms_backend.Controllers;


import com.rhms.hms_backend.Repositories.ComplainRepo;
import com.rhms.hms_backend.Services.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@CrossOrigin
@RequestMapping("/api/report")
public class ReportController {


    @Autowired
    private ComplainRepo complainRepo;
    @Autowired
    private ReportService reportService;



    @GetMapping("/report")
    public String generateReport() throws FileNotFoundException, JRException {
        return reportService.exportReport();
    }



    @GetMapping("/monthly")
    public String generateMonthlyReport() throws FileNotFoundException, JRException {
        return reportService.MonthlyReport();
    }






}
