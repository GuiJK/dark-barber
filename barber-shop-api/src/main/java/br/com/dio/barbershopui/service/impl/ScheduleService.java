package br.com.dio.barbershopui.service.impl;

import br.com.dio.barbershopui.entity.ScheduleEntity;
import br.com.dio.barbershopui.exception.NotFoundException;
import br.com.dio.barbershopui.notification.NotificationGateway;
import br.com.dio.barbershopui.repository.IClientRepository;
import br.com.dio.barbershopui.repository.IScheduleRepository;
import br.com.dio.barbershopui.service.IScheduleService;
import br.com.dio.barbershopui.service.query.IScheduleQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ScheduleService implements IScheduleService {

    private final IScheduleRepository repository;
    private final IClientRepository clientRepository; // Inject do repositório de clientes
    private final IScheduleQueryService queryService;
    private final NotificationGateway notificationGateway;

    @Override
    @Transactional
    public ScheduleEntity save(final ScheduleEntity entity) {
        queryService.verifyIfScheduleExists(entity.getStartAt(), entity.getEndAt());

        // Garante que o cliente completo (com nome e telefone) seja buscado do banco
        if (entity.getClient() != null && entity.getClient().getId() != null) {
            var fullClient = clientRepository.findById(entity.getClient().getId())
                    .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
            entity.setClient(fullClient);
        }

        var saved = repository.save(entity);

        // Dispara a notificação com a entidade populada
        notificationGateway.notifyScheduleCreated(saved);

        return saved;
    }

    @Override
    @Transactional
    public void delete(final long id) {
        queryService.findById(id);
        repository.deleteById(id);
    }
}