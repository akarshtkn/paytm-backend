package com.example.backend.dto.paytmUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferAmountRequest {
    private Integer fromUserId;
    private Integer toUserId;
    private Integer amount;
}
