package dev.java10x.CadastroDeNinjas.Ninjas;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NinjaService {

    private NinjaRepository ninjaRepository;
    private NinjaMapper ninjaMapper;

    public NinjaService(NinjaRepository ninjaRepository, NinjaMapper ninjaMapper) {
        this.ninjaRepository = ninjaRepository;
        this.ninjaMapper = ninjaMapper;
    }
    // Listar todos os ninjas

    public List<NinjaDTO> listarNinjas(){
         return ninjaRepository.findAll()
                 .stream()
                 .map(ninjaMapper::map)
                 .collect(Collectors.toList());
    }

    // Buscar ninja por ID
    public Optional<NinjaDTO> listaNinjaId(Long id){
        Optional<NinjaModel> ninjaPorId = ninjaRepository.findById(id);
        return Optional.ofNullable(ninjaPorId.map(ninjaMapper::map).orElse(null));
    }

    // Criar um novo ninja
    public NinjaDTO criarNinja(NinjaDTO ninjaDTO){
        NinjaModel ninjaModel = ninjaMapper.map(ninjaDTO);
        NinjaModel ninjaSalvo = ninjaRepository.save(ninjaModel);
        return ninjaMapper.map(ninjaSalvo);
    }

    // Deletar Ninja
    public void deletarNinjaPorId(Long id){
        ninjaRepository.deleteById(id);
    }

    public Optional<NinjaDTO> atualizarNinja(Long id, NinjaDTO ninjaDTO){
        Optional<NinjaModel> ninjaExistente = ninjaRepository.findById(id);
        if (ninjaExistente.isPresent()) {
            NinjaModel ninjaAtualizado = ninjaMapper.map(ninjaDTO);
            ninjaAtualizado.setId(id);
            NinjaModel ninjaAtualizadoNoBanco = ninjaRepository.save(ninjaAtualizado);
            return Optional.of(ninjaMapper.map(ninjaAtualizadoNoBanco));
        } else {
            return Optional.empty();
        }
    }

}