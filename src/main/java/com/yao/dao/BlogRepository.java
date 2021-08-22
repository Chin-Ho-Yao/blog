package com.yao.dao;

import com.yao.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Jack Yao on 2021/5/31 1:40 下午
 */

/*繼承JPa，Blog類型，Long主鍵。JpaSpecificationExecutor傳遞Blog*/
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor {
     /*#37首頁最新推薦表格數據獲取*/
    @Query("select b from Blog b where b.recommend = true ")/*這邊多一個true代表是推薦的博客*/
    List<Blog> findTop(Pageable pageable);

    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);
}
