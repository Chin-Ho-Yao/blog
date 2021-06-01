package com.yao.service;

import com.yao.dao.BlogRepository;
import com.yao.po.Blog;
import com.yao.po.Type;
import com.yao.web.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack Yao on 2021/5/31 1:39 下午
 */

@Service/*標記他為service層*/
public class BlogServiceImpl implements BlogService{

    /*@Autowired注入blogRepository*/
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {

        /*查詢主鍵id，查詢對象返回*/
        return blogRepository.findOne(id);
    }

    /*分頁動態查詢方式，較複雜最後處理*/
    /*根據條件動態組合，三個條件可能傳可能不傳，根據不同組合查詢數據戶，就是動態查詢*/
    /*傳統做法會根據空不空去處理*/
    /*Jpa有幫我們封裝好這種高級查詢，可以直接把接口拿過來*/
    /*BlogRepository要先繼承JpaSpecificationExecutor*/
    @Override
    public Page<Blog> listBlog(Pageable pageable, Blog blog) {
        /*new出來使用，這是傳遞的第一個參數*/
        return blogRepository.findAll(new Specification<Blog>() {
            /*條件動態組合在這裡處理，new出來就自動會給這個方法，就是處理動態查詢條件*/
            /*root就是要查詢的對象也就是Blog，然後映射成Root*/
            /*從Root可以獲取表的屬性字串*/
            /*CriteriaQuery，可以把條件放進來，簡寫cq*/
            /*CriteriaBuilder就是設置具體條件的表達式，模糊查詢like也可以，方便起見用cb簡寫*/
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                /*處理具體動態查詢條件，等等組合條件放在List裡面*/
                List<Predicate> predicates = new ArrayList<>();
                /*判斷如果title不為空，非空判斷。前面也是如果不為空*/

                if (!"".equals(blog.getTitle()) && blog.getTitle() != null){
                /*有值傳進來，在組織條件放在集合裡面*/
                    /*add到predicates，他是我們要封裝好的條件，這邊用like查詢*/
                    /*第一個值是查詢對象的屬性的名字，透過get拿到String類型的"title"*/
                    /*第二個是傳遞過來的屬性的值*/
                    /*like查詢不能直接把值給他，要拼接上"%"，這樣才是有效的like*/
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                /*傳遞分類，傳遞的是type對象，是根據id值判斷*/
                if (blog.getType().getId() != null){
                    /*非空一樣add近來，拿到type對象裡面的id，再把blog對象get*/
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getType().getId()));
                    /*這樣就構造好了這個分類*/
                }
                /*推薦，布林值，這邊只判斷true就可以了*/
                /*blog.isRecommend()本身就是返回布林值。<>裡面用Boolean對象*/
                if (blog.isRecommend()) {
                    /*blog.isRecommend()實際上要傳過來的值*/
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                /*以上就是三組動態組合，成立的會加到predicates裡面，根據組合條件完成自動拼接動態查詢sql語句*/
                /*這個做法在jpa裡面有這規範，spring boot直接封裝直接使用*/

                /*之後用cq進行查詢。where跟sql語法差不多意思，裡面傳的是條件的數組，所以要把現在做好的List轉數組*/
                /*這邊轉成Predicate數組，其大小為predicates.size()*/
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
                /*service層處理好再來是web層*/
            }
            /*第二個參數是*/
        }, pageable);
    }


    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    /*新增一樣要先查詢*/
    @Override
    public Blog updateBlog(Long id, Blog blog) {

        /*根據Id查詢出來*/
        Blog b = blogRepository.findOne(id);
        /*如果對象為空，就是對象不存在，所以拋出錯誤，給提示信息*/
        if (b == null){
            throw new NotFoundException("不存在該類型");
        }
        /*否則就是存在，把新的內容修改屬性，把blog屬性給予b，然後保存b就好*/
        BeanUtils.copyProperties(blog,b);
        return blogRepository.save(b);
    }

    /*刪除最簡單*/
    @Override
    public void deleteBlog(Long id) {
        blogRepository.delete(id);
    }
}
