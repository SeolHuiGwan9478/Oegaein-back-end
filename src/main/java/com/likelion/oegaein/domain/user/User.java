package com.likelion.oegaein.domain.user;

import com.likelion.oegaein.domain.global.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Transactional
public class User extends BaseTime {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
}
