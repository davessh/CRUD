import com.david.logica.PruebaValidaciones;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestValidaciones {

    @Test
    void nombreVacio_lanzaError() {
        assertThrows(IllegalArgumentException.class, () ->
                PruebaValidaciones.nombreObligatorio("   "));
    }

    @Test
    void nombreCorrecto_noLanza() {
        assertDoesNotThrow(() ->
                    PruebaValidaciones.nombreObligatorio("David"));
    }

    @Test
    void telefonoInvalido_lanzaError() {
        assertThrows(IllegalArgumentException.class, () ->
                PruebaValidaciones.telefonoValido("abc###"));
    }

    @Test
    void telefonoValido_noLanza() {
        assertDoesNotThrow(() ->
                PruebaValidaciones.telefonoValido("686-123-4567"));
    }
}

