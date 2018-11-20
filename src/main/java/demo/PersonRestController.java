package demo;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persons")
public class PersonRestController {

  private static final Logger LOG = LoggerFactory.getLogger(PersonRestController.class);

  private final PersonRepository personRepository;

  private final ObjectMapper objectMapper;

  public PersonRestController(PersonRepository personRepository,
      ObjectMapper objectMapper) {
    this.personRepository = personRepository;
    this.objectMapper = objectMapper;
  }

  @GetMapping
  @Transactional(readOnly = true)
  public void persons(HttpServletResponse response) throws Exception {
    response.setContentType("application/json");
    JsonGenerator g = objectMapper.getFactory().createGenerator(response.getOutputStream());
    g.writeStartObject();
    g.writeArrayFieldStart("persons");
    personRepository.all().forEach(person -> {
      try {
        objectMapper.writeValue(g, person);
      } catch (IOException e) {
        // ignore
      }
    });
    g.writeEndArray();
    g.writeEndObject();
    g.close();
  }
}
