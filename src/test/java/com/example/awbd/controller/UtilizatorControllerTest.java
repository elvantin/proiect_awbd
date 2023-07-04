/**
 * Testează funcționalitatea metodei getAllUtilizatori() din UtilizatorController.
 * Verifică dacă lista de utilizatori returnată conține toți utilizatorii din repository și
 * dacă codul de stare al răspunsului este OK (200).
 */

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
        // Se creează o listă de obiecte Utilizator pentru a simula datele returnate de repository
        List<Utilizator> utilizatorList = new ArrayList<>();
        utilizatorList.add(new Utilizator(1L, "utilizator1", "Nume1", "Prenume1", "email1@example.com", "parola1"));
        utilizatorList.add(new Utilizator(2L, "utilizator2", "Nume2", "Prenume2", "email2@example.com", "parola2"));

        // Se configurează comportamentul mock-ului utilizatorRepo pentru a returna lista de utilizatori
        when(utilizatorRepo.findAll()).thenReturn(utilizatorList);

        // Act
        // Se apelează metoda getAllUtilizatori() din utilizatorController pentru a obține răspunsul
        ResponseEntity<List<Utilizator>> responseEntity = utilizatorController.getAllUtilizatori();

        // Assert
        // Se verifică că codul de stare din răspuns este OK (200)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Se verifică că lista de utilizatori returnată în corpul răspunsului este aceeași cu lista de utilizatori configurată anterior
        assertEquals(utilizatorList, responseEntity.getBody());
        // Se verifică că metoda findAll() a fost apelată o singură dată pe mock-ul utilizatorRepo
        verify(utilizatorRepo, times(1)).findAll();
    }

}