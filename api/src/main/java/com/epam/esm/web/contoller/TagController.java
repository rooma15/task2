package com.epam.esm.web.contoller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.web.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

  private final TagService tagService;

  @Autowired
  public TagController(TagService tagService) {
    this.tagService = tagService;
  }

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  public List<TagDto> getAllTags() {
    return tagService.findAll();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
  public TagDto getTag(@PathVariable int id) {
    return tagService.findById(id);
  }

  @RequestMapping(
      method = RequestMethod.POST,
      produces = "application/json",
      consumes = "application/json")
  public TagDto createTag(@RequestBody TagDto tag) {
    return tagService.save(tag);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTag(@PathVariable int id) {
    tagService.delete(id);
  }
}
