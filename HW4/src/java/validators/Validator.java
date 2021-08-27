package validators;

import validationErrors.ValidationError;

import java.util.Set;

public interface Validator {
    Set<ValidationError> validate(Object object);
}
