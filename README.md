# Xanadu [![Build Status](https://travis-ci.org/gakuzzzz/xanadu.svg)](https://travis-ci.org/gakuzzzz/xanadu)

Java8 utilities powered by Lombok

## Example

### without Xanadu

```java
public class EmployeeFinderService {

    public List<Employee> findAllEmployees() {
        final List<Company> companies = ...
        return companies.stream()
            .map(Company::getEmployees)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    ...

}
```

### with Xanadu

```java
@ExtensionMethod(ListOps.class)
public class EmployeeFinderService {

    public List<Employee> findAllEmployees() {
        final List<Company> companies = ...
        return companies.flatMap(Company::getEmployees);
    }

    ...

}
```
