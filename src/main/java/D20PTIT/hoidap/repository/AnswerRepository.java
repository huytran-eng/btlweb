package D20PTIT.hoidap.repository;

import D20PTIT.hoidap.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,String> {
    @Query("SELECT a FROM Answer a WHERE a.question.questionId = ?1")
    List<Answer> findByQId(String id);

    @Modifying
    @Query("UPDATE Answer a SET a.content = :content WHERE a.id = :id")
    void updateAnswer(@Param(value = "id") String id, @Param(value = "content") String content);
}
