package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String sendurl;

    private String receiveurl;

    private String title;

    private Integer code;

    private String replycontent;
}
