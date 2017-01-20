package abacus.persist.dao;

import abacus.domain.money.Money;
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
import java.time.Period;
import java.time.temporal.TemporalAdjuster;
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

    /**
     * Return a specific posting
     *
     * @param id
     * @return
     */
    public Posting getPosting(long id) {
        assert (em != null);
        TypedQuery<PostingEntity> query =
                em.createQuery("SELECT p from Posting p WHERE p.id = :postingId",
                        PostingEntity.class);
        query.setParameter("postingId", id);
        PostingEntity row = query.getSingleResult();

        return mapper.postingEntityToPosting(row);
    }

    /**
     * Return ALL postings
     *
     * @return all postings
     */
    public List<Posting> getPostings() {
        TypedQuery<PostingEntity> query = em.createQuery("SELECT p FROM Posting p",
                PostingEntity.class);
        return mapper.postingEntityListToPostingList(query.getResultList());
    }

    /**
     * Return the total of postings to a given account on a given business day
     *
     * @param accountId
     * @param bizDate
     * @return
     */
    public List<Money> getPostingValues(long accountId, LocalDate bizDate) {
        assert (em != null);
        String JPQL_POSTING_TOTAL = "SELECT new abacus.domain.money.Money" +
                "(sum(p.value.amount), p.value.currency) " +
                "FROM Posting p WHERE p.accountId = :accountId and p.bizDate = :bizDate " +
                "GROUP BY p.accountId, p.bizDate, p.value.currency";

        TypedQuery<Money> query =
                em.createQuery(JPQL_POSTING_TOTAL, Money.class);
        query.setParameter("accountId", accountId);
        query.setParameter("bizDate", bizDate);

        return query.getResultList();
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

    public List<Balance> getBalances() {
        TypedQuery<BalanceEntity> query = em.createQuery("SELECT b FROM Balance b",
                BalanceEntity.class);
        return mapper.balanceEntityListToBalanceList(query.getResultList());
    }

    public List<Balance> getBalance(long accountId, LocalDate bizDate) {
        assert (em != null);
        TypedQuery<BalanceEntity> query =
                em.createQuery("SELECT b from Balance b WHERE b.account.id = :accountId and b.bizDate = :bizDate",
                        BalanceEntity.class);
        query.setParameter("accountId", accountId);
        query.setParameter("bizDate", bizDate);

        return mapper.balanceEntityListToBalanceList(query.getResultList());
    }

    public List<Money> getBalanceValues(long accountId, LocalDate bizDate) {
        assert (em != null);
        TypedQuery<Money> query =
                em.createQuery("SELECT new abacus.domain.money.Money(b.amount, b.currency) " +
                                "FROM Balance b " +
                                "WHERE b.account.id = :accountId and b.bizDate = :bizDate",
                        Money.class);
        query.setParameter("accountId", accountId);
        query.setParameter("bizDate", bizDate);

        return query.getResultList();
    }

    public List<Balance> getIDTBalance(long accountId, LocalDate bizDate) {
        LocalDate priorBizDate = bizDate.minus(Period.ofDays(1));

        List<Money> postingValues = getPostingValues(accountId, bizDate);
        List<Money> balanceValues = getBalanceValues(accountId, priorBizDate);

        // Append postings and aggregate
        balanceValues.addAll(postingValues);
        List<Money> values = Money.aggregate(balanceValues);

        return Balance.toBalances(accountId, bizDate, values);
    }

}
