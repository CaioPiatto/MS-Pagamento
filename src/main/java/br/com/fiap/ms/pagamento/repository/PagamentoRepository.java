package br.com.fiap.ms.pagamento.repository;

import br.com.fiap.ms.pagamento.entities.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
