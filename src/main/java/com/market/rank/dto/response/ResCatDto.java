package com.market.rank.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResCatDto {
    private String cat_id;
    private String cat_name;
    private int price;
}
