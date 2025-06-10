package com.example.demo.controller;

    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/projects")
    public class ProjectController {

        // 创建项目
        @PostMapping
        public String createProject(@RequestParam String name, @RequestHeader("token") String token) {
            // 实现创建逻辑
            return "项目创建成功";
        }

        // 删除项目
        @DeleteMapping("/{projectId}")
        public String deleteProject(@PathVariable String projectId, @RequestHeader("token") String token) {
            // 实现删除逻辑
            return "项目删除成功";
        }

        // 查询项目
        @GetMapping("/{projectId}")
        public String getProject(@PathVariable String projectId, @RequestHeader("token") String token) {
            // 实现查询逻辑
            return "项目信息";
        }

        // 修改项目
        @PutMapping("/{projectId}")
        public String updateProject(@PathVariable String projectId, @RequestParam String name, @RequestHeader("token") String token) {
            // 实现修改逻辑
            return "项目修改成功";
        }
    }