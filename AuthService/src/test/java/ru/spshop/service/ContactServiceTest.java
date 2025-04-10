package ru.spshop.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import ru.spshop.bootstrap.RoleSeeder;
import ru.spshop.model.Contact;
import ru.spshop.model.Role;
import ru.spshop.model.User;
import ru.spshop.model.enums.RoleEnum;
import ru.spshop.repositories.ContactRepository;
import ru.spshop.repositories.UserRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class ContactServiceTest {
    @Autowired
    private ContactService contactService;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleSeeder roleSeeder;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private Contact contact;
    private User user;
    private final String email = "jane.doe@example.com";
    private final String password = "password";
    private final String phoneNumber = "+1234567890";

    @BeforeEach
    void setUp() {
        contactRepository.deleteAll();
        userRepository.deleteAll();

        Role role = roleSeeder.findRoleByName(RoleEnum.USER);
        user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user = userService.saveUser(user);

        contact = new Contact();
        contact.setUser(user);
        contact.setPhoneNumber(phoneNumber);
        contact = contactService.saveContact(contact);
    }

    @Test
    void shouldFindContactById() {
        Contact result = contactService.findContactById(contact.getId());
        assertNotNull(result);
        assertEquals(phoneNumber, result.getPhoneNumber());
    }

    @Test
    void shouldThrowWhenContactIdNotFound() {
        assertThatThrownBy(() -> contactService.findContactById(contact.getId() + 1))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldGetContactByUserId() {
        Contact result = contactService.getContactByUser(user);
        assertNotNull(result);
        assertEquals(phoneNumber, result.getPhoneNumber());
    }

    @Test
    void shouldThrowWhenContactByUserIdNotFound() {
        assertThatThrownBy(() -> contactService.getContactByUser(null))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldReturnAllContacts() {
        List<Contact> contacts = contactService.findAll();
        assertEquals(1, contacts.size());
        assertEquals(phoneNumber, contacts.get(0).getPhoneNumber());
    }

    @Test
    void shouldSaveContactWithValidPhoneNumber() {
        Contact validContact = new Contact();
        validContact.setUser(user);
        validContact.setPhoneNumber("+1234567890123"); // корректный номер

        Contact saved = contactService.saveContact(validContact);
        assertNotNull(saved.getId());
        assertEquals("+1234567890123", saved.getPhoneNumber());
    }

    @Test
    void shouldThrowWhenPhoneNumberDoesNotStartWithPlus() {
        Contact invalidContact = new Contact();
        invalidContact.setUser(user);
        invalidContact.setPhoneNumber("1234567890"); // нет +

        assertThrows(ConstraintViolationException.class, () -> contactService.saveContact(invalidContact));
    }

    @Test
    void shouldThrowWhenPhoneNumberContainsLetters() {
        Contact invalidContact = new Contact();
        invalidContact.setUser(user);
        invalidContact.setPhoneNumber("+123ABC456"); // содержит буквы

        assertThrows(ConstraintViolationException.class, () -> contactService.saveContact(invalidContact));
    }

    @Test
    void shouldThrowWhenPhoneNumberTooShort() {
        Contact invalidContact = new Contact();
        invalidContact.setUser(user);
        invalidContact.setPhoneNumber("+1"); // слишком короткий

        assertThrows(ConstraintViolationException.class, () -> contactService.saveContact(invalidContact));
    }

    @Test
    void shouldThrowWhenPhoneNumberTooLong() {
        Contact invalidContact = new Contact();
        invalidContact.setUser(user);
        invalidContact.setPhoneNumber("+12345678901234567890123"); // слишком длинный

        assertThrows(ConstraintViolationException.class, () -> contactService.saveContact(invalidContact));
    }
}
