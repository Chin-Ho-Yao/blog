package com.yao.dao;

import com.yao.po.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Jack Yao on 2021/5/31 1:40 下午
 */

/*繼承JPa，Blog類型，Long主鍵。JpaSpecificationExecutor傳遞Blog*/
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor {
}
