package hao.stream.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("`stream_info`")
public class Stream {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String streamId;
    private String streamUrl;
    private Long createTime;
}