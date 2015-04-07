package jp.t2v.xanadu.model;

import lombok.Value;

import java.util.Optional;

@Value
public class Message {
    Optional<String> title;
}
