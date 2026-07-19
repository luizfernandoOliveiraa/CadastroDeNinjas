package dev.java10x.CadastroDeNinjas.Missoes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/missoes")
public class MissoesController {

    private final MissoesService missoesService;

    public MissoesController(MissoesService missoesService) {
        this.missoesService = missoesService;
    }

    @GetMapping("health")
    @Operation(summary = "Check Heath API Status", description = "Verifica se a API esta pronta para receber requisições.")
    public ResponseEntity<String> healthApi(){
        return ResponseEntity.ok("API pronta para receber requisições");
    }

    @GetMapping("/listarTodasMissoes")
    @Operation(summary = "Lista todas as missões cadastradas no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista de missões cadastradas")
    })
    public ResponseEntity<List<MissoesDTO>> listarMissoes(){
        List<MissoesDTO> missoes = missoesService.listarTodasMissoes();
        return ResponseEntity.ok(missoes);
    }

    @GetMapping("/listarMissaoPorId/{id}")
    @Operation(summary = "Lista missão com o ID informado, se estiver presente no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a missão cadastrada"),
            @ApiResponse(responseCode = "404", description = "Missão não existente no banco")
    })
    public ResponseEntity<?> listarMissaoPorId(@PathVariable Long id) {
        if (missoesService.listarMissaoPorId(id) != null) {
            MissoesDTO missao = missoesService.listarMissaoPorId(id);
            return ResponseEntity.ok(missao);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Não foi possivel encontrar a missão de ID " + id);
        }
    }

    @PostMapping("/criarMissao")
    @Operation(summary = "Cria missão com DTO como body", description = "Body da requisição serve como o DTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Missão criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Algum parametro no body está incorreto")
    })
    public ResponseEntity<?> criarMissoes(@RequestBody MissoesDTO missao){
        try {
            MissoesDTO criarMissao = missoesService.criarMissao(missao);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Missão " + criarMissao.getNome() + " criada com sucesso!");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Não foi possivel criar o ninja, tente novamente");
        }
    }

    @PutMapping("/alterar/{id}")
    @Operation(summary = "Altera dados de missões", description = "Necessário passar todos os campos da missão para alteração completa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Missão alterada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Algum campo do body está faltando")
    })
    public ResponseEntity<?> alterarMissao(@PathVariable Long id, @RequestBody MissoesDTO missao) {
        if (missoesService.listarMissaoPorId(id) != null) {
            MissoesDTO missaoAlterada = missoesService.atualizarMissao(id, missao);
            return ResponseEntity.ok("Missão alterada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Missão não encontrada para alteração.");
        }
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deleta missão no banco de acordo com o Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Missão deletada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Missão não existe no banco!")
    })
    public ResponseEntity<?> deletarMissao(@PathVariable Long id) {
        if (missoesService.listarMissaoPorId(id) != null) {
            missoesService.deletarMissao(id);
            return ResponseEntity.ok("Missão deletada com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Missão com Id " + id + " não encontrada");
        }
    }
}

