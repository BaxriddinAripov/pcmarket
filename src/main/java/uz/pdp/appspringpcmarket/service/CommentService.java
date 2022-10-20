package uz.pdp.appspringpcmarket.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.appspringpcmarket.entity.Attachment;
import uz.pdp.appspringpcmarket.entity.Comment;
import uz.pdp.appspringpcmarket.payload.ApiResponse;
import uz.pdp.appspringpcmarket.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    private final AttachmentService attachmentService;

    public CommentService(CommentRepository commentRepository, AttachmentService attachmentService) {
        this.commentRepository = commentRepository;
        this.attachmentService = attachmentService;
    }

    public ApiResponse add(String title, String text, MultipartFile file) {
        Attachment attachment = null;
        if (!file.isEmpty()) {
            attachment = attachmentService.add(file);
        }

        try {
            Comment comment = new Comment();
            comment.setText(text);
            comment.setTitle(title);
            comment.setAttachment(attachment);
            commentRepository.save(comment);
            return new ApiResponse("comment qo'shildi", true);
        } catch (Exception e) {
            return new ApiResponse(e.getMessage(), false);
        }
    }

    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    public Comment getById(Integer commentId) {
        Optional<Comment> byId = commentRepository.findById(commentId);
        return byId.orElse(null);
    }

    public ApiResponse edit(Integer commentId, String title, String text, MultipartFile file) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (!optionalComment.isPresent())
            return new ApiResponse("comment topilmadi", false);

        Attachment attachment = null;
        if (!file.isEmpty()) {
            attachment = attachmentService.add(file);
        }

        try {
            Comment comment = optionalComment.get();
            comment.setText(text);
            comment.setTitle(title);
            comment.setAttachment(attachment);
            commentRepository.save(comment);
            return new ApiResponse("comment tahrirlandi", true);
        } catch (Exception e) {
            return new ApiResponse(e.getMessage(), false);
        }
    }

    public ApiResponse delete(Integer id) {
        try {
            commentRepository.deleteById(id);
            return new ApiResponse("comment o'chirildi", true);
        } catch (Exception e) {
            return new ApiResponse("xatolik", false);
        }
    }
}
