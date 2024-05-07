package com.market.rank.config.api;

import com.market.rank.config.jwt.TokenProvider;
import com.market.rank.dto.request.ReqLoginUsrDto;
import com.market.rank.dto.response.ResDto;
import com.market.rank.dto.response.ResUsrDto;
import com.market.rank.domain.Usr;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsrCheckConfig {


    private final TokenProvider tokenProvider;

    public ReqLoginUsrDto usrIsEmpty(String email, String usrNm){
        return ReqLoginUsrDto.builder()
                .mail(email)
                .usrNm(usrNm)
                .build();

    }

    public ReqLoginUsrDto usrNotEmpty(Optional<Usr> usr, String token){
        Usr usrInfo = usr.get();
        return ReqLoginUsrDto.builder()
                .usrId(usrInfo.getUsrId())
                .usrNm(usrInfo.getUsrNm())
                .token(token)
                .build();
    }

    public ResponseEntity<ResDto> usrMakeToken(ReqLoginUsrDto usr){
        Duration expiredAt;
        String token;
        if(usr.getUsrId() == null) {
            System.out.println("권한1번");
            token = tokenProvider.makeAccessToken(usr, "ROLE_GUEST");
        }else{
            System.out.println("권한2번");
            tokenProvider.makeRefreshToken(usr);
            token = tokenProvider.makeAccessToken(usr,"ROLE_USER");

        }



        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", token);
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(ResDto.builder()
                        .status(HttpStatus.OK)
                        .response(
                                ResUsrDto.builder()
                                        .mail(usr.getMail())
                                        .usrNm(usr.getUsrNm())
                                        .build())
                        .build());

    }
}
