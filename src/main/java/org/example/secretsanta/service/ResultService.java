package org.example.secretsanta.service;

import org.example.secretsanta.dto.ResultDTO;
import org.example.secretsanta.model.entity.ResultEntity;
import org.example.secretsanta.repository.ResultRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ResultService {

    private final ResultRepository resultRepository;

    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public ResultEntity create(ResultDTO dto) {
        ResultEntity result = new ResultEntity();
        result.setIdSanta(dto.getIdSanta());
        result.setIdWard(dto.getIdWard());

        return result;
    }

    public List<ResultEntity> readAll() {
        return resultRepository.findAll();
    }

    public ResultEntity update(int id, ResultDTO dto) {
        ResultEntity result = resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result not found with id: " + id));

        result.setIdSanta(dto.getIdSanta());
        result.setIdWard(dto.getIdWard());

        return result;
    }

    public  void delete(int id){
        resultRepository.deleteById(id);
    }

}
