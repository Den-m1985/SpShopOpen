package ru.spshop.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.spshop.model.Contact;
import ru.spshop.model.User;
import ru.spshop.repositories.ContactRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;

    public Contact findContactById(Integer contactId) {
        return contactRepository.findById(contactId)
                .orElseThrow(() -> new EntityNotFoundException("Contact with ID " + contactId + " not found"));
    }

    public Contact getContactByUser(User userId) {
        return contactRepository.findByUser(userId)
                .orElseThrow(() -> new EntityNotFoundException("Contact with userId: " + userId + " not found"));
    }

    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

}
