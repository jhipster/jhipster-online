package io.github.jhipster.online.service;

import io.github.jhipster.online.domain.Language;
import io.github.jhipster.online.repository.LanguageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Language.
 */
@Service
@Transactional
public class LanguageService {

    private final Logger log = LoggerFactory.getLogger(LanguageService.class);

    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    /**
     * Save a language.
     *
     * @param language the entity to save
     * @return the persisted entity
     */
    public Language save(Language language) {
        log.debug("Request to save Language : {}", language);
        return languageRepository.save(language);
    }

    /**
     * Get all the languages.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Language> findAll() {
        log.debug("Request to get all Languages");
        return languageRepository.findAll();
    }


    /**
     * Get one language by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Language> findOne(Long id) {
        log.debug("Request to get Language : {}", id);
        return languageRepository.findById(id);
    }

    /**
     * Delete the language by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Language : {}", id);
        languageRepository.deleteById(id);
    }
}
