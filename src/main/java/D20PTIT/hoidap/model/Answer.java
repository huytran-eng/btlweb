package D20PTIT.hoidap.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "answer")
@AllArgsConstructor
@NoArgsConstructor(force = true)

public class Answer {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String answerId;

    @Lob
    private String content;

    @Column(name = "created_at")
    private Date createdAt;
    private int isCorrect = 0;

    @ManyToMany(cascade = {
            CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "answer_upvote",
            joinColumns = @JoinColumn(name = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> upvoteNo;

    @ManyToMany(cascade = {
            CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "answer_downvote",
            joinColumns = @JoinColumn(name = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> downvoteNo;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ElementCollection
    @CollectionTable(
            name = "answer_pic",
            joinColumns = @JoinColumn(name = "answer_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"answer_id", "answerPic"})
    )
    private Set<String> answerPic;
    public String getFormattedDate(LocalDateTime timestamp, String pattern) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return timestamp.format(formatter);
    }

    @PrePersist
    void placedAt() {
        this.createdAt = new Date();
    }

    public int vote() {
        if (downvoteNo != null && upvoteNo != null) {
            return upvoteNo.size() - downvoteNo.size();
        }
        return 0;
    }

    public void addDownvoteNo(User user) {
        if (downvoteNo == null) {
            downvoteNo = new HashSet<>();
        }
        downvoteNo.add(user);
    }

    public void removeDownvote(User user) {
        downvoteNo.remove(user);
    }

    ;

    public void addUpvoteNo(User user) {
        if (upvoteNo == null) {
            upvoteNo = new HashSet<>();
        }
        upvoteNo.add(user);
    }

    public void removeUpvote(User user) {
        upvoteNo.remove(user);
    }



    public void correct() {
        isCorrect = 1;
    }

    public void wrong() {
        isCorrect = 0;
    }
}
