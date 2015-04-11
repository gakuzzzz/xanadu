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

## Install

1. Setup Lombok. see http://projectlombok.org/mavenrepo/index.html
1. Add Sonatype snapshot repository and add dependencies.

### Using gradle

```groovy
repositories {
    maven {
        url 'https://oss.sonatype.org/content/repositories/snapshots'
    }
}

dependencies {
    compile 'jp.t2v:xanadu:0.1.0-SNAPSHOT'
}
```


## License

This library is released under the Apache Software License, version 2, which should be included with the source in a file named LICENSE.
