package abacus.jobs;

import abacus.domain.account.Account;
import abacus.domain.money.CurrencyCode;
import abacus.domain.money.CurrencyCodeFactory;
import abacus.domain.money.Money;
import abacus.domain.posting.Posting;
import abacus.persist.dao.AccountRepository;
import abacus.persist.dao.PostingRepository;
import com.google.inject.persist.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class Postie  {

    private Logger log = LoggerFactory.getLogger(Postie.class);

    @javax.inject.Inject
    private PostingRepository repo;

    private static int call = 0;

    @Transactional
    public void perform() {
        ++call;
        log.info("Call#: " + call);

        int startPostingId = 1000 * call;

        for (int i = startPostingId; i <= startPostingId + 5; i++) {
            Posting p = new Posting();
            p.setId(i);
            p.setAccountId(i);
            String v = "A" + i;
            p.setValue(new Money(new BigDecimal(i), CurrencyCodeFactory.USD));
            p.setDescription("FOO " + v);
            log.info(p.toString());
            repo.createOrUpdatePosting(p);
        }
    }

    public void check()
    {
        for( Posting p : repo.getPostings())
        {
            log.info(p.toString());
        }

        log.info(repo.getPosting(1000).toString());
    }
}