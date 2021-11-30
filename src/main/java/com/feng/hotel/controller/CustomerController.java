package com.feng.hotel.controller;

import com.feng.hotel.base.Pagination;
import com.feng.hotel.base.entity.response.Result;
import com.feng.hotel.base.exception.BizException;
import com.feng.hotel.base.exception.BizInfo;
import com.feng.hotel.domain.Customer;
import com.feng.hotel.manager.ICustomerManager;
import com.feng.hotel.request.CustomerPageQuery;
import com.feng.hotel.response.CustomerResponse;
import com.feng.hotel.utils.GlobalUtils;
import com.feng.hotel.utils.IdWorkerUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author feng
 * @since 2021/9/22
 */
@RestController
@RequestMapping(value = "customer")
public class CustomerController {

    private final ICustomerManager customerManager;


    public CustomerController(ICustomerManager customerManager) {
        this.customerManager = customerManager;
    }


    /**
     * 分页查询客户列表
     *
     * @param customerPageQuery 查询参数
     * @return 分页查询结果
     */
    public Result<Pagination<CustomerResponse>> page(@RequestBody CustomerPageQuery customerPageQuery) {
        Pagination<CustomerResponse> page = customerManager.page(customerPageQuery);
        return Result.success(page);
    }

    @PostMapping(value = "/save")
    public Customer upload(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();

        File file = new File(System.getProperty("user.dir").concat("/image/").concat(IdWorkerUtils.generateStringId())
            .concat(GlobalUtils.getFileExt(fileName)));
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new BizException(BizInfo.FILE_UPLOAD_ERROR);
            }
        }

        multipartFile.transferTo(file);
        String canonicalPath = file.getCanonicalPath();

        return customerManager.save(canonicalPath);
    }
}
