package com.example.services.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.datamodels.models.user.UserEvent;

import lombok.Getter;

@Component
@Getter
public class UserEventListener{
    private final List<ActionListener> listeners = new ArrayList<>();
    
    private UserEvent receivedEvent;

    /**
     * Метод для добавления слушателя
     * (подписка на события)
     * @param listener Слушатель
     */
    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    /**
     * Вызывается каждый раз, когда
     * kafka присылает уведомления
     * @param event Событие
     */
    @KafkaListener(
        topics="${spring.kafka.user.topic}", 
        groupId="${spring.kafka.user.group}", 
        containerFactory="userListenerContainerFactory")
    public void listenUserEvents(UserEvent event){
        ActionEvent actionEvent = new ActionEvent(event, ActionEvent.ACTION_PERFORMED, UserEventListener.Events.onUserEvent.toString());
        listeners.stream().forEach(x -> x.actionPerformed(actionEvent));
        
        this.receivedEvent = event;
    }

    /** Список событий генерируемые этим классом */
    public static enum Events {
        onUserEvent
    }
}