package com.xpxp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xpxp.pojo.VO.MessageVO;
import com.xpxp.pojo.entity.Joinapplications;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JoinapplicationsMapper extends BaseMapper<Joinapplications> {
    List<MessageVO> getMessages(Integer id);
}
