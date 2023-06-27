package com.wuyang.yygh.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyang.yygh.common.exception.YyghException;
import com.wuyang.yygh.common.utils.JwtHelper;
import com.wuyang.yygh.enums.AuthStatusEnum;
import com.wuyang.yygh.model.user.Patient;
import com.wuyang.yygh.model.user.UserInfo;
import com.wuyang.yygh.user.mapper.UserInfoMapper;
import com.wuyang.yygh.user.service.PatientService;
import com.wuyang.yygh.user.service.UserInfoService;
import com.wuyang.yygh.vo.user.LoginVo;
import com.wuyang.yygh.vo.user.UserAuthVo;
import com.wuyang.yygh.vo.user.UserInfoQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wuyang.yygh.user.util.HttpClientUtils.get;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wuyang
 * @since 2023-03-28
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private PatientService patientService;

    @Override
    public Map<String, Object> login(LoginVo loginVo) {
        //1.获取用户输入的手机号和验证码
        String phone = loginVo.getPhone();
        String code = loginVo.getCode();
        //2.要对手机号和验证码进行非空校验
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)){
            throw new YyghException(20001,"用户名和验证码");
        }
        //3.用户输入的验证码和服务器端redis中的验证码比较 TODO
        if (code.equals(redisTemplate.opsForValue().get(phone))) {
            throw new YyghException(20001,"验证码不正确");
        }
        //4.判断是不是首次登录，如果是首次登录，完成自动注册功能
        UserInfo userInfo = null;
        String openid = loginVo.getOpenid();

        if (StringUtils.isEmpty(openid)) {//手机登录
            QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone",phone);
            userInfo = baseMapper.selectOne(queryWrapper);
            //如果返回对象为空，就是第一次登录，存到数据库登录数据
            if (userInfo == null) {//首次登录，完成自动注册功能
                userInfo.setPhone(phone);
                userInfo.setStatus(1);
                userInfo.setCreateTime(new Date());
                baseMapper.insert(userInfo);
            }
            //判断用户是否可用
            if(userInfo.getStatus() == 0) {
                throw new YyghException(20001,"用户已经禁用");
            }
        }else {//微信绑定手机号
            //1 创建userInfo对象，用于存在最终所有数据
            UserInfo userInfoFinal = new UserInfo();
            QueryWrapper<UserInfo> phoneWrapper = new QueryWrapper<>();
            phoneWrapper.eq("phone",phone);
            UserInfo userInfo1 = baseMapper.selectOne(phoneWrapper);
            if (userInfo1 != null) {
                // 如果查询手机号对应数据,封装到userInfoFinal
                BeanUtils.copyProperties(userInfo1,userInfoFinal);
                //把手机号数据删除
                baseMapper.delete(phoneWrapper);
            }else {
                userInfoFinal.setPhone(phone);
            }
            QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("openid",openid);
            userInfo = baseMapper.selectOne(queryWrapper);
            userInfoFinal.setId(userInfo.getId());
            userInfoFinal.setNickName(userInfo.getNickName());
            userInfoFinal.setOpenid(openid);
            userInfoFinal.setId(userInfo.getId());
            baseMapper.updateById(userInfoFinal);
        }
        //5.判断用户的状态，如果用户不是首次登录，但是用户的状态不确定，要确定用户是否被禁用
        if (userInfo.getStatus()==0) {
            throw new YyghException(20001,"该用户已被禁用");
        }
        //6.返回用户信息
        Map<String,Object> map = new HashMap<String,Object>();
        String name = userInfo.getName();
        if (StringUtils.isEmpty(name)) {
            name = userInfo.getNickName();
        }
        if (StringUtils.isEmpty(name)) {
            name = userInfo.getPhone();
        }
        map.put("name",name);
        String token = JwtHelper.createToken(userInfo.getId(), name);
        map.put("token",token); //TODO
        return map;
    }
    @Override
    public UserInfo selectByOpenId(String openid) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid",openid);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public void userAuth(Long userId, UserAuthVo userAuthVo) {
        //根据用户id查询用户信息
        UserInfo userInfo = baseMapper.selectById(userId);
        //设置认证信息
        //认证人姓名
        userInfo.setName(userAuthVo.getName());
        //其他认证信息
        userInfo.setCertificatesType(userAuthVo.getCertificatesType());
        userInfo.setCertificatesNo(userAuthVo.getCertificatesNo());
        userInfo.setCertificatesUrl(userAuthVo.getCertificatesUrl());
        userInfo.setAuthStatus(AuthStatusEnum.AUTH_RUN.getStatus());
        //进行信息更新
        baseMapper.updateById(userInfo);
    }

    @Override
    public UserInfo selectById(Long userId) {
        if (userId == null){
            return null;
        }
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        UserInfo userInfo = baseMapper.selectOne(queryWrapper);
        userInfo.getParam().put("authStatusString",AuthStatusEnum.getStatusNameByStatus(userInfo.getAuthStatus()));
        return userInfo;
    }

    @Override
    public Page<UserInfo> selectUserInfoPage(Integer pageNo, Integer limit,UserInfoQueryVo userInfoQueryVo) {
        Page<UserInfo> page = new Page<>(pageNo,limit);
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        //UserInfoQueryVo获取条件值
        String keyword = userInfoQueryVo.getKeyword(); //用户名称
        Integer status = userInfoQueryVo.getStatus();//用户状态
        Integer authStatus = userInfoQueryVo.getAuthStatus(); //认证状态
        String createTimeBegin = userInfoQueryVo.getCreateTimeBegin(); //开始时间
        String createTimeEnd = userInfoQueryVo.getCreateTimeEnd(); //结束时间
        //对条件值进行非空判断
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(keyword)) {
            wrapper.like("name",keyword).or().like("phone",keyword);
        }
        if(!StringUtils.isEmpty(status)) {
            wrapper.eq("status",status);
        }
        if(!StringUtils.isEmpty(authStatus)) {
            wrapper.eq("auth_status",authStatus);
        }
        if(!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge("create_time",createTimeBegin);
        }
        if(!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.le("create_time",createTimeEnd);
        }
        //调用mapper的方法
        //调用mapper的方法
        Page<UserInfo> userInfoPage = baseMapper.selectPage(page, wrapper);
        //多线程平行流遍历
        userInfoPage.getRecords().parallelStream().forEach(item->{
            this.packageUserInfo(packageUserInfo(item));
        });
        return userInfoPage;
    }

    @Override
    public void lock(Long userId, Integer status) {
//        if (status == 0 || status == 1){
//            UserInfo userInfo = new UserInfo();
//            userInfo.setId(userId);
//            userInfo.setStatus(status);
//            baseMapper.updateById(userInfo);
//        }
        if(status.intValue() == 0 || status.intValue() == 1) {
            UserInfo userInfo = baseMapper.selectById(userId);//高并发适用
            userInfo.setStatus(status);
            this.updateById(userInfo);
        }
    }

    @Override
    public Map<String, Object> show(Long userId) {
        Map<String,Object> map = new HashMap<>();
        //根据userid查询用户信息
        UserInfo userInfo = this.packageUserInfo(baseMapper.selectById(userId));
        map.put("userInfo",userInfo);
        //根据userid查询就诊人信息
        List<Patient> patientList = patientService.findAllPatientById(userId);
        map.put("patientList",patientList);
        return map;
    }

    //认证审批  2通过  -1不通过
    @Override
    public void approval(Long userId, Integer authStatus) {
        if (authStatus.intValue() == 2 || authStatus.intValue() == -1) {
            UserInfo userInfo = baseMapper.selectById(userId);
            userInfo.setAuthStatus(authStatus);
            baseMapper.updateById(userInfo);
        }
    }

    //编号变成对应值封装 TODO
    private UserInfo packageUserInfo(UserInfo userInfo){
        //处理认证状态编码
        userInfo.getParam().put("authStatusString",AuthStatusEnum.getStatusNameByStatus(userInfo.getAuthStatus()));
        //处理用户状态 0  1
        String statusString = userInfo.getStatus().intValue()==0 ?"锁定" : "正常";
        userInfo.getParam().put("statusString",statusString);
        return userInfo;
    }
}