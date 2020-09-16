package com.xh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.entity.Sys_Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper extends BaseMapper<Sys_Permission> {
    List<Sys_Permission> findPermsByRoleId(String roleId);

}
