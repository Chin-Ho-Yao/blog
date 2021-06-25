package com.yao.dao;

import com.yao.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Jack Yao on 2021/5/29 3:57 下午
 */
public interface TypeRepository extends JpaRepository<Type,Long> {

    /*查詢Type*/
    Type findByName(String name);

    /*需要按照分類對應的博客數目大小進行排序，需要根據blog的list的size去排序，根據分頁來查，因為分頁本身有排序，也有條目數*/
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
