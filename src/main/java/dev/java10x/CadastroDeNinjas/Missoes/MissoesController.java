package dev.java10x.CadastroDeNinjas.Missoes;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/missoes")
public class MissoesController {

    private MissoesService missoesService;

    public MissoesController(MissoesService missoesService) {
        this.missoesService = missoesService;
    }

    @GetMapping("/listarTodasMissoes")
    public List<MissoesModel> listarMissoes(){
        return missoesService.listarTodasMissoes();
    }

    @GetMapping("/listarMissaoPorId")
    public MissoesModel listarMissaoPorId(@PathVariable Long id){
        return missoesService.listarMissaoPorId(id);
    }

    // Adicionar novas missoes
    @PostMapping("/criarMissao")
    public MissoesModel criarMissoes(@RequestBody MissoesModel missao){
        return missoesService.criarMissao(missao);
    }

    // Buscar missoes
    @GetMapping("/alterar")
    public String alterarMissao(){
        return "Missão alterada com sucesso!";
    }

    @DeleteMapping("/deletar")
    public String deletarMissao(){
        return "Missão deletada com sucesso!";
    }

}
