package ua.ivan.provider.service;

import ua.ivan.provider.model.Donate;
import ua.ivan.provider.model.User;
import ua.ivan.provider.repository.DonateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service("donateService")
public class DonateService {

    private final DonateRepository donateRepository;

    @Autowired
    public DonateService(DonateRepository donateRepository) {
        this.donateRepository = donateRepository;
    }

    public Donate getDonateByUserId(Long id) {
        return donateRepository.findByUserId(id).orElseThrow(() ->
                new EntityNotFoundException("Donate doesn't exists"));
    }

    public Donate findById(Long id) {
        return donateRepository.getById(id);
    }

    public List<Donate> findAll() {
        return donateRepository.findAll();
    }

    public Donate saveDonate(Donate donate) {
        return donateRepository.save(donate);
    }

    public boolean deleteDonate(Long id) {
        donateRepository.deleteById(id);
        return true;
    }

    public Donate requestDonate(Donate donate, Long sum, User user) {
        donate.setSum(sum);
        donate.setUserId(user);
        return saveDonate(donate);
    }

}
