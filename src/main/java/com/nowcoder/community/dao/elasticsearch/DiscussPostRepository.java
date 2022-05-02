package com.nowcoder.community.dao.elasticsearch;

import com.nowcoder.community.entity.DiscussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * TODO
 *
 * @author wuzexin
 * @created 2022/5/2 14:28
 */

// repository是spring提供的针对于数据访问层的注解，而mapper是mybatis提供的
@Repository
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost, Integer> {  // <接口处理的实体类，实体类主键类型>

}
