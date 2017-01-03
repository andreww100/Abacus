package abacus.persist.converters;

import abacus.domain.money.CurrencyCode;
import abacus.domain.money.CurrencyCodeFactory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CurrencyCodeConverter implements AttributeConverter<CurrencyCode, String>
{

    @Override
    public String convertToDatabaseColumn(CurrencyCode currencyCode) {
        return currencyCode.getValue();
    }

    @Override
    public CurrencyCode convertToEntityAttribute(String s) {
        return CurrencyCodeFactory.getCurrencyCode(s);
    }
}
