package Database;

import javax.servlet.annotation.WebServlet;

@WebServlet("/MapData")
public class MapData {
	
	private int ID;
	private String title, addr1, addr2;
	private float lat, lon;
	
	public MapData() {
		ID = -1;
		title = "";
		addr1 = "";
		addr2 = "";
		lat = -1;
		lon = -1;
	}
	
	public MapData(int idIN, String titleIN, String addr1IN, String addr2IN, float latIN, float lonIN)
	{
		ID = idIN;
		title = titleIN;
		addr1 = addr1IN;
		addr2 = addr2IN;
		lat = latIN;
		lon = lonIN;
	}
	
	public String toString()
	{
		 return "{\"title\":\"" + title + "\",\"address1\":\"" + addr1 + "\",\"address2\":\"" + addr2 + "\",\"coords\":{\"lat\":" + lat + ",\"lng\":" + lon + "}}";
	}
}
