package com.practice.crud_api.controllers;

import com.practice.crud_api.handlers.ApiResponse;
import com.practice.crud_api.models.Blog;
import com.practice.crud_api.services.BlogService;
import org.apache.catalina.loader.ResourceEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Blog>>> get()
    {
        List<Blog> blogs = blogService.getAll();
        ApiResponse<List<Blog>> response = new ApiResponse<>(200, "Blogs Retrieved SUccessfully", blogs);

        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public  ResponseEntity<ApiResponse<String>> store(@RequestBody Blog blog)
    {
        blogService.store(blog);
        ApiResponse<String> response = new ApiResponse<>(201, "Blog Created", "Blog Created Succesfully");

        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public  ResponseEntity<ApiResponse<Blog>> findById(@PathVariable Long id)
    {
        return blogService.findById(id)
                .map(blog -> new ResponseEntity<>(new ApiResponse<>(200, "Blog Found", blog), HttpStatus.OK))
                .orElse(new ResponseEntity<>(new ApiResponse<>(404, "Blog Not Found", null), HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id)
    {
        try{
            blogService.deleteById(id);
            ApiResponse<String> response = new ApiResponse<>(200, "Blog Deleted", "Blog ID " + id+ " deleted Successfully");
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (EmptyResultDataAccessException e){
            ApiResponse<String> response = new ApiResponse<>(404, "Blog Not Found","Blog ID " + id+ " Not Found");
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e){
            ApiResponse<String> response = new ApiResponse<>(500, "Deletion Error", "Error Deleting blog id with ID "+id+ ": "+e.getMessage());
            return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/id/{id}")
    public ResponseEntity<ApiResponse<?>> updateById(@PathVariable Long id, @RequestBody Blog newBlog)
    {
        Blog oldBlog = blogService.findById(id).orElse(null);

        if(oldBlog != null){
            oldBlog.setTitle(newBlog.getTitle());
            oldBlog.setBody(newBlog.getBody());

            blogService.store(oldBlog);
            ApiResponse<Blog> response = new ApiResponse<>(200, "Blog Updated", oldBlog);

            return  new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            String message = "Blog Id "+id+ "not found.";
            ApiResponse<String> response = new ApiResponse<>(200, "Not Found", message);

            return  new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
