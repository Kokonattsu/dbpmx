package com.cy.pj.sys.service.impl;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.dao.SysUserRolesDao;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.pojo.SysUser;
import com.cy.pj.sys.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Transactional(readOnly = false,
        rollbackFor =Throwable.class,
        timeout = 60,
        isolation = Isolation.READ_COMMITTED,
        propagation = Propagation.REQUIRED)
@Service
public class SysUserServiceImpl implements SysUserService {

    //根据name查询用户
    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUserRolesDao sysUserRolesDao;
    @Override
    public PageObject<SysUser> findPageObjects(String username, Integer pageCurrent) {
        //参数校验。。
        Integer rowCount = sysUserDao.getRowCount(username);
        if (rowCount==0){
            throw new ServiceException("查询记录不存在");
        }
        Integer pageSize=7;
        Integer startIndex =(pageCurrent-1)*pageSize;
        List<SysUser> users =
                sysUserDao.findPageObjects(
                        username, startIndex, pageSize);
        return new PageObject<>(pageCurrent,pageSize,rowCount,users);
    }

    //修改状态
    @RequiredLog("禁用/启用用户")
    @Override
    public Integer updateValidById(Integer id,Integer valid,String modifiedUser) {
        if(id==null||id<0){
            throw new ServiceException("需要传递正确的参数");
        }
        Integer rows = sysUserDao.updateValidById(id,valid,modifiedUser);

        return rows;
    }

    //查询修改页面数据
    @Override
    public Map<String, Object> findObjectById(Integer id) {
        if (id==0||id<0){throw new ServiceException("传递参数不正确");};
        SysUser user = sysUserDao.findObjectById(id);
        List<Integer> roleIds = sysUserRolesDao.findRolesByUserId(id);
        if (user==null){throw new ServiceException("查询的数据不存在");};
        Map<String,Object> map =new HashMap<>();
        map.put("user", user);
        map.put("roleIds", roleIds);
        return map;
    }

    //添加用户
    @RequiredLog("添加用户")
    @Override
    public Integer saveObject(SysUser sysUser, Integer[] RoleIds) {
        if (sysUser==null){
            throw new ServiceException("需要传递正确的用户");
        }
        if (sysUser.getUsername()==null){
            throw new ServiceException("用户名不能为空");
        }
        if (sysUser.getPassword()==null){
            throw new ServiceException("密码不能为空");
        }
        if (RoleIds==null||RoleIds.length==0){
            throw new ServiceException("必须为用户分配一个角色");
        }
        String password = sysUser.getPassword();
        //产生随机数作为盐值
        String salt = UUID.randomUUID().toString();
        SimpleHash sh = new SimpleHash("MD5", password, salt, 1);
        String newpassword = sh.toHex();

        sysUser.setSalt(salt);
        sysUser.setPassword(newpassword);

        Integer rows = sysUserDao.insertObject(sysUser);
        if (rows==0){throw new ServiceException("用户名已存在");}
        sysUserRolesDao.saveObjectsByUserId(sysUser.getId(), RoleIds);


        return rows;
    }

    //修改用户
    @RequiredLog("修改用户")
    @Override
    public Integer updateObjectById(SysUser sysUser, Integer[] RoleIds) {
        if (sysUser==null){
            throw new ServiceException("需要传递正确的用户");
        }
        if (sysUser.getUsername()==null){
            throw new ServiceException("用户名不能为空");
        }
        if (RoleIds==null||RoleIds.length==0){
            throw new ServiceException("必须为用户分配一个角色");
        }
        Integer userId = sysUser.getId();
        Integer row = sysUserDao.updateObject(sysUser);
        sysUserRolesDao.deleteRoleIdsByUserId(userId);
        sysUserRolesDao.saveObjectsByUserId(userId,RoleIds);

        if (row==null){throw new ServiceException("该记录可能已经不在了");}
        return row;
    }

    //修改密码
    @RequiredLog("修改密码")
    @Override
    public Integer updatePassword(String password, String newPassword, String cfgPassword) {

        if (StringUtils.isEmpty(password)){throw new IllegalArgumentException("原密码不能为空");}
        if (StringUtils.isEmpty(newPassword)){throw new IllegalArgumentException("新密码不能为空");}
        if (!(newPassword.equals(cfgPassword))){throw new IllegalArgumentException("两次输出的密码不一致");}
        //此对象的密码是加密过的
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        String oldPassword = user.getPassword();
        SimpleHash sh=new SimpleHash("MD5",password,user.getSalt(),1);
       // System.out.println(sh.toHex());

        String newHashPassword=sh.toHex();
        if(!oldPassword.equals(newHashPassword)){throw new IllegalArgumentException("原密码不正确");}
        String newSalt=UUID.randomUUID().toString();
        Integer row = sysUserDao.updatePasswordByUserName(user.getUsername(), newHashPassword, newSalt);
        if (row==0){throw new IllegalArgumentException("该记录可能已经不在了");}
        return row;
    }
}
