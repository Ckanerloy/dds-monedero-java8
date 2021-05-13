package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MonederoTest {
  private Cuenta cuenta;

  @BeforeEach
  void init() {
    cuenta = new Cuenta();
  }

  @Test
  void RealizarDepositoCorrectamente() {
    cuenta.depositar(1500);
    cuenta.depositar(456);
    assertEquals(cuenta.getSaldo(),1956);
    assertEquals(cuenta.getMovimientos().stream().count(),2);
  }

  @Test
  public void RealizarExtraccionCorrectamente() {
    cuenta.setSaldo(1000);
    cuenta.extraer(200);
    assertEquals(cuenta.getSaldo(),800);
    assertFalse(cuenta.getMovimientos().isEmpty());
  }

  @Test
  void NosePuedeDepositarMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.depositar(-1500));
  }

  @Test
  void NosePuedeMasDeTresDepositos() {
    assertThrows(MaximaCantidadDepositosException.class, () -> {
          cuenta.depositar(1500);
          cuenta.depositar(456);
          cuenta.depositar(1900);
          cuenta.depositar(245);
    });
  }

  @Test
  void NosePuedeExtraerMasQueElSaldo() {
    assertThrows(SaldoMenorException.class, () -> {
          cuenta.setSaldo(90);
          cuenta.extraer(1001);
    });
  }

  @Test
  public void NosePuedeExtraerMasDe1000() {
    assertThrows(MaximoExtraccionDiarioException.class, () -> {
      cuenta.setSaldo(5000);
      cuenta.extraer(1001);
    });
  }

  @Test
  public void NosePuedeExtraerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.extraer(-500));
  }

}