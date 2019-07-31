package com.zypcy.framework.fast.sys.service;

import com.zypcy.framework.fast.sys.entity.ZySysAttach;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 附件表，同一用户上传多个附件请用attach_no 服务类
 * </p>
 *
 * @author zhuyu
 * @since 2019-07-31
 */
public interface IZySysAttachService extends IService<ZySysAttach> {

    /**
     * 根据Id获取文件
     * @param id
     * @return
     */
    ZySysAttach getById(String id);

    /**
     * 根据attachNo获取附件集合
     * @param attachNo
     * @return
     */
    List<ZySysAttach> getByAttachNo(String attachNo);

    /**
     * 获取附件表前20条数据
     * @return
     */
    List<ZySysAttach> listAttachTop20();

    /**
     * 根据md5获取附件
     * @param md5
     * @return
     */
    ZySysAttach getByAttachByMd5(String md5);

    /**
     * 上传附件
     * @param md5
     * @param attachNo
     * @param file
     * @return
     */
    ZySysAttach fileUpload(String md5, String attachNo,MultipartFile file);
}
