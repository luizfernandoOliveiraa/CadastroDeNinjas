package dev.java10x.CadastroDeNinjas.Ninjas;

import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // Adicionar ninja (CREATE)
    @PostMapping("/criar")
    public NinjaDTO criarNinja(@RequestBody NinjaDTO ninja){
        return ninjaService.criarNinja(ninja);
    }

    // Mostrar todos os ninjas (READ)
    @GetMapping("/listarNinjas")
    public List<NinjaDTO> mostrarTodosOsNinjas(){
        return ninjaService.listarNinjas();
    }

    // Mostrar ninja por ID (READ)
    @GetMapping("/listarNinjaPorId/{id}")
    public NinjaDTO mostrarTodosOsNinjasPorID(@PathVariable Long id){
        return ninjaService.listaNinjaId(id);
    }

    // Alterar dados dos ninjas (UPDATE)
    @PutMapping("/alterar/{id}")
    public NinjaDTO alterarNinjaPorID(@PathVariable Long id, @RequestBody NinjaDTO ninjaAtualizado){
        return ninjaService.atualizarNinja(id, ninjaAtualizado);
    }

    // Deletar ninja (DELETE)
    @DeleteMapping("/deletar/{id}")
    public void deletableNinjaPorID(@PathVariable Long id){
        ninjaService.deletarNinjaPorId(id);
    }

}
