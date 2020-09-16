package com.xh.common.shiro.realm;

import com.xh.service.PermissionService;
import com.xh.service.UserService;
import com.xh.entity.Sys_Permission;
import com.xh.entity.Sys_Role;
import com.xh.entity.Sys_User;
import com.xh.common.shiro.token.PlatformToken;
import com.xh.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PlatformAuthorizingRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 用户权限认证
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Sys_User user = (Sys_User) principals.getPrimaryPrincipal();
        List<Sys_Role> roles = roleService.findRolesByUserId(user.getId());
        if (null != roles && roles.size() > 0) {
            for (Sys_Role role : roles) {
                info.addRole(role.getRole());
                List<Sys_Permission> permissions = permissionService.findPermsByRoleId(role.getId());
                if (null != permissions) {
                    for (Sys_Permission permission : permissions) {
                        info.addStringPermission(permission.getPermission());
                    }
                }
            }
        }
        return info;
    }

    /**
     * 用户登录授权
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (token.getClass().isAssignableFrom(PlatformToken.class)) {
            PlatformToken authToken = (PlatformToken) token;
            String username = authToken.getUsername();
            //String captcha = authToken.getCaptcha();
            //获取该用户的session
            Session session = SecurityUtils.getSubject().getSession();
            Sys_User user = userService.findByUsername(username);
            session.setAttribute("username", username);
            session.setAttribute("nickname", user.getNickname());
            session.setAttribute("loginerrorCount", 0);
            session.setAttribute("user", user);
            session.setAttribute("uid", user.getId());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword().toCharArray(), ByteSource.Util.bytes(user.getSalt()), getName());
            info.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt()));
            return info;
        }
        return null;
    }

    public void clearAuthz() {
        clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
