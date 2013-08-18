import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;


public class TrayTest {

	@Test
	public void test() {
		Block a = new Block("0 0 0 0");
		Block f = new Block("0 0 0 0");
		ArrayList<Block> b=new ArrayList<Block>(2);
		ArrayList<Block> g=new ArrayList<Block>(2);
		b.add(a);
		g.add(a);
		b.add(f);
		g.add(f);
		Tray c = new Tray(5,5,b);
		Tray d = new Tray(5,5,g);
		assertEquals(c,d);

		
	}

}
