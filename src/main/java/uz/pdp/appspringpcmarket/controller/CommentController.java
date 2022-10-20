package uz.pdp.appspringpcmarket.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.appspringpcmarket.entity.Comment;
import uz.pdp.appspringpcmarket.payload.ApiResponse;
import uz.pdp.appspringpcmarket.service.AttachmentService;
import uz.pdp.appspringpcmarket.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;
    private final AttachmentService attachmentService;

    public CommentController(CommentService commentService, AttachmentService attachmentService) {
        this.commentService = commentService;
        this.attachmentService = attachmentService;
    }

    @PostMapping
    public ResponseEntity<?> addProductToBasket(@RequestParam String title, @RequestParam String text, @RequestParam MultipartFile file){
        ApiResponse apiResponse=commentService.add(title, text, file);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getAll(){
        List<Comment> all = commentService.getAll();
        return ResponseEntity.status(all.isEmpty()?409:200).body(all);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        Comment byId = commentService.getById(id);
        return ResponseEntity.status(byId==null?409:200).body(byId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> addProductToBasket(@PathVariable Integer id, @RequestParam String title, @RequestParam String text, @RequestParam MultipartFile file){
        ApiResponse edit = commentService.edit(id, title, text, file);
        return ResponseEntity.status(edit.isSuccess()?202:409).body(edit);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse delete = commentService.delete(id);
        return ResponseEntity.status(delete.isSuccess()?204:409).body(delete);
    }

    @GetMapping("/photo")
    public  HttpEntity<?> getPhoto(@RequestParam("attachmentId") Integer attachmentId){
        return attachmentService.get(attachmentId);
    }

}
