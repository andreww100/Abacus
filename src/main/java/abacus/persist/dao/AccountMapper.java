package abacus.persist.dao;

import abacus.domain.account.Account;
import abacus.domain.money.CurrencyCode;
import abacus.persist.entities.AccountEntity;
import abacus.persist.embeddables.CurrencyCodeField;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @see <a href="http://mapstruct.org/documentation/1.1/reference/html/index.html">MapStruct</a>
 */
@Mapper
public interface AccountMapper {

    public CurrencyCodeField currencyCodeToCurrencyCodeField(CurrencyCode cur);

    public CurrencyCode currencyCodeFieldToCurrencyCode(CurrencyCodeField cur);

    public Account accountEntityToAccount(AccountEntity account);

    //public List<Account> accountEntityListToAccountList(List<AccountEntity> account);

    public AccountEntity accountToAccountEntity(Account account);

}
