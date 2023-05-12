package D20PTIT.hoidap.controller;


import D20PTIT.hoidap.model.Question;
import D20PTIT.hoidap.model.Tag;
import D20PTIT.hoidap.model.User;
import D20PTIT.hoidap.repository.TagRepository;
import D20PTIT.hoidap.repository.UserRepository;
import com.amazonaws.services.dynamodbv2.xspec.S;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/tag", produces = "application/json")
@CrossOrigin(origins = "*")
public class TagRestController {
    private TagRepository tagRepo;
    @Autowired
    EntityLinks entityLinks;

    @Autowired
    public TagRestController(TagRepository tagRepo) {
        this.tagRepo = tagRepo;
    }

    @GetMapping("/search")
    public List<Tag> searchProducts(@RequestParam String input) {
        System.out.println("dang o day");
        System.out.println(input);
        List<Tag> tags = tagRepo.searchTags(input);
        for (Tag tag : tags) {
            tag.setQuestions(null);
        }

        for (Tag tag : tags) {
            System.out.println(tag);
        }
        return tags;
    }
}
