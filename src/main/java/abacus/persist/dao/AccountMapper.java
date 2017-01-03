package abacus.persist.dao;

import abacus.domain.account.Account;
import abacus.domain.money.CurrencyCode;
import abacus.persist.entities.AccountEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @see <a href="http://mapstruct.org/documentation/1.1/reference/html/index.html">MapStruct</a>
 */
@Mapper
public interface AccountMapper {

    Account accountEntityToAccount(AccountEntity account);

    List<Account> accountEntityListToAccountList(List<AccountEntity> accounts);

    AccountEntity accountToAccountEntity(Account account);

}
