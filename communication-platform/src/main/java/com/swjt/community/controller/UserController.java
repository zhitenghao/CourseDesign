package com.swjt.community.controller;


import cn.hutool.core.map.MapUtil;
import com.swjt.community.common.Dto.PassDto;
import com.swjt.community.common.Dto.UserDto;
import com.swjt.community.common.lang.Const;
import com.swjt.community.common.lang.Result;
import com.swjt.community.entity.User;
import com.swjt.community.service.UserService;
import com.swjt.community.utils.BeanUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DaiRui
 * @since 2021-10-27
 */
@RestController
@RequestMapping("/user")
@Api("用户接口")
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @PreAuthorize("hasRole('admin')")
    @GetMapping("/userListByAdmin")
    public Result userListByAdmin(){
        return Result.succ(userService.list());
    }

    @PreAuthorize("hasRole('normal')")
    @GetMapping("userList")
    public Result userList(){
        return Result.succ(userService.userList());
    }


    @PostMapping("/addUser")
    public Result addUser(@Validated @RequestBody UserDto userDto){
        if(userService.getUserByAccount(userDto.getUserAccount())!=null){
            return Result.fail(
                    MapUtil.builder()
                            .put("msg","添加用户失败，因为该账号已经存在！")
                            .put("用户账号",userDto.getUserAccount())
                            .map()
            );
        }
        User user = new User();
        //设置为正常状态
        user.setUserStatus(Const.STATUS_ON);
        BeanUtils.copyPropertiesBean(userDto,user);
        user.setAddTime(LocalDateTime.now());
        if(user.getUserBirthday()!=null){
            int age=user.getAddTime().getYear()-user.getUserBirthday().getYear();
            if(user.getUserBirthday().getMonthValue()>user.getAddTime().getMonthValue()){
                age--;
            }
            else if(user.getUserBirthday().getMonthValue()==user.getAddTime().getMonthValue()){
                if(user.getUserBirthday().getDayOfMonth()>user.getAddTime().getDayOfMonth()){
                    age--;
                }
            }
            user.setUserAge(age);
        }
        String password = passwordEncoder.encode(user.getUserPwd());
        user.setUserPwd(password);
        userService.save(user);
        return Result.succ(
                MapUtil.builder()
                .put("msg","添加用户成功")
                .put("用户账号",user.getUserAccount())
                .map()
        );
    }


    @GetMapping("/deleteUser/{userAccount}")
    public Result deleteUser(@PathVariable String userAccount){
        User userByAccount = userService.getUserByAccount(userAccount);
        userService.removeById(userByAccount.getId());
        return Result.succ(
                MapUtil.builder()
                .put("msg","删除成功")
                .put("用户账号",userAccount)
                .map()
        );
    }

    @PostMapping("/removeUser")
    public Result removeUser(@RequestBody String[] userAccounts){
        for (String userAccount:userAccounts) {
            User userByAccount = userService.getUserByAccount(userAccount);
            userByAccount.setUserStatus(Const.STATUS_OFF);
            userService.updateById(userByAccount);
        }
        return Result.succ(
                MapUtil.builder()
                        .put("msg","冻结成功！")
                        .put("用户账号",userAccounts.toString().concat(","))
                        .map()
        );
    }

    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody UserDto userDto){
        User user = new User();
        User userByAccount = userService.getUserByAccount(userDto.getUserAccount());
        BeanUtils.copyProperties(userDto,user);
        user.setId(userByAccount.getId());
        user.setUpdateTime(LocalDateTime.now());
        userService.updateById(user);
        return Result.succ("修改信息成功！");
    }

    @GetMapping("/userInfo")
    public Result userInfo(Principal principal) {

        User sysUser = userService.getUserByAccount(principal.getName());
        return Result.succ(sysUser);

//        return Result.succ(MapUtil.builder()
//                .put("account", sysUser.getUserAccount())
//                .put("name", sysUser.getUserName())
//                .put("avatar", sysUser.getUserAvatar())
//                .put("address", sysUser.getUserAddress())
//                .put("sex", sysUser.getUserSex())
//                .put("birthday", sysUser.getUserBirthday())
//                .map()
//        );
    }
    @PostMapping("/repass")
    @PreAuthorize("hasAuthority('sys:user:repass')")
    public Result repass(@RequestBody String userId) {

        User sysUser = userService.getById(userId);

        sysUser.setUserPwd(passwordEncoder.encode(Const.DEFULT_PASSWORD));
        sysUser.setUpdateTime(LocalDateTime.now());

        userService.updateById(sysUser);
        return Result.succ("重置密码成功");
    }

    @PostMapping("/updatePass")
    public Result updatePass(@Validated @RequestBody PassDto passDto, Principal principal) {

        User sysUser = userService.getUserByAccount(principal.getName());

        boolean matches = passwordEncoder.matches(passDto.getCurrentPass(), sysUser.getUserPwd());
        if (!matches) {
            return Result.fail("旧密码不正确");
        }

        sysUser.setUserPwd(passwordEncoder.encode(passDto.getPassword()));
        sysUser.setUpdateTime(LocalDateTime.now());

        userService.updateById(sysUser);
        return Result.succ("修改密码成功！");
    }
}
