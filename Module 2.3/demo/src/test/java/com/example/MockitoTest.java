package com.example;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.Core.Entities.User.User;
import com.example.Core.Services.UserService;

/**
 * Unit test for simple App.
 */
public class MockitoTest 
{
    private UserService service;

    @Before
    public void OnStartupTest(){
        service = mock(UserService.class);
    }

    @Test
    public void shouldFindUserById(){
        User testUser = getTestUser();
        
        when(service.findUserById(testUser.getId())).thenReturn(testUser);

        User user = service.findUserById(testUser.getId());

        verify(service, times(1)).findUserById(testUser.getId());
        
        assertNotNull(user);
        assertEquals(testUser, user);
    }

    @Test
    public void shouldSaveUser(){
        ArgumentCaptor<String> strCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> intCaptor = ArgumentCaptor.forClass(Integer.class);

        User testUser = getTestUser();

        service.saveUser(testUser.getName(), testUser.getAge(), testUser.getEmail());

        verify(service, times(1)).saveUser(strCaptor.capture(), intCaptor.capture(), strCaptor.capture());
        
        assertEquals(testUser.getName(), strCaptor.getAllValues().get(0));
        assertEquals(testUser.getAge(), (int)intCaptor.getValue());
        assertEquals(testUser.getEmail(), strCaptor.getAllValues().get(1));
    }

    @Test
    public void shouldUpdateUser(){
        User testUser = getTestUser();
        int id = testUser.getId();
        String name = "username";

        when(service.updateUser(id, name)).thenReturn(true);

        boolean isUpdated = service.updateUser(testUser.getId(), "username");

        assertTrue(isUpdated == true);
        verify(service, times(1)).updateUser(testUser.getId(), "username");
    }

    @Test
    public void shouldDeleteUser(){
        int id = 0;

        when(service.deleteUserById(id)).thenReturn(true);

        boolean isDeleted = service.deleteUserById(id);
        
        assertTrue(isDeleted == true);
        verify(service, times(1)).deleteUserById(id);
    }
    

    private User getTestUser(){
        return new User("username", 19, "username@mail.com");
    }
}
