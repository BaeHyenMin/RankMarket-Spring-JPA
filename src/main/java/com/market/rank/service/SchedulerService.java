package com.market.rank.service;

import com.market.rank.repository.WinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final WinRepository winRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void run(){
        winRepository.winSave();
    }
}
