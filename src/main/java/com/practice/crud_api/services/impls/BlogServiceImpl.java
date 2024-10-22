package com.practice.crud_api.services.impls;

import com.practice.crud_api.models.Blog;
import com.practice.crud_api.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private JpaRepository jpaRepository;

    @Override
    public List<Blog> getAll(){
        return jpaRepository.findAll();
    }

    @Override
    public void store(Blog blog){
        jpaRepository.save(blog);
    }

    @Override
    public Optional<Blog> findById(Long id){
        return jpaRepository.findById(id);
    }

    @Override
    public void deleteById(Long id){
        jpaRepository.deleteById(id);
    }
}
