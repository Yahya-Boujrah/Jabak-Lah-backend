package com.Jabaklahbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@AttributeOverrides({
        @AttributeOverride(name = "phone", column = @Column(nullable = false,unique = true)),
})
public class Client extends User{
    private String address;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @ColumnDefault("0")
    private BigDecimal balance = new BigDecimal(0);

    @Override
    public String getUsername() {
        return phone;
    }

}
