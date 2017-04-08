package abacus.rest;

import abacus.domain.account.Account;
import abacus.domain.money.CurrencyCodeFactory;
import abacus.persist.dao.AccountDAO;
import abacus.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// WE MUST USE javax.inject.Inject NOT com.google.inject.Inject;
// This is because the Jersey Servlet is using HK2 not Guice
import javax.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * @See http://localhost:8080/abacus/resources/account
 */
@Path("/account")
public class AccountResource {

    private Logger log = LoggerFactory.getLogger(AccountResource.class);

    @Inject
    private AccountDAO daoAccount;

    public AccountResource() {
        log.info("CREATED");
    }

    // The Java method will process HTTP GET requests
    @GET
    @Produces("text/plain")
    public String getAccountText() {
        Account a = getAccountByID(1000);
        return "Found" + StringUtil.safeToString(a);
    }

    // http://localhost:8080/abacus/resources/account/count
    @GET
    @Path("/count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        return "999";
    }

    public AccountDAO getDaoAccount() {
        return daoAccount;
    }

    public void setDaoAccount(AccountDAO daoAccount) {
        this.daoAccount = daoAccount;
    }

    // HELPERS

    private Account getAccountByID(long accountId) {
        Account a = null;
        if (this.daoAccount != null) {
            a = this.daoAccount.getAccount(accountId);
        } else {
            a = new Account();
            a.setId(999);
            a.setName("DAO not found");
            a.setBaseCurrency(CurrencyCodeFactory.GBP);
        }
        return a;
    }
}
