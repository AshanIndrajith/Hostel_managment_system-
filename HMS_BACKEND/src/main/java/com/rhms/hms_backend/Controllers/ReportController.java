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



    @GetMapping("/report/{format}")
    public String generateReport(@PathVariable String format) throws FileNotFoundException, JRException {
        return reportService.exportReport(format);
    }






}
