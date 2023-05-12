package D20PTIT.hoidap.controller;

import D20PTIT.hoidap.model.Question;
import D20PTIT.hoidap.repository.QuestionRepository;
import D20PTIT.hoidap.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private QuestionRepository questionRepo;
    @Autowired
    private TagRepository tagRepo;

    @GetMapping("/")
    public String index(Model model, Authentication authentication) {

        List<Question> questions = questionRepo.findTop5ByOrderByViewsNo();
        model.addAttribute("questions", questions);
        System.out.println((questions));
        return "index";
    }
}
