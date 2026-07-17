package dev.java10x.CadastroDeNinjas.Missoes;

import dev.java10x.CadastroDeNinjas.Ninjas.NinjaMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MissoesService {

    private final MissoesRepository missoesRepository;
    private final MissoesMapper missoesMapper;


    public MissoesService(MissoesRepository missoesRepository, MissoesMapper missoesMapper) {
        this.missoesRepository = missoesRepository;
        this.missoesMapper = missoesMapper;
    }

    public List<MissoesDTO> listarTodasMissoes(){
        return missoesRepository.findAll()
                .stream()
                .map(missoesMapper::map)
                .collect(Collectors.toList());
    }

    public MissoesDTO listarMissaoPorId(Long id) {
        return missoesRepository.findById(id)
                .map(missoesMapper::map)
                .orElse(null);
    }

    public MissoesDTO criarMissao(MissoesDTO missaoDTO){
        MissoesModel missaoModel = missoesMapper.map(missaoDTO);
        MissoesModel missaoSalva = missoesRepository.save(missaoModel);
        return missoesMapper.map(missaoSalva);
    }

    public void deletarMissao(Long id){
        missoesRepository.deleteById(id);
    }

    public MissoesDTO atualizarMissao(Long id, MissoesDTO missoesDTO){
        if (missoesRepository.existsById(id)){
            MissoesModel missoesModel = missoesMapper.map(missoesDTO);
            missoesModel.setId(id);
            MissoesModel missaoAtualizada = missoesRepository.save(missoesModel);
            return missoesMapper.map(missaoAtualizada);
        }else{
            return null;
        }
    }

}
