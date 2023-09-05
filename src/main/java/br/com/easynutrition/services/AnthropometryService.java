package br.com.easynutrition.services;

import br.com.easynutrition.models.Anthropometry;
import br.com.easynutrition.repositories.AnthropometryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AnthropometryService {
    @Autowired
    private AnthropometryRepository anthropometryRepository;

    @Autowired
    private PersonService personService;

    public List<Anthropometry> findAllByEvaluationDate(LocalDate date, Long id) {
        List<Anthropometry> anthropometryList = anthropometryRepository.findAllByEvaluationDateAndPersonId(date, id);
        if (anthropometryList.isEmpty()) {
            throw new RuntimeException("Não foram encontradas avaliações.");
        }
        return anthropometryList;
    }

    public List<Anthropometry> findAllByPersonId(Long id) {
        List<Anthropometry> anthropometryList = anthropometryRepository.findAllByPersonId(id);
        if (anthropometryList.isEmpty()) {
            throw new RuntimeException("Não foram encontradas avaliações.");
        }
        return anthropometryList;
    }

    @Transactional
    public Anthropometry save(Anthropometry anthropometry) {
        return anthropometryRepository.save(anthropometry);
    }

    @Transactional
    public Anthropometry update(Anthropometry anthropometry) {
        anthropometryRepository.findById(anthropometry.getId()).orElseThrow(() -> new RuntimeException("Avaliação não encontrada."));
        return anthropometryRepository.save(anthropometry);
    }

    @Transactional
    public void delete(Long id) {
        anthropometryRepository.findById(id).orElseThrow(() -> new RuntimeException("Avaliação não encontrada."));
        anthropometryRepository.deleteById(id);
    }
}
