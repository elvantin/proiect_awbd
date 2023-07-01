import com.example.awbd.controller.UtilizatorController;
import com.example.awbd.model.Utilizator;
import com.example.awbd.repo.UtilizatorRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UtilizatorControllerTest {

    @Mock
    private UtilizatorRepo utilizatorRepo;

    @InjectMocks
    private UtilizatorController utilizatorController;

    @Test
    public void testGetAllUtilizatori() {
        // Arrange
        List<Utilizator> utilizatorList = new ArrayList<>();
        utilizatorList.add(new Utilizator(1L, "utilizator1", "Nume1", "Prenume1", "email1@example.com", "parola1"));
        utilizatorList.add(new Utilizator(2L, "utilizator2", "Nume2", "Prenume2", "email2@example.com", "parola2"));

        when(utilizatorRepo.findAll()).thenReturn(utilizatorList);

        // Act
        ResponseEntity<List<Utilizator>> responseEntity = utilizatorController.getAllUtilizatori();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(utilizatorList, responseEntity.getBody());
        verify(utilizatorRepo, times(1)).findAll();
    }

   }
