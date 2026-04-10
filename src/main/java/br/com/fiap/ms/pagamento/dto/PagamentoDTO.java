package br.com.fiap.ms.pagamento.dto;

import br.com.fiap.ms.pagamento.entities.Pagamento;
import br.com.fiap.ms.pagamento.entities.Status;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoDTO {
    private Long id;

    @NotNull(message = "O campo valor é obrigatório")
    @Positive(message = "O valor do pagamento deve ser um número positivo")
    private BigDecimal valor;

    @NotBlank(message = "O campo nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "O campo número do cartão é obrigatório")
    @Size(min = 16, max = 16, message = "Número do cartão deve ter 16 caracteres")
    private String numeroCartao;

    @NotBlank(message = "O campo validade é obrigatório")
    @Size(min = 5, max = 5, message = "Validade do cartão deve ter 5 caracteres")
    private String validade;

    @NotBlank(message = "O código de segurança é obrigatório")
    @Size(min = 3, max = 3, message = "Código de segurança deve ter 3 caracteres")
    private String codigoSeguranca;

    private Status status;

    @NotNull(message = "O campo ID do pedido é obrigatório")
    private Long pedidoId;

    public PagamentoDTO(Pagamento pagamento) {
        this.id = pagamento.getId();
        this.valor = pagamento.getValor();
        this.nome = pagamento.getNome();
        this.numeroCartao = pagamento.getNumeroCartao();
        this.validade = pagamento.getValidade();
        this.codigoSeguranca = pagamento.getCodigoSeguranca();
        this.status = pagamento.getStatus();
        this.pedidoId = pagamento.getPedidoId();
    }
}