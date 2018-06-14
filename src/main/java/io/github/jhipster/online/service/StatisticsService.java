package io.github.jhipster.online.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jhipster.online.domain.GeneratorIdentity;
import io.github.jhipster.online.domain.OwnerIdentity;
import io.github.jhipster.online.domain.YoRC;
import io.github.jhipster.online.repository.YoRCRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class StatisticsService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final YoRCRepository yoRCRepository;

    private final YoRCService yoRCService;
    private LanguageService languageService;
    private GeneratorIdentityService generatorIdentityService;

    public StatisticsService(YoRCService yoRCService,
                             YoRCRepository yoRCRepository,
                             LanguageService languageService,
                             GeneratorIdentityService generatorIdentityService) {
        this.yoRCService = yoRCService;
        this.yoRCRepository = yoRCRepository;
        this.languageService = languageService;
        this.generatorIdentityService = generatorIdentityService;
    }

    public long getYoRCCount() {
        return yoRCRepository.count();
    }

    public List<Long> getYoRCCountForEachUser() {
        return yoRCRepository.findYoRCCountForEachUser();
    }

    public void addEntry(String entry) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNodeRoot = mapper.readTree(entry);
        JsonNode jsonNodeGeneratorJHipster = jsonNodeRoot.get("generator-jhipster");
        String generatorGuid = jsonNodeRoot.get("generator-id").asText();
        String generatorVersion = jsonNodeRoot.get("generator-version").asText();
        String gitProvider = jsonNodeRoot.get("git-provider").asText();
        log.info("Adding an entry for generator {}.", generatorGuid);

        OwnerIdentity owner = generatorIdentityService.findOrCreateOneByGuid(generatorGuid).getOwner();

        YoRC yorc = mapper.treeToValue(jsonNodeGeneratorJHipster, YoRC.class);
        yorc.setJhipsterVersion(generatorVersion);
        yorc.setGitProvider(gitProvider);
        yorc.setOwner(owner);

        yoRCService.save(yorc);
        yorc.getSelectedLanguages().forEach(languageService::save);
    }

}
