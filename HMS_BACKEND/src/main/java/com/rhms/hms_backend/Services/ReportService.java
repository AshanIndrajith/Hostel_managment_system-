package com.rhms.hms_backend.Services;

import com.rhms.hms_backend.Models.Complain;
import com.rhms.hms_backend.Repositories.ComplainRepo;
import jakarta.transaction.Transactional;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Transactional
public class ReportService {

    @Autowired
    private ComplainService complainService;


    @Autowired
    private ComplainRepo complainRepo;



    public String exportReport() throws JRException {
        String path = "F:\\hostel management system\\Hostel_managment_system-\\HMS_BACKEND\\pdf";
        List<Complain> complaints = (List<Complain>) complainRepo.CreateTodayComplaintView();

        // Generate a timestamp
        SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = timestampFormat.format(new java.util.Date());

        try {
            // Load the JRXML file
            File file = ResourceUtils.getFile("classpath:complaint_daily.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(complaints);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

            // Append the timestamp to the filename
            String filename = "complaint_" + timestamp;

            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\" + filename + ".pdf");
            return "Report generated with timestamp: " + timestamp;
        } catch (JRException e) {
            e.printStackTrace();
            throw e; // Rethrow the exception for proper error handling
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }





    public String MonthlyReport() throws JRException {
        String path = "F:\\hostel management system\\Hostel_managment_system-\\HMS_BACKEND\\pdf";
        List<Complain> complaints = (List<Complain>) complainRepo.CreateMonthlyComplaintView();

        // Generate a timestamp
        SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = timestampFormat.format(new java.util.Date());

        try {
            // Load the JRXML file
            File file = ResourceUtils.getFile("classpath:monthly.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(complaints);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

            // Append the timestamp to the filename
            String filename = "Monthly_" + timestamp;

            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\" + filename + ".pdf");
            return "Report generated with timestamp: " + timestamp;
        } catch (JRException e) {
            e.printStackTrace();
            throw e; // Rethrow the exception for proper error handling
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
