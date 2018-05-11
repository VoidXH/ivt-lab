package hu.bme.mit.spaceship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class GT4500Test {
  private GT4500 ship;
  private TorpedoStore mockPrimary, mockSecondary;

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {
            { true, true, true },
            { true, false, true },
            { false, true, true },
            { false, false, false }
    });
  }

  private boolean primaryWorks, secondaryWorks, expected;

  public GT4500Test(boolean primary, boolean secondary, boolean assertion) {
    primaryWorks = primary;
    secondaryWorks = secondary;
    expected = assertion;
  }

  void setEmpty(boolean value) {
    when(mockPrimary.isEmpty()).thenReturn(value);
    when(mockSecondary.isEmpty()).thenReturn(value);
  }

  @Before
  public void init() {
    mockPrimary = Mockito.mock(TorpedoStore.class);
    mockSecondary = Mockito.mock(TorpedoStore.class);
    this.ship = new GT4500(mockPrimary, mockSecondary);
  }

  @Test
  public void fireTorpedo_All() {
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(primaryWorks);
    when(mockSecondary.fire(1)).thenReturn(secondaryWorks);
    setEmpty(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(expected, result);
  }

  @Test
  public void fireTorpedo_Single() {
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(primaryWorks);
    when(mockSecondary.fire(1)).thenReturn(secondaryWorks);
    setEmpty(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(expected, result);
  }

  @Test
  public void fireTorpedo_Alternate(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(primaryWorks);
    when(mockSecondary.fire(1)).thenReturn(secondaryWorks);
    setEmpty(false);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    when(mockPrimary.fire(1)).thenReturn(!primaryWorks);
    when(mockSecondary.fire(1)).thenReturn(!secondaryWorks);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(primaryWorks && secondaryWorks ? false : expected, result1 && result2);
  }

  @Test
  public void fireTorpedo_Empty(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(primaryWorks);
    when(mockSecondary.fire(1)).thenReturn(secondaryWorks);
    setEmpty(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
  }
}
