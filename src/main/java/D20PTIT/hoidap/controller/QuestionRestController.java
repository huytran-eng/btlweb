package D20PTIT.hoidap.controller;


import D20PTIT.hoidap.model.Question;
import D20PTIT.hoidap.model.Tag;
import D20PTIT.hoidap.model.User;
import D20PTIT.hoidap.repository.QuestionRepository;
import D20PTIT.hoidap.repository.TagRepository;
import D20PTIT.hoidap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/question", produces = "application/json")
@CrossOrigin(origins = "*")
public class QuestionRestController {
    private final QuestionRepository questionRepo;
    private final UserRepository userRepo;

    @Autowired
    EntityLinks entityLinks;

    @Autowired
    public QuestionRestController(QuestionRepository questionRepo, UserRepository userRepo) {
        this.questionRepo = questionRepo;
        this.userRepo = userRepo;
    }

    @PutMapping("/up/{id}")
    public ResponseEntity<Integer> upvote(@PathVariable String id, Authentication authentication) {
        System.out.println("dang o day");
        System.out.println(authentication.getPrincipal());
        User u = (User) authentication.getPrincipal();
        User user = userRepo
                .findById(u.getUserId())
                .orElseThrow(() -> new RuntimeException("User with this email not found: "));
        Question question = questionRepo.findById(id).orElseThrow(() -> new RuntimeException("khong tim thay "));
        question.removeDownvote(user);
        question.addUpvote(user);
        questionRepo.save(question);
        int newVotes = question.getUpvote().size() - question.getDownvote().size();
        return new ResponseEntity<Integer>(newVotes, HttpStatus.OK);
    }
    @PutMapping("/down/{id}")
    public ResponseEntity<Integer> downvote(@PathVariable String id, Authentication authentication) {
        User u = (User) authentication.getPrincipal();
        User user = userRepo
                .findById(u.getUserId())
                .orElseThrow(() -> new RuntimeException("User with this email not found: "));
        Question question = questionRepo.findById(id).orElseThrow(() -> new RuntimeException("khong tim thay "));
        question.removeUpvote(user);
        question.addDownvote(user);
        questionRepo.save(question);
        int newVotes = question.getUpvote().size() - question.getDownvote().size();
        return new ResponseEntity<Integer>(newVotes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Question getQuestion(@PathVariable String id) {
        return questionRepo.findByIdWithTag(id);
    }
}
