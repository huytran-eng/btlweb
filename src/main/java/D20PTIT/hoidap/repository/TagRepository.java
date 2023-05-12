package D20PTIT.hoidap.repository;

import D20PTIT.hoidap.model.Question;
import D20PTIT.hoidap.model.Tag;
import D20PTIT.hoidap.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {
    @Query("SELECT t FROM Tag t WHERE " +
            "t.tagName LIKE CONCAT('%',:query, '%')" +
            "Or t.tagDescription LIKE CONCAT('%', :query, '%')")
    List<Tag> searchTags(String query);

    Page<Tag> findAll(Pageable pageable);

    Optional<Tag> findByTagName(String tagName);
}
