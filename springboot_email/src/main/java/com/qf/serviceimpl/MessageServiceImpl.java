package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.MessageMapper;
import com.qf.entity.Message;
import com.qf.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;
@Service
public class MessageServiceImpl implements IMessageService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public int sendRegisterMessage(Message message) {
        //创建一封邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        //创建一个Spring提供的邮件帮助对象
        MimeMessageHelper messageHelper = null;
        try {
            messageHelper = new MimeMessageHelper(mimeMessage, true);
            //设置发送方
            messageHelper.setFrom(message.getSendurl());

            //设置接收方
            //to - 普通接收方
            //cc - 抄送方
            //bcc - 密送方
            messageHelper.addTo(message.getReceiveurl(), "注册用户");

            //设置标题
            messageHelper.setSubject(message.getTitle());
            System.out.println(message.getReplycontent());
            if(message.getReplycontent()==null){
            //设置验证码
            messageHelper.setText("本次验证码为"+message.getCode());}
            //设置修改密码的回复地址
            else {
               messageHelper.setText(message.getReplycontent());
            }

            //设置时间
            messageHelper.setSentDate(new Date());

            //发送邮件
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return messageMapper.insert(message);
    }


}
