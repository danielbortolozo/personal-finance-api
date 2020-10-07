package br.com.sisdb.myfinances.api.resource;

import br.com.sisdb.myfinances.api.LancamentoDTO;
import br.com.sisdb.myfinances.enums.StatusLancamento;
import br.com.sisdb.myfinances.enums.TipoLancamento;
import br.com.sisdb.myfinances.exception.RegraNegocioException;
import br.com.sisdb.myfinances.model.entity.Lancamento;
import br.com.sisdb.myfinances.model.entity.Usuario;
import br.com.sisdb.myfinances.service.LancamentoService;
import br.com.sisdb.myfinances.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoResource {

    private LancamentoService service;
    private UsuarioService usuarioService;

    public LancamentoResource(LancamentoService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity salvar(@RequestBody LancamentoDTO dto) {
        try {
            Lancamento entidade = converter(dto);
            entidade = service.salvar(entidade);
            return new ResponseEntity(entidade, HttpStatus.CREATED);
        }catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("{id}")
    private ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LancamentoDTO dto ) {
       return service.obterPorId(id).map( entity -> {
          try {
              Lancamento lancamento = converter(dto);
              lancamento.setId(entity.getId());
              service.atualizar(lancamento);
              return ResponseEntity.ok(lancamento);
          }catch (RegraNegocioException e) {
              return ResponseEntity.badRequest().body(e.getMessage());
          }
        }).orElseGet(() -> new ResponseEntity("Lançamentos não encontrados na base de dados.",
               HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar (@PathVariable("id") Long id){
        return  service.obterPorId(id).map( entidade -> {
            service.deletar(entidade);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> new ResponseEntity("Lançamento não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
    }

    private Lancamento converter(LancamentoDTO dto) {
        Lancamento lancamento = new Lancamento();
        lancamento.setId(dto.getId());
        lancamento.setDescricao(dto.getDescricao());
        lancamento.setAno(dto.getAno());
        lancamento.setMes(dto.getMes());
        lancamento.setValor(dto.getValor());
        Usuario usuario =  usuarioService
                .obterPorId(dto.getIdUsuario())
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado para o id informado."));
        lancamento.setUsuario(usuario);
        lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
        lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
        return lancamento;
    }
}
