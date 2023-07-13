package pl.gr.newspringsecurity.demo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @GetMapping
    public String get() {
        return "get:: admin controller";
    }
    @PostMapping
    public String post() {
        return "post:: admin controller";
    }
    @PutMapping
    public String put() {
        return "put:: admin controller";
    }
    @DeleteMapping
    public String delete() {
        return "delete:: admin controller";
    }
}


