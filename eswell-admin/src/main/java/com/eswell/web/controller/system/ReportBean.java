package com.eswell.web.controller.system;

import com.eswell.common.core.domain.entity.SysUser;
import com.eswell.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ReportBean {

    @Autowired
    private ISysUserService userService;

    public List getProductList(String dsName, String datasetName, Map<String, Object> parameters) {
        SysUser sysUser = new SysUser();
        List<SysUser> maps = userService.selectUserList(sysUser);
        for (SysUser map : maps) {
            System.out.println(map);
        }
        return maps;
    }

}
