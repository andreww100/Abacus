package abacus.persist.dao;

import abacus.domain.account.Account;
import abacus.persist.entities.AccountEntity;
import com.google.inject.persist.Transactional;
import org.mapstruct.factory.Mappers;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Domain objects pass in and out
 * Internally JPA is used
 * JPA objects cannot be passed out
 */
//@RequestScoped
public class AccountDAO {

    @javax.inject.Inject
    private EntityManager em;

    // MapStruct Mapper
    private AccountMapper mapper;


    public AccountDAO() {
        mapper = Mappers.getMapper(AccountMapper.class);
    }

    @Transactional
    public void createOrUpdateAccount(Account a) {


        // set the transaction and valid time and persist the account
        em.persist(mapper.accountToAccountEntity(a));
    }

    public Account getAccount(long id) {
        assert (em != null);
        Account ret = null;
        TypedQuery<AccountEntity> query =
                em.createQuery("SELECT a from Account a WHERE a.id = :accountId",
                        AccountEntity.class);
        query.setParameter("accountId", id);
        List<AccountEntity> results = query.getResultList();
        if ((results != null) && (results.size() >= 1))
        {
            AccountEntity row = results.get(0);
            ret = mapper.accountEntityToAccount(row);

        }
        return ret;
    }

    public List<Account> getAccounts() {
        TypedQuery<AccountEntity> query = em.createQuery("SELECT e FROM Account e",
                AccountEntity.class);
        return mapper.accountEntityListToAccountList(query.getResultList());
    }

    // package

    AccountEntity getAccountEntity(long id) {
        assert (em != null);
        TypedQuery<AccountEntity> query =
                em.createQuery("SELECT a from Account a WHERE a.id = :accountId",
                        AccountEntity.class);
        query.setParameter("accountId", id);
        return query.getSingleResult();
    }

    AccountEntity getAccountEntityRef(long id) {
        assert (em != null);
        return em.getReference(AccountEntity.class, id);
    }

}
