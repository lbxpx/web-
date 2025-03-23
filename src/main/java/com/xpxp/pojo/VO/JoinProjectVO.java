package com.xpxp.pojo.VO;

import com.xpxp.pojo.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class JoinProjectVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String title;
    private String publisherName;
    private String description;
    private String image;
    private Integer publisherId;
    private String status;
    private List<Category> categories;
}
