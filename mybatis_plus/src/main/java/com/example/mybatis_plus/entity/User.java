package com.example.mybatis_plus.entity;

import com.example.mybatis_plus.config.annotation.SensitiveFiled;
import com.example.mybatis_plus.config.constants.EncryptTypeEnum;
import lombok.Data;

@Data
public class User {
    private Long id;
    @SensitiveFiled(EncryptTypeEnum.SM2)
    private String name;
    private Integer age;
    private String email;
}
