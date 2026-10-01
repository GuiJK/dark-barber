package br.com.dio.barbershopui.notification;

import br.com.dio.barbershopui.entity.ScheduleEntity;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class WhatsAppNotificationGateway implements NotificationGateway {

    private static final Logger log = LoggerFactory.getLogger(WhatsAppNotificationGateway.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");

    // Template padrão mantido para contas Trial/Sandbox
    private static final String SANDBOX_TEMPLATE_SID = "HXfe5ab5f00277942d4d4200328b4d403c";

    private final String whatsAppFrom;

    public WhatsAppNotificationGateway(
            @Value("${twilio.account-sid}") String accountSid,
            @Value("${twilio.auth-token}") String authToken,
            @Value("${twilio.whatsapp-from}") String whatsAppFrom) {
        Twilio.init(accountSid, authToken);
        this.whatsAppFrom = whatsAppFrom;
    }

    @Override
    @Async
    public void notifyScheduleCreated(ScheduleEntity schedule) {
        var client = schedule.getClient();
        try {
            if (client == null || client.getPhone() == null) {
                log.warn("Cliente ou telefone não encontrado para o agendamento {}", schedule.getId());
                return;
            }

            // 1. Sanitização do telefone para formato E.164
            String rawPhone = client.getPhone().replaceAll("\\D", "");
            if (!rawPhone.startsWith("55")) {
                rawPhone = "55" + rawPhone;
            }
            String toPhone = "whatsapp:+" + rawPhone;

            // 2. Personalização das variáveis mantendo conformidade com a Sandbox
            String clientName = client.getName() + " 💈 (Dark Barber)";
            String dateFormatted = schedule.getStartAt().format(FORMATTER);

            // Injeção de variáveis em formato JSON
            String jsonVariables = "{\"1\":\"" + clientName + "\",\"2\":\"" + dateFormatted + "\"}";

            // 3. Disparo via Twilio SDK
            Message message = Message.creator(
                            new PhoneNumber(toPhone),
                            new PhoneNumber(whatsAppFrom),
                            ""
                    )
                    .setContentSid(SANDBOX_TEMPLATE_SID)
                    .setContentVariables(jsonVariables)
                    .create();

            log.info("Notificação WhatsApp enviada via Template com sucesso para o cliente {}. SID: {}", client.getId(), message.getSid());

        } catch (Exception e) {
            Long clientId = client != null ? client.getId() : null;
            log.error("Falha ao enviar notificação WhatsApp para o cliente {}: {}", clientId, e.getMessage());
        }
    }
}