package abacus.persist.dao;

import abacus.domain.posting.Balance;
import abacus.domain.posting.Posting;
import abacus.persist.entities.AccountEntity;
import abacus.persist.entities.BalanceEntity;
import abacus.persist.entities.PostingEntity;
import com.google.inject.persist.Transactional;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Domain objects pass in and out
 * Internally JPA is used
 * JPA objects cannot be passed out
 */
//@RequestScoped
public class PostingRepository {

    private Logger log = LoggerFactory.getLogger(PostingRepository.class);

    @javax.inject.Inject
    private EntityManager em;

    // MapStruct Mapper
    private PostingMapper mapper;

    @javax.inject.Inject
    private AccountRepository repoA;


    public PostingRepository() {
        mapper = Mappers.getMapper(PostingMapper.class);
    }

    @Transactional
    public void createOrUpdatePosting(Posting p) {
        // set the transaction and valid time and persist the posting
        em.persist(mapper.postingToPostingEntity(p));
    }

    public Posting getPosting(long id) {
        assert (em != null);
        TypedQuery<PostingEntity> query =
                em.createQuery("SELECT p from Posting p WHERE p.id = :postingId",
                        PostingEntity.class);
        query.setParameter("postingId", id);
        PostingEntity row = query.getSingleResult();

        // Test Lazy Load

        try {
            row.state("accountid");
        } catch (Exception e) {
            e.printStackTrace();
        }

        AccountEntity a = row.getAccount();
        log.info("Posting for account: " +
                ((a != null) ? a.toString() : "null"));

        return mapper.postingEntityToPosting(row);
    }

    public List<Posting> getPostings() {
        TypedQuery<PostingEntity> query = em.createQuery("SELECT p FROM Posting p",
                PostingEntity.class);
        return mapper.postingEntityListToPostingList(query.getResultList());
    }

    @Transactional
    public void createOrUpdateBalance(Balance b) {
        // set the transaction and valid time and persist the posting
        BalanceEntity row = mapper.balanceToBalanceEntity(b);
        AccountEntity ae = repoA.getAccount2(b.getAccountId());
        row.setAccount(ae);
        em.persist(row);
    }

    public Balance getBalance(long id) {
        assert (em != null);
        TypedQuery<BalanceEntity> query =
                em.createQuery("SELECT b from Balance b WHERE b.id = :accountId",
                        BalanceEntity.class);
        query.setParameter("accountId", id);
        BalanceEntity row = query.getSingleResult();

        if (row != null) {
            // Test Lazy Load
            AccountEntity a = row.getAccount();
            log.info("Balance for account: " +
                    ((a != null) ? a.toString() : "null"));
        }

        return mapper.balanceEntityToBalance(row);
    }

    public List<Balance> getBalances() {
        TypedQuery<BalanceEntity> query = em.createQuery("SELECT b FROM Balance b",
                BalanceEntity.class);
        return mapper.balanceEntityListTobalanceList(query.getResultList());
    }

}
