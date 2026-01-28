package com.example.converter;

import com.example.model.AccountDto;
import com.example.model.UserDto;
import com.example.persistence.entity.Account;
import com.example.persistence.entity.User;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;


@Component
public class Converter {

    public UserDto toUserDto(User entity) {
        if (entity != null) {
            List<AccountDto> dtoList = new ArrayList<>();
            entity.getAccounts().forEach(a -> dtoList.add(toAccountDto(a)));
            return UserDto.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .accounts(dtoList)
                    .build();
        }
        return null;
    }

    public User toUserEntity(UserDto dto) {
        if (dto != null) {
            List<Account> accountList = new ArrayList<>();
            dto.getAccounts().forEach(a -> accountList.add(toAccountEntity(a)));
            return User.builder()
                    //.id(dto.getId())
                    .name(dto.getName())
                    .accounts(accountList)
                    .build();
        }
        return null;
    }

    public AccountDto toAccountDto(Account entity) {
        if (entity != null) {
            return AccountDto.builder()
                    .id(entity.getId())
                    .userId(entity.getUser().getId())
                    //.userName(entity.getName())
                    .moneyAmount(entity.getMoneyAmount())
                    .build();
        }
        return null;
    }

    public Account toAccountEntity(AccountDto dto) {
        if (dto != null) {
            return Account.builder()
                    //.id(dto.getId())
                    .user(null)
                    //.name(dto.getUserName())
                    .moneyAmount(dto.getMoneyAmount())
                    .build();
        }
        return null;
    }

}
