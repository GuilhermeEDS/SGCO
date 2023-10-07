package dgn.com.br.sgco.service;

import dgn.com.br.sgco.entity.Dentista;
import dgn.com.br.sgco.repository.DentistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DentistaService {
    @Autowired
    DentistaRepository dentistaRepository;

    public Iterable<Dentista> todos() {
        return dentistaRepository.findAll();
    }
}
