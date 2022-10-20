package uz.pdp.appspringpcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appspringpcmarket.entity.Category;

@RepositoryRestResource(path = "category", collectionResourceRel = "list")
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
