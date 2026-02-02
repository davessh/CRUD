import com.david.dao.PersonaDao;
import com.david.dao.TelefonoDao;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestCrud {

    @Test
    void insertarPersona_yTelefonos_funciona() throws Exception {
        PersonaDao personaDao = new PersonaDao();
        TelefonoDao telDao = new TelefonoDao();

        int personaId = personaDao.insertar("Test Integración", "Dirección Test");

        int t1 = telDao.insertar(personaId, "686-142-6639");
        int t2 = telDao.insertar(personaId, "686-223-5816");

        assertTrue(personaId > 0);
        assertTrue(t1 > 0);
        assertTrue(t2 > 0);

        var tels = telDao.obtenerPorPersona(personaId);
        assertEquals(2, tels.size());
        personaDao.eliminar(personaId);

        var telsFinal = telDao.obtenerPorPersona(personaId);
        assertEquals(0, telsFinal.size());
    }
}
