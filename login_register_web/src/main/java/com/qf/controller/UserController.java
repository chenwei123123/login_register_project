package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Message;
import com.qf.entity.User;
import com.qf.service.IMessageService;
import com.qf.service.IUserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/user")
public class UserController {
    /*rpc远程调用服务*/
    @Reference
    private IUserService userService;

    @Reference
    private IMessageService messageService;
    @RequestMapping("/tologin")
    public String tologin(){
        return "login";
    }
    @RequestMapping("/toregister")
    public String toregister(){
        return "register";
    }
    /*发送注册验证码*/
    @ResponseBody
    @RequestMapping("/registermessage")
    public Object sendRegisterMessage(@RequestBody User user, HttpServletRequest request){
        System.out.println(user);
        Message message = new Message();
        message.setSendurl("123456chenwei123@sina.cn");
        message.setTitle("发送验证码");
        message.setReceiveurl(user.getEmail());
        Random random = new Random();
        Integer code= 1000+ 9*random.nextInt(1000);
        message.setCode(code);
        request.getSession().setAttribute("code",message.getCode());
        messageService.sendRegisterMessage(message);
        return true;
    }
    /*注册用户*/
    @RequestMapping("/register")
    public String register(User user, Message message, HttpServletRequest request, Model model){
        System.out.println("11111");
        System.out.println(user+"==="+message);
        if(request.getSession().getAttribute("code").equals(message.getCode())){
          userService.insertUser(user);
          model.addAttribute("msg","");
          return "login";
        }
        model.addAttribute("msg","注册失败,请重新注册");
        return "register";
    }
    /*跳转到重置密码填写用户名页面*/
    @RequestMapping("/tofindPsw")
    public Object tofindPsw(){
       return "findPsw";
    }
    /*根据填写的用户名得到邮箱地址发送邮箱*/
    @RequestMapping("/findPsw")
    public Object findPsw(String username){
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
      List<User> users=userService.getUserByName(map);
      if(users==null){
          return "findPsw";
      }
        User user = users.get(0);
        Message message = new Message();
        message.setSendurl("123456chenwei123@sina.cn");
        message.setTitle("密码重置");
        message.setReceiveurl(user.getEmail());
        message.setReplycontent("请点击<a href='http://10.36.138.188:8080/user/togetPsw?username="+username+" '>这里</a>找回密码~");
        messageService.sendRegisterMessage(message);
        return "login";
    }
    /*重置密码用户名初始化*/
    @RequestMapping("/togetPsw")
    public Object togetPsw(@RequestParam String username, Model model){
        model.addAttribute("username" ,username);
        return  "updatePsw";
    }
    /*根据隐藏的用户名和填写密码修改用户密码*/
    @RequestMapping("/updatePsw")
    public Object updatePsw(User user){
        Map<String,Object> map = new HashMap<>();
        map.put("username",user.getUsername());
        List<User> users = userService.getUserByName(map);
        User user1 = users.get(0);
        user1.setPassword(user.getPassword());
        userService.updateUser(user1);
        return  "login";
    }
    /*根据填写的用户名和密码用户登录*/
    @RequestMapping("/login")
    public Object login(@RequestParam String username,@RequestParam String password){
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        List<User> users = userService.getUserByName(map);
        if(users!=null){
            return  "success11";
        }
        return  "login";
    }
}
