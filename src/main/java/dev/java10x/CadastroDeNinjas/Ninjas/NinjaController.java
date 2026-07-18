package dev.java10x.CadastroDeNinjas.Ninjas;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ninjas")
public class NinjaController {

    private final NinjaService ninjaService;

    public NinjaController(NinjaService ninjaService) {
        this.ninjaService = ninjaService;
    }

    @GetMapping("/heath")
    @Operation(summary = "Check health status", description = "Essa rota verifica se a API está funcionando")
    public ResponseEntity<String> apiFuncionando(){
        return ResponseEntity.ok("API funcionando!");
    }

    @PostMapping("/criar")
    @Operation(summary = "Cria um novo ninja", description = "Rota cria um novo ninja e insere no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ninja criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na criação do ninja")
    })
    public ResponseEntity<String> criarNinja(@RequestBody NinjaDTO ninja){
        NinjaDTO ninjaDTO =  ninjaService.criarNinja(ninja);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Ninja criado com sucesso: "+ ninjaDTO.getNome() + " ID: " + ninjaDTO.getId());
    }

    @GetMapping("/listarNinjas")
    public ResponseEntity<List<NinjaDTO>> mostrarTodosOsNinjas(){
        List<NinjaDTO> ninjas = ninjaService.listarNinjas();
        return ResponseEntity.ok(ninjas);
    }

    @GetMapping("/listarNinjaPorId/{id}")
    @Operation(summary = "Lista o ninja por Id", description = "Rota lista um ninja pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ninja encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ninja não encontrado ninja")
    })
    public ResponseEntity<?> mostrarTodosOsNinjasPorID(@PathVariable Long id){
        Optional<NinjaDTO> ninja = ninjaService.listaNinjaId(id);

        if (ninja.isPresent()){
            return ResponseEntity.ok(ninja);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ninja com ID: " + id + " não encontrado!");
        }
    }

    @PutMapping("/alterar/{id}")
    @Operation(summary = "Altera o ninja por Id", description = "Rota altera um ninja pelo seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ninja alterado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel alterar ninja")
    })
    public ResponseEntity<?> alterarNinjaPorID(
            @Parameter(description = "Usuário manda o ID no caminho da requisição")
            @PathVariable Long id,
            @Parameter(description = "Usuário manda os dados do ninja a ser atualizado no corpo da requisição")
            @RequestBody NinjaDTO ninjaDTO){

        Optional<NinjaDTO> ninja = ninjaService.listaNinjaId(id);
        if (ninja.isPresent()){
            NinjaDTO ninjaAtualizado = ninjaService.atualizarNinja(id, ninjaDTO).get();
            return ResponseEntity.ok(ninjaAtualizado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ninja com ID: " + id + " não encontrado!");
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletableNinjaPorID(@PathVariable Long id){
        Optional<NinjaDTO> ninja = ninjaService.listaNinjaId(id);
        if (ninja.isPresent()){
            ninjaService.deletarNinjaPorId(id);
            return ResponseEntity.ok("Ninja com (ID): " + id + " deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("ID: " + id + " não encontrado no banco.");
        }
    }
}