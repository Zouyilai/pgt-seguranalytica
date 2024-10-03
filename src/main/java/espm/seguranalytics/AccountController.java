package espm.seguranalytics;

// import java.math.BigDecimal;
// import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.util.Date;
// import java.util.List;
// import java.util.UUID;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AccountController {
    
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello ESPM";
    }

    // @GetMapping("/account")
    // public List<AccountOut> findAll();

    // @PostMapping("/account")
    // public void create(@RequestBody AccountIn in);
}