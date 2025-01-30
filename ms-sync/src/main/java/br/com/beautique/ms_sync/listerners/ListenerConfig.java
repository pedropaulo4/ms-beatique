package br.com.beautique.ms_sync.listerners;

public interface ListenerConfig {

    void listenToCustomerQueue(String message);
    void listenToBeautyProcedureQueue(String message);
    void listenToAppointmentQueue(String message);
}
