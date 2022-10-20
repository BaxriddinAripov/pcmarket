package uz.pdp.appspringpcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appspringpcmarket.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
