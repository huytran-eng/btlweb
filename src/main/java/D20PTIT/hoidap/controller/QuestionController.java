package D20PTIT.hoidap.controller;

import D20PTIT.hoidap.model.Answer;
import D20PTIT.hoidap.model.Question;
import D20PTIT.hoidap.model.Tag;
import D20PTIT.hoidap.model.User;
import D20PTIT.hoidap.repository.AnswerRepository;
import D20PTIT.hoidap.repository.QuestionRepository;
import D20PTIT.hoidap.repository.TagRepository;
import D20PTIT.hoidap.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import D20PTIT.hoidap.Service.StorageService;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    private QuestionRepository questionRepo;
    private UserRepository userRepo;
    private StorageService storageService;
    private TagRepository tagRepo;
    private AnswerRepository answerRepo;


    @GetMapping
    public String findAll(Model model,
                          @RequestParam(value = "sort", required = false) String sort,
                          @RequestParam(defaultValue = "1") int page
    ) {
        Pageable paging = null;

        if (sort == null) {
            paging = PageRequest.of(page - 1, 3);
        } else {

            if (sort.equals("NEWEST"))
                paging = PageRequest.of(page - 1, 3, Sort.by("createdAt").descending());
            else if (sort.equals("POPULAR"))
                paging = PageRequest.of(page - 1, 3, Sort.by("viewsNo").descending());
            else if (sort.equals("VOTES"))
                paging = PageRequest.of(page - 1, 3, Sort.Direction.DESC, "downvote", "upvote");
        }
        List<Question> questions;
        Page<Question> pageQuestion = questionRepo.findAll(paging);
        questions = pageQuestion.getContent();
        model.addAttribute("sort", sort);
        model.addAttribute("questions", questions);
        model.addAttribute("pagequestions", pageQuestion);

        return "question/list";
    }


    @GetMapping("/add")
    public String addQuestionForm(Model model) {
        model.addAttribute(new Question());
        return "question/add";
    }

    @PostMapping("/add")
    public String saveQuestion(@Valid @ModelAttribute Question question, BindingResult errors,
                               @RequestParam("files") List<MultipartFile> multipartFiles, Authentication authentication) {
        if (errors.hasErrors()) {
            System.out.println(" co loi");
            System.out.println(errors);
            return "/question/add";
        }


        User u = (User) authentication.getPrincipal();
        if (!multipartFiles.get(0).getOriginalFilename().isEmpty()) {
            try {
                question.setQuestionPic(storageService.uploadFiles(multipartFiles));
            } catch (Exception e) {
                throw e;
            }
        }
        User user = userRepo
                .findById(u.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found "));
        question.setUser(user);


        String id = questionRepo.save(question).getQuestionId();
        return String.format("redirect:/question/%s", id);
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable String id, Model model, Principal principal) {
        Question question = questionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("khong tim thay"));
        questionRepo.updateView(id);
        Answer answer = new Answer();
        answer.setQuestion(question);
        System.out.println(question);
        model.addAttribute("question", question);
        model.addAttribute("answer", answer);

        List<Answer> answers = answerRepo.findByQId(id);
        model.addAttribute("answers", answers);
        return "question/view";
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(@PathVariable String id, Model model) {
        Question question = questionRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        model.addAttribute("question", question);
        return "question/edit";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String editQuestion(@Valid @ModelAttribute Question question, BindingResult errors) {
        if (errors.hasErrors()) {
            System.out.println(errors);
            return "/question/edit";
        }

        question.setCreatedAt(new Date());
        String updatedQuestionId = questionRepo
                .save(question)
                .getQuestionId();
        System.out.println(updatedQuestionId);
        return "redirect:/question/" + updatedQuestionId;
    }

    @GetMapping("/tag/{tagName}")
    public String questionWithTag(@PathVariable("tagName") String tagName, Model model, @RequestParam(defaultValue = "0") int page) {
        Tag tag = tagRepo.findByTagName(tagName).orElseThrow(() -> new RuntimeException("User with this email not found: "));
        Pageable paging = PageRequest.of(page == 0 ? page : page - 1, 5);
        Page<Question> pageQuestion = questionRepo.searchWithTag(paging, tag);

        List<Question> questions = pageQuestion.getContent();
        System.out.println(pageQuestion.getTotalPages());
        System.out.println(questions);
        model.addAttribute("tag", tag);
        model.addAttribute("questions", questions);
        model.addAttribute("pagequestions", pageQuestion);
        return "question/withTag";
    }


    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable String id, Authentication authentication) {
//        User u = (User) authentication.getPrincipal();
//        if(!u.getPosition().equals("admin")){
//            return "redirect:/";
//        }
        questionRepo.deleteById(id);
        return "redirect:/";
    }
}
