package br.com.dio.barbershopui.notification;

import br.com.dio.barbershopui.entity.ScheduleEntity;

public interface NotificationGateway {

    void notifyScheduleCreated(ScheduleEntity schedule);
}
