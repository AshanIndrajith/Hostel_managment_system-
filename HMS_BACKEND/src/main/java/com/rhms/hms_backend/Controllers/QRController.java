package com.rhms.hms_backend.Controllers;


import com.rhms.hms_backend.Services.QRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/api/qr")
public class QRController {

    @Autowired
    private QRService qrService;

    @GetMapping(value = "/qrcode/{content}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] generateQrCode(@PathVariable String content) throws IOException {
        int width = 200; // Adjust the desired width of the QR code
        int height = 200; // Adjust the desired height of the QR code
        return qrService.generateQrCodeImage(content, width, height);
    }


}
