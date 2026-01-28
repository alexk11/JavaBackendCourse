package com.example.model;

import lombok.Builder;
import lombok.Getter;
import java.util.List;


@Getter
@Builder
public class UserDto {

    private final long id;
    private final String name;
    private final List<AccountDto> accounts;

    public UserDto(long id, String name, List<AccountDto> accounts) {
        this.id = id;
        this.name = name;
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "User { id=" + id + ", name='" + name + ", accounts=" + accounts + " }";
    }

}
