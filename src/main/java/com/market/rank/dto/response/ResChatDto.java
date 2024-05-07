package com.market.rank.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResChatDto {
    private String chat_id;
    private String usr_id;
    private int prd_id;
    private String prd_title;

}
