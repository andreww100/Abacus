package abacus.persist.dao;

import abacus.domain.account.Account;
import abacus.domain.money.CurrencyCode;
import abacus.persist.entities.AccountEntity;
import abacus.persist.embeddables.CurrencyCodeField;
import org.mapstruct.Mapper;

/**
 * @see <a href="http://mapstruct.org/documentation/1.1/reference/html/index.html">MapStruct</a>
 */
@Mapper
interface AccountMapper {

    CurrencyCodeField currencyCodeToCurrencyCodeField(CurrencyCode cur);

    CurrencyCode currencyCodeFieldToCurrencyCode(CurrencyCodeField cur);

    Account accountEntityToAccount(AccountEntity account);

    AccountEntity accountToAccountEntity(Account account);

}
