package com.km086.server.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.km086.server.config.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.km086.server.model.apk.ApkVersion;
import com.km086.server.service.ApkVersionService;

@RestController
@RequestMapping("apkVersion")
public class ApkVersionController {

    @Autowired
    ApkVersionService apkVersionService;

    @Autowired
    private ConfigProperties configProperties;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ApkVersion findApkVersion() {
        ApkVersion apkVersion = apkVersionService.findById(new Long(1));
        return apkVersion;
    }

    @CrossOrigin
    @RequestMapping(value = "/apk/{fileName}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadUserAvatarImage(@PathVariable String fileName)
            throws IOException {
        File image = new File(configProperties.getApkDir() + File.separator + fileName);
        InputStream in = new FileInputStream(image);
        return ResponseEntity.ok().contentLength(in.available())
                .header("Content-Disposition", "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/octet-stream")).body(new InputStreamResource(in));
    }

}
