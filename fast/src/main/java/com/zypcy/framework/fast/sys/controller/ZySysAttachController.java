package com.zypcy.framework.fast.sys.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.zypcy.framework.fast.common.error.BusinessException;
import com.zypcy.framework.fast.common.response.ResponseModel;
import com.zypcy.framework.fast.common.response.ResultEnum;
import com.zypcy.framework.fast.common.util.IdWorker;
import com.zypcy.framework.fast.sys.entity.ZySysAttach;
import com.zypcy.framework.fast.sys.service.IZySysAttachService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 附件表，同一用户上传多个附件请用attach_no 前端控制器
 * 在WebConfiguration.java 中开放权限 /sys/attach/** ，所有未登录用户都可以上传或下载附件
 * 可访问：/sys/attach/list 示例上传文件
 *
 * @author zhuyu
 * @since 2019-07-31
 */
@Api(tags = "sys-附件上传下载控制器")
@RestController
@RequestMapping("/sys/attach")
public class ZySysAttachController {

    @Autowired
    IZySysAttachService attachService;

    @ApiOperation(value = "打开附件上传页面", notes = "页面", httpMethod = "GET")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(ModelMap map) {
        map.addAttribute("attachs", attachService.listAttachTop20());
        return new ModelAndView("sys/file_list");
    }

    @ApiOperation(value = "打开附件上传页面", notes = "页面", httpMethod = "GET")
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ModelAndView upload() {
        return new ModelAndView("sys/fileupload");
    }

    /**
     * 在线显示文件
     *
     * @param id 文件id
     * @return
     */
    @ApiOperation(value = "预览附件（支持图片、txt、pdf）", notes = "api", httpMethod = "GET")
    @GetMapping("/view/{id}")
    public ResponseEntity<Object> serveFileOnline(@PathVariable String id) throws IOException {
        ZySysAttach attach = attachService.getById(id);
        if (attach != null) {
            byte[] files = FileUtil.readBytes(attach.getAttachPath());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "fileName=" + attach.getAttachName())
                    .header(HttpHeaders.CONTENT_TYPE, attach.getContentType())
                    .header(HttpHeaders.CONTENT_LENGTH, attach.getAttachSize() + "").header("Connection", "close")
                    .header(HttpHeaders.CONTENT_LENGTH, attach.getAttachSize() + "")
                    .body(files);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not found");
        }
    }

    /**
     * 下载附件
     *
     * @param id
     * @return
     * @throws UnsupportedEncodingException
     */
    @ApiOperation(value = "下载附件", notes = "api", httpMethod = "GET")
    @GetMapping("/download/{id}")
    public ResponseEntity<Object> downloadFileById(@PathVariable String id) throws UnsupportedEncodingException {
        ZySysAttach attach = attachService.getById(id);
        if (attach != null) {
            byte[] files = FileUtil.readBytes(attach.getAttachPath());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + URLEncoder.encode(attach.getAttachName(), "utf-8"))
                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                    .header(HttpHeaders.CONTENT_LENGTH, attach.getAttachSize() + "").header("Connection", "close")
                    .header(HttpHeaders.CONTENT_LENGTH, attach.getAttachSize() + "")
                    .body(files);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not found");
        }
    }

    /**
     * 上传文件
     * 当数据库中存在该md5值时，可以实现秒传功能
     *
     * @param file 文件
     * @return
     */
    @PostMapping("/upload")
    public ResponseModel formUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        ResponseModel model = ResponseModel.failInstance();
        try {
            if (file != null && !file.isEmpty()) {
                String fileMd5 = SecureUtil.md5(file.getInputStream());
                String attachNo = request.getParameter("attachNo");
                if (StringUtils.isEmpty(attachNo)) {
                    attachNo = IdWorker.getDateId();
                }

                ZySysAttach attach = attachService.fileUpload(fileMd5, attachNo, file);
                System.out.println(JSON.toJSONString(attach));
                model.setResultObj(attach);
                model.setResultCode(ResultEnum.SUCCESS.getResultCode());
                model.setResultMessage(ResultEnum.SUCCESS.getResultMessage());
            } else {
                model.setResultMessage("请传入文件");
            }
        } catch (IOException ex) {
            //ex.printStackTrace();
            model.setResultMessage(ex.getMessage());
        }
        return model;
    }


    /**
     * 删除附件，请不要删除附件，只删除业务中保存的附件Id，因实现了秒传功能，同一个附件只保存一份可能被多个用户引用，删除后其他用户会操作不了
     *
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public boolean deleteFileById(@PathVariable String id) {
        if (!StringUtils.isEmpty(id)) {
            //fileService.removeFile(id , true);
            //model.setCode(ResponseModel.Success);
            throw new BusinessException("暂不支持删除附件，请删除业务中保存的附件Id");
        } else {
            throw new BusinessException("请传入文件id");
        }
    }
}
