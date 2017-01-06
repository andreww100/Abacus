package abacus.persist.dao;

import abacus.domain.money.Money;
import abacus.domain.posting.Balance;
import abacus.domain.posting.Posting;
import abacus.persist.embeddables.MoneyFields;
import abacus.persist.entities.BalanceEntity;
import abacus.persist.entities.PostingEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @see <a href="http://mapstruct.org/documentation/1.1/reference/html/index.html">MapStruct</a>
 */
@Mapper
public interface PostingMapper {

    MoneyFields moneyToMoneyFields(Money value);

    Money moneyFieldsToMoney(MoneyFields value);

    Posting postingEntityToPosting(PostingEntity posting);

    List<Posting> postingEntityListToPostingList(List<PostingEntity> postings);

    PostingEntity postingToPostingEntity(Posting posting);

    Balance balanceEntityToBalance(BalanceEntity balance);

    List<Balance> balanceEntityListTobalanceList(List<BalanceEntity> balances);

    BalanceEntity balanceToBalanceEntity(Balance balance);
}
