package de.seven.fate.message.service;

import de.seven.fate.message.dao.MessageDAO;
import de.seven.fate.message.model.Message;

import javax.inject.Inject;

/**
 * Created by Mario on 15.02.2016.
 */
public class MessageService {

    @Inject
    private MessageDAO dao;


    public Message getMessage(Message model) {

        return dao.get(model);
    }

    public void removeMessage(Message model) {

        dao.remove(model);
    }
}
