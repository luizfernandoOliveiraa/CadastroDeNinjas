package dev.java10x.CadastroDeNinjas.Ninjas;

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
    public ResponseEntity<String> apiFuncionando(){
        return ResponseEntity.ok("API funcionando!");
    }

    @PostMapping("/criar")
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
    public ResponseEntity<?> alterarNinjaPorID(@PathVariable Long id, @RequestBody NinjaDTO ninjaDTO){
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