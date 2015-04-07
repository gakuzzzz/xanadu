package jp.t2v.xanadu.model;

import lombok.Value;

import java.util.List;

@Value
public class Company {
    List<Employee> employees;
}
