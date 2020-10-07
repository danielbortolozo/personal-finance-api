package br.com.sisdb.myfinances.api;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LancamentoDTO {

   private Long id;
   private String descricao;
   private Integer mes;
   private Integer ano;
   private BigDecimal valor;
   private Long idUsuario;
   private String tipo;
   private String status;



}
