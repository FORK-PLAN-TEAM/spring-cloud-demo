package com.zypcy.framework.fast.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zypcy.framework.fast.common.error.BusinessException;
import com.zypcy.framework.fast.common.util.FilePathUtil;
import com.zypcy.framework.fast.common.util.IdWorker;
import com.zypcy.framework.fast.sys.entity.ZySysAttach;
import com.zypcy.framework.fast.sys.mapper.ZySysAttachMapper;
import com.zypcy.framework.fast.sys.service.IZySysAttachService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 附件表，同一用户上传多个附件请用attach_no 服务实现类
 * </p>
 *
 * @author zhuyu
 * @since 2019-07-31
 */
@Service
public class ZySysAttachServiceImpl extends ServiceImpl<ZySysAttachMapper, ZySysAttach> implements IZySysAttachService {

    @Autowired
    ZySysAttachMapper attachMapper;

    @Override
    public ZySysAttach getById(String id) {
        ZySysAttach attach = new ZySysAttach();
        attach.setAttachId(id);
        attach.setIsdel(false);
        return attachMapper.selectOne(new QueryWrapper<>(attach));
    }

    @Override
    public List<ZySysAttach> getByAttachNo(String attachNo) {
        ZySysAttach attach = new ZySysAttach();
        attach.setAttachNo(attachNo);
        attach.setIsdel(false);
        return attachMapper.selectList(new QueryWrapper<>(attach));
    }

    @Override
    public List<ZySysAttach> listAttachTop20() {
        return attachMapper.listAttachTop20();
    }

    @Override
    public ZySysAttach getByAttachByMd5(String md5) {
        ZySysAttach attach = new ZySysAttach();
        attach.setMd5(md5);
        attach.setIsdel(false);
        return attachMapper.selectOne(new QueryWrapper<>(attach));
    }

    /**
     * 文件上传
     * @param md5
     * @param attachNo
     * @param file
     * @return
     */
    @Override
    public ZySysAttach fileUpload(String md5, String attachNo,MultipartFile file) {
        ZySysAttach attach = getByAttachByMd5(md5);
        if (attach != null) {
            return attach;
        }
        try {
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

            //把文件写到当前发布目录下(按年月日存放)
            String attachName = IdWorker.getDateId() + suffix;
            String path = FilePathUtil.getFileUploadPath() + attachName;
            file.transferTo(new File(path));

            attach = new ZySysAttach();
            attach.setAttachPath(path);
            attach.setAttachId(IdWorker.getDateId());
            attach.setAttachNo(attachNo);
            attach.setAttachName(attachName);
            attach.setAttachOldName(file.getOriginalFilename());
            attach.setAttachSize(file.getSize());
            attach.setContentType(file.getContentType());
            attach.setCreateTime(LocalDateTime.now());
            attach.setMd5(md5);
            attach.setAttachSuffix(suffix);
            attach.setIsdel(false);
            attachMapper.insert(attach);
        }catch (Exception ex){
            throw new BusinessException("文件上传失败");
        }
        return attach;
    }
}
