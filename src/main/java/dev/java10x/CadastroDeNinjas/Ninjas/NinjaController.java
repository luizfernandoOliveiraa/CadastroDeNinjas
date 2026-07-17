package dev.java10x.CadastroDeNinjas.Ninjas;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ninjas")
public class NinjaController {

    private NinjaService ninjaService;

    public NinjaController(NinjaService ninjaService) {
        this.ninjaService = ninjaService;
    }

    @GetMapping("/boasvindas")
    public String boasVindas(){
        return "Essa é a minha primeira mensagem nessa rota!";
    }

    @PostMapping("/criar")
    public NinjaDTO criarNinja(@RequestBody NinjaDTO ninja){
        return ninjaService.criarNinja(ninja);
    }

    @GetMapping("/listarNinjas")
    public List<NinjaDTO> mostrarTodosOsNinjas(){
        return ninjaService.listarNinjas();
    }

    @GetMapping("/listarNinjaPorId/{id}")
    public Optional<NinjaDTO> mostrarTodosOsNinjasPorID(@PathVariable Long id){
        return ninjaService.listaNinjaId(id);
    }

    @PutMapping("/alterar/{id}")
    public Optional<NinjaDTO> alterarNinjaPorID(@PathVariable Long id, @RequestBody NinjaDTO ninjaAtualizado){
        return ninjaService.atualizarNinja(id, ninjaAtualizado);
    }

    @DeleteMapping("/deletar/{id}")
    public void deletableNinjaPorID(@PathVariable Long id){
        ninjaService.deletarNinjaPorId(id);
    }

}
