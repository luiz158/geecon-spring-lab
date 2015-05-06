package customer

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated

import javax.validation.Valid
import javax.validation.constraints.NotNull

import static java.util.Optional.ofNullable

@Service
@Validated
@CompileStatic
class CustomerService {

    private final CustomerRepository repository
    private final BlacklistValidator validator

    @Autowired
    CustomerService(CustomerRepository repository, BlacklistValidator validator) {
        this.validator = validator
        this.repository = repository
    }

    Optional<Customer> findById(Long id) {
        return ofNullable(repository.findOne(id))
    }

    Optional<Customer> findByCreditCardNumber(String creditCardNumber) {
        return ofNullable(repository.findByCreditCardsNumber(creditCardNumber))
    }

    Customer create(@NotNull @Valid Customer customer) {
        validator.validate(customer.firstName)
        validator.validate(customer.lastName)

        return repository.save(customer)
    }
}
