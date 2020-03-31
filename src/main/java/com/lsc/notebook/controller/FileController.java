package com.lsc.notebook.controller;

import com.lsc.notebook.util.PropertiesUtils;
import com.lsc.notebook.util.Result;
import com.lsc.notebook.util.UuidUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @Author: luosc
 * @Description:
 * @Date:created in 14:22 2020/3/31
 */
@RestController
@RequestMapping("/file")
public class FileController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    private static final String FILE_PATH = PropertiesUtils.getCommonYml("download.filePath");


    @ApiOperation(value = "文件上传", notes = "文件上传", httpMethod = "POST")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result upload(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return Result.error("文件为空");
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            log.info("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            log.info("文件的后缀名为：" + suffixName);
            //文件重命名
            fileName = UuidUtil.getUuid() + suffixName;
            // 设置文件存储路径

            String path = FILE_PATH + fileName;
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入
            return Result.success(fileName);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.error("上传失败");
    }

    @ApiOperation(value = "文件批量上传", notes = "文件批量上传", httpMethod = "POST")
    @PostMapping("/batchUpload")
    public Result handleFileUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(
                            new File(FILE_PATH + file.getOriginalFilename())));//设置文件路径及名字
                    stream.write(bytes);// 写入
                    stream.close();
                } catch (Exception e) {
                    stream = null;
                    return Result.error("第 " + i + " 个文件上传失败 ==> "
                            + e.getMessage());
                }
            } else {
                return Result.error("第 " + i
                        + " 个文件上传失败因为文件为空");
            }
        }
        return Result.success("上传成功");
    }

    @ApiOperation(value = "文件下载", notes = "文件下载", httpMethod = "GET")
    @GetMapping("/download/{fileName}")
    public Result downloadFile(HttpServletRequest request, HttpServletResponse response, @ApiParam @PathVariable String fileName) {
        if (fileName != null) {
            //设置文件路径
            File file = new File(FILE_PATH + fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return Result.success("下载成功");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return Result.error("下载失败");
    }
}
