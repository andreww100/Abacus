package abacus.jobs;

import abacus.domain.account.Account;
import abacus.domain.money.CurrencyCodeFactory;
import abacus.domain.money.Money;
import abacus.domain.posting.Balance;
import abacus.domain.posting.Posting;
import abacus.persist.dao.AccountRepository;
import abacus.persist.dao.BizDateDAO;
import abacus.persist.dao.PostingRepository;
import com.google.inject.persist.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

public class PostingJob {

    private static int call = 0;
    private static int ball = 0;
    private Logger log = LoggerFactory.getLogger(PostingJob.class);
    @javax.inject.Inject
    private PostingRepository repo;
    @javax.inject.Inject
    private AccountRepository repoA;
    @javax.inject.Inject
    private BizDateDAO repoBizDate;

    @Transactional
    public void perform() {
        setBalances();

        ++call;
        log.info("Call#: " + call);

        int startPostingId = 1000 * call;

        LocalDate bizDate = repoBizDate.getCurBizDate();

        for (int i = startPostingId; i <= startPostingId + 5; i++) {
            Posting p = new Posting();
            p.setId(i);
            p.setAccountId(1000);

            p.setPostingDate(bizDate);

            LocalDateTime dt = LocalDateTime.now();
            p.setTransactionDate(dt);

            p.setValue(new Money(new BigDecimal(i), CurrencyCodeFactory.USD));
            String v = "A" + i;
            p.setDescription("FOO " + v);
            log.info(p.toString());
            repo.createOrUpdatePosting(p);
        }

    }

    public void setBalances() {
        LocalDate yday = repoBizDate.getPrevBizDate();
        Money START = new Money(new BigDecimal(100), CurrencyCodeFactory.USD);
        for (Account a : repoA.getAccounts()) {
            Balance b = new Balance();
            b.setAccountId(a.getId());
            b.setBizDate(yday);
            b.setValue(START);
            repo.createOrUpdateBalance(b);
        }
    }

    public void check() {
        for (Posting p : repo.getPostings()) {
            log.info(p.toString());
        }

        for (Balance b : repo.getBalances()) {
            log.info(b.toString());
        }

        log.info(repo.getPosting(1000).toString());

        List<Balance> totals = repo.getIDTBalance(1000);
        for (Balance total : totals) {
            log.info(total.toString());
        }
    }
}