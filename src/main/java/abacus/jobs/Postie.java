package abacus.jobs;

import abacus.domain.account.Account;
import abacus.domain.money.CurrencyCode;
import abacus.domain.money.CurrencyCodeFactory;
import abacus.domain.money.Money;
import abacus.domain.posting.Balance;
import abacus.domain.posting.Posting;
import abacus.persist.dao.AccountRepository;
import abacus.persist.dao.PostingRepository;
import abacus.persist.entities.AccountEntity;
import com.google.inject.persist.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.TemporalAmount;

public class Postie  {

    private Logger log = LoggerFactory.getLogger(Postie.class);

    @javax.inject.Inject
    private PostingRepository repo;

    @javax.inject.Inject
    private AccountRepository repoA;

    private static int call = 0;
    private static int ball = 0;

    @Transactional
    public void perform() {
        setBalances();

        ++call;
        log.info("Call#: " + call);

        int startPostingId = 1000 * call;

        for (int i = startPostingId; i <= startPostingId + 5; i++) {
            Posting p = new Posting();
            p.setId(i);
            p.setAccountId(1000);
            p.setBizDate(LocalDateTime.now());
            p.setValue(new Money(new BigDecimal(i), CurrencyCodeFactory.USD));
            String v = "A" + i;
            p.setDescription("FOO " + v);
            log.info(p.toString());
            repo.createOrUpdatePosting(p);
        }

    }

    public void setBalances()
    {
        LocalDate yday = LocalDate.now().minus(Period.ofDays(1));
        Money START = new Money(new BigDecimal(100), CurrencyCodeFactory.USD);
        for (Account a : repoA.getAccounts())
        {
            Balance b = new Balance();
            b.setAccountId(a.getId());
            b.setBizDate(yday);
            b.setValue(START);
            repo.createOrUpdateBalance(b);
        }
    }

    public void check()
    {
        for( Posting p : repo.getPostings())
        {
            log.info(p.toString());
        }

        for( Balance b : repo.getBalances())
        {
            log.info(b.toString());
        }

        log.info(repo.getPosting(1000).toString());

        //log.info(repo.getIDTBalance(1000, LocalDate.now()).toString());

        // log.info(repo.getBalance(1000).toString());

    }
}