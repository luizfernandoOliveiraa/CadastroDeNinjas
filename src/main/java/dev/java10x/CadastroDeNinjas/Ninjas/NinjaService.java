package dev.java10x.CadastroDeNinjas.Ninjas;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NinjaService {

    private final NinjaRepository ninjaRepository;
    private final NinjaMapper ninjaMapper;

    public NinjaService(NinjaRepository ninjaRepository, NinjaMapper ninjaMapper) {
        this.ninjaRepository = ninjaRepository;
        this.ninjaMapper = ninjaMapper;
    }

    // Listar todos os ninjas
    public List<NinjaDTO> listarNinjas() {
        return ninjaRepository.findAll()
                .stream()
                .map(ninjaMapper::map)
                .collect(Collectors.toList());
    }

    // Buscar ninja por ID
    public NinjaDTO listaNinjaId(Long id) {
        return ninjaRepository.findById(id)
                .map(ninjaMapper::map)
                .orElse(null);
    }

    // Criar um novo ninja
    public NinjaDTO criarNinja(NinjaDTO ninjaDTO) {
        NinjaModel ninjaModel = ninjaMapper.map(ninjaDTO);
        NinjaModel ninjaSalvo = ninjaRepository.save(ninjaModel);
        return ninjaMapper.map(ninjaSalvo);
    }

    // Deletar Ninja
    public void deletarNinjaPorId(Long id) {
        ninjaRepository.deleteById(id);
    }

    // Atualizar um ninja
    public NinjaDTO atualizarNinja(Long id, NinjaDTO ninjaDTO) {
        if (ninjaRepository.existsById(id)) {
            NinjaModel ninjaModel = ninjaMapper.map(ninjaDTO);
            ninjaModel.setId(id); // Garante que o ID correto está sendo atualizado
            NinjaModel ninjaAtualizado = ninjaRepository.save(ninjaModel);
            return ninjaMapper.map(ninjaAtualizado);
        } else {
            return null;
        }
    }
}