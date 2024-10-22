package com.practice.crud_api.services;


import com.practice.crud_api.models.Blog;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BlogService {

    List<Blog> getAll();

    void store(Blog blog);

    Optional<Blog> findById(Long id);

    void deleteById(Long id);
}
