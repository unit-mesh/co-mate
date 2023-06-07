package cc.unitmesh.untitled.demo.controller;

import cc.unitmesh.untitled.demo.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class BlogController {
    BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/blog/get")
    public String get() {
        return "";
    }
}
