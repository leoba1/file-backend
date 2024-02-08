package com.qiwenshare.file.dto.file;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;


@Data
public class TempFile {
    private int id;
    private String code;
    private String fileName;
    private Date time;
}
