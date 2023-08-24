package br.com.easynutrition.services;

import br.com.easynutrition.models.Anthropometry;
import br.com.easynutrition.models.Person;
import br.com.easynutrition.repositories.AnthropometryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public Anthropometry save(Anthropometry anthropometry) {
        return anthropometryRepository.save(anthropometry);
    }

    public Anthropometry update(Anthropometry anthropometry) {
        anthropometryRepository.findById(anthropometry.getId()).orElseThrow(() -> new RuntimeException("Avaliação não encontrada."));
        return anthropometryRepository.save(anthropometry);
    }

    public void delete(Long id) {
        anthropometryRepository.findById(id).orElseThrow(() -> new RuntimeException("Avaliação não encontrada."));
        anthropometryRepository.deleteById(id);
    }
}
