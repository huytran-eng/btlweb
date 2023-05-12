package D20PTIT.hoidap.repository;

import D20PTIT.hoidap.model.Question;
import D20PTIT.hoidap.model.Tag;
import D20PTIT.hoidap.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String>, PagingAndSortingRepository<Question, String> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE Question q SET q.viewsNo = q.viewsNo+1 WHERE (q.questionId =?1) ")
    void updateView(String id);

    Page<Question> findAll(Pageable pageable);


    @Modifying
    @Query("update Question q set q.title = :title,q.content=:content where q.id = :id")
    void update(@Param(value = "id") String id, @Param(value = "title") String title, @Param(value = "content") String content);

    @Query("select q from Question q  left join fetch q.tags where q.questionId = ?1")
    Question findByIdWithTag(String id);

    @Query("SELECT q FROM Question q WHERE :tag MEMBER OF q.tags")
    Page<Question> searchWithTag(Pageable pageable, @Param(value = "tag") Tag tag);

    List<Question> findTop5ByOrderByViewsNo();
}
