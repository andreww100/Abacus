package abacus.jobs;

import abacus.domain.account.Account;
import abacus.domain.money.CurrencyCode;
import abacus.persist.dao.AccountRepository;
import com.google.inject.persist.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticSetup {

    private Logger log = LoggerFactory.getLogger(StaticSetup.class);

    @javax.inject.Inject
    private AccountRepository repo;

    private static int call = 0;

    @Transactional
    public void perform() {
        ++call;
        log.info("Call#: " + call);

        int startAccountId = 1000 * call;

        for (int i = startAccountId; i <= startAccountId + 5; i++) {
            Account a = new Account();
            a.setId(i);
            String v = "A" + i;
            a.setName(v);
            a.setBaseCurrency(new CurrencyCode("USD"));
            repo.createOrUpdateAccount(a);
        }
    }

    public void check()
    {
        for( Account a : repo.getAccounts())
        {
            log.info(a.toString());
        }
    }
}
