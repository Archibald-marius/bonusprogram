package partnership_program.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import partnership_program.models.SiteUser;
import partnership_program.models.Transactions;
import partnership_program.services.BonusProgramService;
import partnership_program.services.TransactionService;
import partnership_program.services.UserService;

@RestController


@Controller
public class HomeController {

 @Autowired
 private TransactionService transactionService;

 @Autowired
 private UserService userService;

 @Autowired
 private BonusProgramService bonusProgramService;

//    @RequestMapping
//    public String index() {
//        return "Greetings from Spring Boot!";
//    }



    @RequestMapping(value = "/")
    public String home() {

        SiteUser siteUser = new SiteUser();
        userService.save(siteUser);
        SiteUser siteUser1 = new SiteUser();
        siteUser1.setFirst_line(siteUser.getId());
        userService.save(siteUser1);
        SiteUser siteUser2 = new SiteUser();
        siteUser2.setFirst_line(siteUser1.getId());
        userService.save(siteUser2);
        SiteUser siteUser3 = new SiteUser();
        siteUser3.setFirst_line(siteUser2.getId());
        userService.save(siteUser3);
        return "Done!";
    }

    // апі для транзакцій по типу http://localhost:8080/send?userid=112&amountSpend=200.0
    @RequestMapping(value = "/send")
    public String send(@RequestParam String userid, @RequestParam String amountSpend) {
        Transactions transactions = new Transactions();
        transactions.setUserId(new Long(userid));
        transactions.setAmountSpend(new Double(amountSpend));
        transactionService.save(transactions);
        return "Done!";
    }

}