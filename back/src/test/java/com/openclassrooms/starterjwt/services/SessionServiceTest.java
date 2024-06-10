package com.openclassrooms.starterjwt.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.exception.BadRequestException;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {
	
     @Mock
     private SessionRepository sessionRepository;

     @Mock
     private UserRepository userRepository;

     @InjectMocks
     private SessionService sessionService;
     
     private Session session;
     private User user;
     
     @BeforeEach
     void setUp() {
         session = new Session();
         session.setId(1L);
         session.setUsers(new ArrayList<>());

         user = new User();
         user.setId(1L);
     }
	
	 @Test
	 void testParticipate_userNotFound_shouldReturnNotFoundException() {
	     Long sessionId = 1L;
	     Long userId = 1L;

	     when(userRepository.findById(userId)).thenReturn(Optional.empty());
	     when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

	     assertThrows(NotFoundException.class, () -> sessionService.participate(sessionId, userId));

	     verify(userRepository).findById(userId);
	     verify(sessionRepository).findById(sessionId); 
	 }
	 
	 @Test
	 void testParticipate_sessionNotFound_shouldReturnNotFoundException() {
		 Long sessionId = 1L;
	     Long userId = 1L;
	     
	     when(userRepository.findById(userId)).thenReturn(Optional.of(user));
	     when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());
		 
	     assertThrows(NotFoundException.class, () -> sessionService.participate(sessionId, userId));

	     verify(userRepository).findById(userId);
	     verify(sessionRepository).findById(sessionId); 
	 }
	 
	 @Test
	 void testParticipate_alreadyParticipate_shouldReturnBadRequest() {
		 Long sessionId = 1L;
	     Long userId = 1L;
	     
	     session.getUsers().add(user);
	     
	     when(userRepository.findById(userId)).thenReturn(Optional.of(user));
	     when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

	     assertThrows(BadRequestException.class, () -> sessionService.participate(sessionId, userId));

	     verify(userRepository).findById(userId);
	     verify(sessionRepository).findById(sessionId);
	 }
	 
	 @Test
	 void testParticipate_success() {
		  Long sessionId = 1L;
	      Long userId = 1L;

	      when(userRepository.findById(userId)).thenReturn(Optional.of(user));
	      when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

	      sessionService.participate(sessionId, userId);

	      verify(userRepository).findById(userId);
	      verify(sessionRepository).findById(sessionId);
	      verify(sessionRepository).save(session);
	 }
	 
	 @Test
	 void testNoLongerParticipate_Success() {
		 User user2 = new User();
		 user2.setId(2L);
		 
	     session.getUsers().add(user);
		 session.getUsers().add(user2);
		 
		 Long sessionId = 1L;
	     Long userId = 2L; 
	     
	     when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
	     
	     sessionService.noLongerParticipate(sessionId, userId);
	     
	     List<User> remainingUsers = session.getUsers();
	     assertEquals(1, remainingUsers.size());
	     assertEquals(user.getId(), remainingUsers.get(0).getId());
	 }
	 

	 
	 
	 
	 


}
