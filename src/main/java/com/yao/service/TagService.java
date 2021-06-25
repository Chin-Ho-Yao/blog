package com.yao.service;

import com.yao.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Jack Yao on 2021/5/29 3:45 下午
 */
public interface TagService {
    Tag saveTag(Tag tag);/*新增完返回Tag，新增要保存，用saveTag，傳過來的是Tag實體對象*/

    Tag getTag(Long id);
    /*通過名稱查詢Tag*/
    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);/*傳遞Pageable的對象*/

    List<Tag> listTag();/*不傳遞任何參數獲取所有tag*/

    List<Tag> listTag(String ids);/*最終返回List，傳遞逗號隔開的多個id*/

    /*index的tag處理*/
    List<Tag> listTagTop(Integer size);

    Tag updateTag(Long id, Tag tag);/*修改最終返回根據id查到的對象，然後再把對象按照新內容修改保存在數據庫，Tag就是修改參數的對象*/

    void deleteTag(Long id);/*刪除就返回一個空，根據Long id主鍵來刪除*/

}
/*再來實驗這個接口*/