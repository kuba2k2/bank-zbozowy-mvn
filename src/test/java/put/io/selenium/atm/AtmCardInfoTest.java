package put.io.selenium.atm;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AtmCardInfoTest {

	private AtmCardInfo fixture = null;
	
	@Before
	public void setUp() throws Exception {
		fixture = new AtmCardInfo();
	}

	@After
	public void tearDown() throws Exception {
		fixture = null;
	}

	@Test
	public void testCardIn() {
		fixture.cardIn();
		assertTrue(fixture.isCardIn);
	}

	@Test
	public void testTryPinCorrect() {
		fixture.cardIn();
		fixture.tryPin("1337");
		assertTrue(fixture.pinWasOk);
	}
	
	@Test
	public void testTryPinIncorrect() {
		fixture.cardIn();
		fixture.tryPin("1338");
		assertFalse(fixture.pinWasOk);
		assertEquals(2, fixture.pinTriesLeft);
	}
	
	@Test
	public void testTryPinCardLocked() {
		fixture.cardIn();
		fixture.tryPin("1338");
		fixture.tryPin("1336");
		fixture.tryPin("1335");
		assertFalse(fixture.pinWasOk);
		assertTrue(fixture.isCardLocked);
		assertEquals(0, fixture.pinTriesLeft);
	}

	@Test
	public void testTryWithdraw() {
		fixture.cardIn();
		fixture.tryPin("1337");
		assertEquals(1000, fixture.balance);
		fixture.tryWithdraw("400");
		assertEquals(600, fixture.balance);
	}

	@Test
	public void testCardOut() {
		assertFalse(fixture.isCardIn);
		fixture.cardIn();
		assertTrue(fixture.isCardIn);
		fixture.cardOut();
		assertFalse(fixture.isCardIn);
	}

}
