package D20PTIT.hoidap.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name="question")
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String questionId;

    @NotEmpty(message = "title cannot be empty.")
    private String title;

    @Column(name = "content", columnDefinition = "text", length = 65536, nullable = false)
    @NotBlank(message = "Question content can't be empty")
    private String content;


    private Date createdAt;


    private int viewsNo;
    @ManyToMany(cascade = {
            CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, CascadeType.DETACH})

    @JoinTable(
            name = "question_upvote",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> upvote;
    @ManyToMany(cascade = {
            CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "question_downvote",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> downvote;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection
    @CollectionTable(
            name = "question_pic",
            joinColumns = @JoinColumn(name = "question_id")
    )
    private Set<String> questionPic;
    @NotEmpty(message = "cau hoi phai co it nhat 1 tag")

    @ManyToMany(cascade = {
            CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "question_tag",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;


    @PrePersist
    void placedAt() {

        this.createdAt = new Date();
    }

    public String getFormattedDate(LocalDateTime timestamp, String pattern) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return timestamp.format(formatter);
    }

    public void addUpvote(User user) {
        if (upvote == null) {
            upvote = new HashSet<>();
        }
        upvote.add(user);
    }

    public void removeDownvote(User user) {
        downvote.remove(user);
    }

    public void addDownvote(User user) {
        if (downvote == null) {
            downvote = new HashSet<>();
        }
        downvote.add(user);
    }

    public void removeUpvote(User user) {
        upvote.remove(user);
    }

}
