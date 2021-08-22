package com.yao.service;

import com.yao.dao.TagRepository;
import com.yao.po.Tag;
import com.yao.web.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jack Yao on 2021/5/29 3:52 下午
 */
/*實現剛剛定義好的TagService.java*/
    /*還有數據庫操作dao*/
@Service  /*註解*/
public class TagServiceImpl implements TagService{

    /*注入TagRepository*/
    @Autowired
    private TagRepository tagRepository;

    @Transactional/*增刪改查都要加這個*/
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);/*直接調用*/
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.findOne(id);/*根據id查詢*/
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);/*分頁查詢，springboot就有jpa能用了，傳遞Pageable就會查詢封裝Page類型的對象*/
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    /*index的tag處理*/
    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");/*sort由DESC排序依照tags.size*/
        Pageable pageable = new PageRequest(0,size,sort);/*選擇第一頁的size按照sort排序*/
        return tagRepository.findTop(pageable);
    }

    @Override
    public List<Tag> listTag(String ids) {//現在id是1,2,3，所以把id轉換成字符串如下
        return tagRepository.findAll(converToList(ids));
    }

    /*老師寫的方法*/
    private List<Long> converToList(String ids){
        List<Long> list = new ArrayList<>();
        /*非空判斷*/
        if(!"".equals(ids) && ids != null){
            /*把字串轉換成數組*/
            String[] idarray = ids.split(",");
            for (int i = 0;i < idarray.length;i++){
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag){
        Tag t = tagRepository.findOne(id);
        if (t == null){
            throw new NotFoundException("不存在該標籤");
        }
        BeanUtils.copyProperties(tag,t);
        return tagRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.delete(id);/*根據id刪除*/

    }
}
/*以上搞定就是web層了*/