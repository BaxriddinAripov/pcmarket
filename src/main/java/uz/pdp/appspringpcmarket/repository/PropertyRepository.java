package uz.pdp.appspringpcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appspringpcmarket.entity.Property;

public interface PropertyRepository extends JpaRepository<Property, Integer> {
}
