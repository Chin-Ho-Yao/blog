package com.yao.service;

import com.yao.dao.TypeRepository;
import com.yao.po.Type;
import com.yao.web.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Jack Yao on 2021/5/29 3:52 下午
 */
/*實現剛剛定義好的TypeService.java*/
    /*還有數據庫操作dao*/
@Service/*註解*/
public class TypeServiceImpl implements TypeService{

    /*注入TypeRepository*/
    @Autowired
    private TypeRepository typeRepository;

    @Transactional/*增刪改查都要加這個*/
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);/*直接調用*/
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.findOne(id);/*根據id查詢*/
    }

    /*調用typeRepsitory，根據名字查詢數據的方法*/
    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
        /*分頁查詢，springboot就有jpa能用了，傳遞Pageable就會查詢封裝Page類型的對象*/
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.findOne(id);
        if (t == null){
            throw new NotFoundException("不存在該類型");
        }
        /*第一個是source，把type得值複製到t*/
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);/*更新，先查詢，然後更新，t裡面有id所以會進行更新*/
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.delete(id);/*根據id刪除*/
    }
}
/*以上搞定就是web層了*/