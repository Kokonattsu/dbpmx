package com.cy.pj.sys.service.realm;

import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenusDao;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRolesDao;
import com.cy.pj.sys.pojo.SysUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ShiroUserRealm extends AuthorizingRealm {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserRolesDao sysUserRolesDao;
    @Autowired
    private SysRoleMenusDao sysRoleMenusDao;
    @Autowired
    private SysMenuDao sysMenuDao;

    /**获取权限*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principalCollection) {
        //获取登录用户信息
        SysUser user = (SysUser) principalCollection.getPrimaryPrincipal();
        Integer userId= user.getId();
        List<Integer> roleIds = sysUserRolesDao.findRolesByUserId(userId);
        if(roleIds==null||roleIds.size()==0){throw new AuthorizationException();}
        List<Integer> menuIds = sysRoleMenusDao.findMenuIdsByRoleIds(roleIds);
        if(menuIds==null||menuIds.size()==0){throw new AuthorizationException();}
        List<String> permissions = sysMenuDao.findPermissionsByMenuIds(menuIds);
        Set<String> set=new HashSet<>();
        for(String per:permissions){
            //判断是否为空
            if(!StringUtils.isEmpty(per)){
                set.add(per);
            }
        }
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.setStringPermissions(set);

        return info;
    }
    /**获取认证信息，用来与输入信息比对验证*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取验证信息,强转成储存账号密码的UsernamePasswordToken
        UsernamePasswordToken upToken=(UsernamePasswordToken)authenticationToken;
        String username = upToken.getUsername();
        //验证
        SysUser user = sysUserDao.findUserByUserName(username);
        if (user==null){throw new UnknownAccountException(); }
        if (user.getValid()==0){throw new LockedAccountException();}
        //封装验证信息，由SecurityManager 完成后续的密码验证
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
        //getName()shiro 框架中Realm自带的方法，用来获取Realm类的名字
        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(
                user,user.getPassword(),credentialsSalt,getName());

        return info;
    }

    //密码加密装置，shiro框架在做验证时会调用此方法对用户输入的密码进行加密
    @Override
    public CredentialsMatcher getCredentialsMatcher() {
        //
        HashedCredentialsMatcher cMatcher=new HashedCredentialsMatcher();
        //密码加密算法
        cMatcher.setHashAlgorithmName("MD5");
        //加密次数
        cMatcher.setHashIterations(1);

        return cMatcher;
    }

//    @Override
//    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
//        HashedCredentialsMatcher cMatcher=new HashedCredentialsMatcher();
//        cMatcher.setHashAlgorithmName("MD5");
//        cMatcher.setHashIterations(1);
//        super.setCredentialsMatcher(cMatcher);
//    }
}
