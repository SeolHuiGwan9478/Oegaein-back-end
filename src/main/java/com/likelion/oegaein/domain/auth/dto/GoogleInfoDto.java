package com.likelion.oegaein.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoogleInfoDto {
    private String iss;
    private String azp;
    private String aud;
    private String sub;
    private String email;
    @JsonProperty("email_verified")
    private String emailVerified;
    @JsonProperty("at_hash")
    private String atHash;
    private String name;
    private String picture;
    @JsonProperty("given_name")
    private String givenName;
    @JsonProperty("family_name")
    private String familyName;
    private String locale;
    private String iat;
    private String exp;
    private String alg;
    private String kid;
    private String typ;
}
