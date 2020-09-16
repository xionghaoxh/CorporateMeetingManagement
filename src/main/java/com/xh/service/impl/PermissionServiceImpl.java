package com.xh.service.impl;

import com.xh.entity.Sys_Permission;
import com.xh.mapper.PermissionMapper;
import com.xh.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Sys_Permission> implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Sys_Permission> getAllPermission() {
        List<Sys_Permission> permissionList = permissionMapper.selectList(null);
        return permissionList;
    }

    @Override
    public int delPermById(String permId) {
        int count = permissionMapper.deleteById(permId);
        return count;
    }

    @Override
    public int delPermBath(ArrayList<String> ids) {
        int count = permissionMapper.deleteBatchIds(ids);
        return count;
    }

    @Override
    public List<Sys_Permission> findPermsByRoleId(String roleId) {
        List<Sys_Permission> perms = permissionMapper.findPermsByRoleId(roleId);
        if (null != perms && perms.size() > 0) {
            return perms;
        }
        return null;
    }
}
