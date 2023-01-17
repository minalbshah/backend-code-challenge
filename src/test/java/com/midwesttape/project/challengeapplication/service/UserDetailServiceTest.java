package com.midwesttape.project.challengeapplication.service;

import com.midwesttape.project.challengeapplication.exception.UserNotFoundException;
import com.midwesttape.project.challengeapplication.model.Address;
import com.midwesttape.project.challengeapplication.model.ResponseTemplateVO;
import com.midwesttape.project.challengeapplication.model.User;
import com.midwesttape.project.challengeapplication.repository.AddressRepository;
import com.midwesttape.project.challengeapplication.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailServiceTest {
    private static final Long USER_ID = 1L;
    private static final Long ADDRESS_ID = 1L;
    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private UserDetailService userDetailService;
    @BeforeEach
    public void beforeEach() {
        userDetailService = new UserDetailService(userRepository, addressRepository);
    }

    @Test
    public void should_get_User() throws UserNotFoundException {
        Optional<User> optionalUser = Optional.of(getUser());
        Optional<Address> optionalAddress = Optional.of(getAddress());

        when(userRepository.findById(USER_ID)).thenReturn(optionalUser);
        when(addressRepository.findById(ADDRESS_ID)).thenReturn(optionalAddress);

        ResponseTemplateVO responseTemplateVO = userDetailService.getUserWithAddress(USER_ID);
        assertEquals(getUserDetails(), responseTemplateVO);
        assertNotNull(responseTemplateVO);
    }

    @Test
    public void should_get_User_without_Address() throws UserNotFoundException {
        Optional<User> optionalUser = Optional.of(getUserWithoutAddress());

        when(userRepository.findById(USER_ID)).thenReturn(optionalUser);

        ResponseTemplateVO responseTemplateVO = userDetailService.getUserWithAddress(USER_ID);
        assertEquals(getUserDetailsWithoutAddress(), responseTemplateVO);
        assertNotNull(responseTemplateVO);
    }

    @Test
    void should_throw_exception_when_no_user_found() {
        Optional<User> optionalUser = Optional.empty();
        when(userRepository.findById(anyLong())).thenReturn(optionalUser);
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userDetailService.getUserWithAddress(Long.valueOf(3));
        });
    }
    public User getUser() {
        User user = new User();
        return user.setId(USER_ID).setFirstName("Phil").setLastName("Ingwell").setUsername("PhilIngwell")
            .setPassword("Password123").setAddressId(ADDRESS_ID);
    }

    public User getUserWithoutAddress() {
        User user = new User();
        return user.setId(USER_ID).setFirstName("Phil").setLastName("Ingwell").setUsername("PhilIngwell")
            .setPassword("Password123").setAddressId(null);
    }

    public Address getAddress() {
        Address address = new Address();
        return address.setId(ADDRESS_ID).setAddress1("101 Newyork Lane").setAddress2("Block A").setCity("Brentwood")
            .setState("TN").setPostal("37027");
    }


    public ResponseTemplateVO getUserDetails() {
        ResponseTemplateVO responseTemplateVO = new ResponseTemplateVO();
        responseTemplateVO.setUser(getUser());
        responseTemplateVO.setAddress(getAddress());
        return responseTemplateVO;
    }
    public ResponseTemplateVO getUserDetailsWithoutAddress() {
        ResponseTemplateVO responseTemplateVO = new ResponseTemplateVO();
        responseTemplateVO.setUser(getUserWithoutAddress());
        return responseTemplateVO;
    }



}
