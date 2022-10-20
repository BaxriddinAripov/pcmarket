package uz.pdp.appspringpcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appspringpcmarket.entity.Message;

@RepositoryRestResource(path = "massage", collectionResourceRel = "list")
public interface MessageRepository extends JpaRepository<Message, Integer> {
}
