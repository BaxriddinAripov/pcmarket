package uz.pdp.appspringpcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appspringpcmarket.entity.Character;

import java.util.Optional;

@RepositoryRestResource(path = "character", collectionResourceRel = "list")
public interface CharacterRepository extends JpaRepository<Character, Integer> {
    Optional<Character> findByName(String name);
}
