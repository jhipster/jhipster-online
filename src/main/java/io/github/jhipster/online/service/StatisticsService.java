package io.github.jhipster.online.service;

import io.github.jhipster.online.repository.YoRCRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final YoRCRepository yoRCRepository;

    private final YoRCService yoRCService;

    public StatisticsService(YoRCService yoRCService, YoRCRepository yoRCRepository) {
        this.yoRCService = yoRCService;
        this.yoRCRepository = yoRCRepository;
    }

    public long getYoRCCount() {
        return yoRCRepository.count();
    }

    public List<Long> getYoRCCountForEachUser() {
        return yoRCRepository.findYoRCCountForEachUser();
    }
}
