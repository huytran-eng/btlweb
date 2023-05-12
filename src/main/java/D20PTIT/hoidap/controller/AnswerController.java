package D20PTIT.hoidap.controller;

import D20PTIT.hoidap.Service.StorageService;
import D20PTIT.hoidap.model.Answer;
import D20PTIT.hoidap.model.Question;
import D20PTIT.hoidap.model.User;
import D20PTIT.hoidap.repository.AnswerRepository;
import D20PTIT.hoidap.repository.QuestionRepository;
import D20PTIT.hoidap.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/answers")
@AllArgsConstructor
public class AnswerController {
    @Autowired
    private QuestionRepository questionRepo;

    @Autowired
    private AnswerRepository answerRepo;

    @Autowired
    private UserRepository userRepo;

    private StorageService storageService;

    @PostMapping
    public String createAnswer(@Valid @ModelAttribute Answer answer, @RequestParam("files") List<MultipartFile> multipartFiles, Authentication authentication) {
        String questionId = answer.getQuestion().getQuestionId();
        Question question = questionRepo.getOne(questionId);
        answer.setQuestion(question);
        User u = (User) authentication.getPrincipal();
        if (!multipartFiles.get(0).getOriginalFilename().isEmpty()) {

            answer.setAnswerPic(storageService.uploadFiles(multipartFiles));
        }
        User user = userRepo
                .findById(u.getUserId())
                .orElseThrow(() -> new RuntimeException("User with this email not found: "));
        question.setUser(user);
        answer.setUser(user);
        answerRepo.save(answer);
        return String.format("redirect:/question/%s", questionId);
    }

    @GetMapping
    public String findAll(Model model) {
        List<Answer> answers = answerRepo.findAll();
        model.addAttribute("answers", answers);
        return "answers/list";
    }

    @GetMapping("/edit/{id}")
    public String editAnswers(@PathVariable String id, Model model) {
        Answer answer = answerRepo.findById(id).orElseThrow(() -> new RuntimeException("answer of this question not found: "));
        model.addAttribute("answer", answer);
        return "answer/edit";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String saveAnswer(@Valid @ModelAttribute Answer answer) {
        answerRepo.save(answer);
        String questionId = answer.getQuestion().getQuestionId();
        answer.setCreatedAt(new Date());
        return String.format("redirect:/question/%s", questionId);
    }

    @DeleteMapping("/{id}")
    public String deleteAnswers(@PathVariable String id) {
        Answer answer = answerRepo.findById(id).orElseThrow(() -> new RuntimeException("answer of this question not found: "));
        String questionId = answer.getQuestion().getQuestionId();
        answerRepo.deleteById(id);
        return String.format("redirect:/question/%s", questionId);
    }

    @GetMapping("/up/{id}")
    public String upvote(@PathVariable String id, Authentication authentication) {
        Answer answer = answerRepo.findById(id).orElseThrow(() -> new RuntimeException("answer of this id not found: "));
        String questionId = answer.getQuestion().getQuestionId();
        User user = (User) authentication.getPrincipal();
        if (answer.getDownvoteNo().contains(user)) answer.removeDownvote(user);
        answer.addUpvoteNo(user);
        answerRepo.save(answer);
        return String.format("redirect:/question/%s", questionId);
    }

    @GetMapping("/down/{id}")
    public String downvote(@PathVariable String id, Authentication authentication) {
        Answer answer = answerRepo.findById(id).orElseThrow(() -> new RuntimeException("answer of this id not found: "));
        String questionId = answer.getQuestion().getQuestionId();
        User user = (User) authentication.getPrincipal();
        if (answer.getUpvoteNo().contains(user)) answer.removeUpvote(user);
        answer.addDownvoteNo(user);
        answerRepo.save(answer);
        return String.format("redirect:/question/%s", questionId);
    }

    @GetMapping("/correct/{id}")
    public String correctAnswer(@PathVariable String id) {
        Answer answer = answerRepo.findById(id).orElseThrow(() -> new RuntimeException("answer of this id not found: "));
        String questionId = answer.getQuestion().getQuestionId();
        for (Answer a : answerRepo.findByQId(questionId)) {
            if (!a.getAnswerId().equals(answer.getAnswerId())) {
                a.setIsCorrect(0);
            }
        }
        answer.correct();
        answerRepo.save(answer);
        return String.format("redirect:/question/%s", questionId);
    }

    @GetMapping("/wrong/{id}")
    public String falseAnswer(@PathVariable String id) {
        Answer answer = answerRepo.findById(id).orElseThrow(() -> new RuntimeException("answer of this id not found: "));
        String questionId = answer.getQuestion().getQuestionId();
        answer.wrong();
        answerRepo.save(answer);
        return String.format("redirect:/question/%s", questionId);
    }
}
