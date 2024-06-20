package example.hao.stream.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("`stream_info`")
public class Stream {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String streamId;
    private String streamUrl;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Date createTime;
}