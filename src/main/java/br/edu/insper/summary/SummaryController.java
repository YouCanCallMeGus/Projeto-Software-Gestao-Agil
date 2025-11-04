package br.edu.insper.summary;

import br.edu.insper.balance.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SummaryController {

    @Autowired
    private SummaryService summaryService;

    @GetMapping("/summary")
    public List<Balance> getSummary(@RequestParam(name = "month") int month, @AuthenticationPrincipal Jwt jwt) {
        return summaryService.getByMonthAndEmail(month, jwt.getClaimAsString("https://stocks-insper.com/email"));
    }
}
