package abacus.persist.dao;

import abacus.domain.account.Account;
import abacus.persist.entities.AccountEntity;
import abacus.persist.embeddables.CurrencyCodeField;
import com.google.inject.persist.Transactional;
import org.mapstruct.factory.Mappers;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

/**
 * Domain objects pass in and out
 * Internally JPA is used
 * JPA objects cannot be passed out
 */
//@RequestScoped
public class AccountRepository {

    @javax.inject.Inject
    private EntityManager em;

    public Account getAccount(long id)
    {
        AccountMapper mapper = Mappers.getMapper(AccountMapper.class);
        assert (em!=null);
        TypedQuery<AccountEntity> query =
                em.createQuery("SELECT a from Account a WHERE a.id = :accountId",
                        AccountEntity.class);
        query.setParameter("accountId", id);
        AccountEntity row = query.getSingleResult();
        return mapper.accountEntityToAccount(row);
    }


    public List<AccountEntity> getAccounts() {
        TypedQuery<AccountEntity> query = em.createQuery("SELECT e FROM Account e",
                AccountEntity.class);
        return query.getResultList();
    }

    @Transactional
    public void createOrUpdateAccount(AccountEntity a)
    {
        // set the transaction and valid time and persist the account
        // a.setCreateTime(LocalDateTime.now());
        em.persist(a);
    }

    public String getAccountName(long id) {
        assert (em!=null);
        TypedQuery<AccountEntity> query =
                em.createQuery("SELECT a from Account a WHERE a.id = :accountId",
                        AccountEntity.class);
        query.setParameter("accountId", id);
        return query.getSingleResult().getName();
    }

    public String getAccountString() {

        String text = "FAIL";
        try {
            AccountEntity a = createDummyAccount();
            createOrUpdateAccount(a);
            text = "Created " + a.toString();
        } catch (Throwable e) {
            e.printStackTrace(System.out);
        }

        return text;
    }

    private AccountEntity createDummyAccount()
    {
        AccountEntity a = new AccountEntity();
        Date now = new Date();
        a.setId(now.getTime());
        a.setName("My account " + now.hashCode());
        a.setBaseCurrency(new CurrencyCodeField("CHF"));
        return a;
    }

}
