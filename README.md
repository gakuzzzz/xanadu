# Xanadu

Java8 utilities powered by Lombok

## Example

```java
@Value
public static class Company {
    @NonNull List<Employee> employees;
}

@Value
@ExtensionMethod(ListExtensions.class)
public class EnployeeFinderService {

    CompanyRepository repository;

    public List<Employee> findAllEmployees() {
        final List<Company> companies = repository.asList();
        return companies.flatMap(Company::getEmployees);
    }

}
```
