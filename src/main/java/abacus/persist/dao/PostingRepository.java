package abacus.persist.dao;

import abacus.domain.posting.Posting;
import abacus.persist.entities.PostingEntity;
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
public class PostingRepository {

    @javax.inject.Inject
    private EntityManager em;

    // MapStruct Mapper
    private PostingMapper mapper;

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
        return mapper.postingEntityToPosting(row);
    }

    public List<Posting> getPostings() {
        TypedQuery<PostingEntity> query = em.createQuery("SELECT p FROM Posting p",
                PostingEntity.class);
        return mapper.postingEntityListToPostingList(query.getResultList());
    }
}
