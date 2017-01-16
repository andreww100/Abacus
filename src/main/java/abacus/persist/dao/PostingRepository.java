package abacus.persist.dao;

import abacus.domain.posting.Balance;
import abacus.domain.posting.Posting;
import abacus.persist.converters.LocalDateAttributeConverter;
import abacus.persist.entities.AccountEntity;
import abacus.persist.entities.BalanceEntity;
import abacus.persist.entities.PostingEntity;
import com.google.inject.persist.Transactional;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
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
        PostingEntity row = mapper.postingToPostingEntity(p);

        AccountEntity ae = repoA.getAccountEntityRef(p.getAccountId());
        row.setAccount(ae);

        // set the transaction and valid time and persist the posting
        em.persist(row);
    }

    public Posting getPosting(long id) {
        assert (em != null);
        TypedQuery<PostingEntity> query =
                em.createQuery("SELECT p from Posting p WHERE p.id = :postingId",
                        PostingEntity.class);
        query.setParameter("postingId", id);
        PostingEntity row = query.getSingleResult();

        if (row != null) {
            // Test Lazy Load
            this.em.clear();
            log.info("Test Lazy Load: Retrieve Id only: " + row.getAccountId());
            log.info("Test Lazy Load: Retrieve full Account record: " + row.getAccount().toString());
        }

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
        AccountEntity ae;
        //ae = repoA.getAccountEntity(b.getAccountId());
        ae = repoA.getAccountEntityRef(b.getAccountId());

        row.setAccount(ae);
        em.persist(row);
    }

    public Balance getBalance(long accountId, LocalDate bizDate) {
        assert (em != null);
        TypedQuery<BalanceEntity> query =
                em.createQuery("SELECT b from Balance b WHERE b.accountId = :accountId and b.bizDate = :bizDate",
                        BalanceEntity.class);
        query.setParameter("accountId", accountId);
        query.setParameter("bizDate", new LocalDateAttributeConverter().convertToDatabaseColumn(bizDate));

        BalanceEntity row = query.getSingleResult();

        if (row != null) {
            this.em.clear();
            // Test Lazy Load
            log.info("Test Lazy Load: Retrieve Id only: " + row.getAccountId());
            log.info("Test Lazy Load: Retrieve full Account record: " + row.getAccount().toString());
        }

        return mapper.balanceEntityToBalance(row);
    }

    public List<Balance> getBalances() {
        TypedQuery<BalanceEntity> query = em.createQuery("SELECT b FROM Balance b",
                BalanceEntity.class);
        return mapper.balanceEntityListToBalanceList(query.getResultList());
    }

    /**
    public Balance getIDTBalance(long accountId, LocalDate bizDate) {
        assert (em != null);
        String JPQL_POSTING_TOTAL = "SELECT new abacus.domain.posting.Balance(x.accountId, :bizDate, x.currency, sum(x.amount)) FROM Posting AS x WHERE x.accountId = :accountId GROUP BY x.currency";
        String JPQL_POSTING_TOTAL1 = "SELECT new abacus.domain.posting.Balance(accountId, :bizDate, currency, sum(amount)) FROM Posting WHERE accountId = :accountId GROUP BY currency";
        String JPQL_POSTING_TOTAL2 = "SELECT p.accountId, :bizDate, p.currency, sum(p.amount) FROM Posting AS p WHERE p.accountId = :accountId GROUP BY p.currency";

        TypedQuery<Balance> query =
                em.createQuery(JPQL_POSTING_TOTAL1, Balance.class);
        query.setParameter("accountId", accountId);
        query.setParameter("bizDate", new LocalDateAttributeConverter().convertToDatabaseColumn(bizDate));

        Balance row = query.getSingleResult();

        return row;
    }
     */
}
