package webcrawler;
/**
 * @author Admin
 *
 */

import java.util.*;


public class ThreadManager {

	static Thread web;
	static Thread save;

	public static void main(String args[]){
		WriteOut output = new WriteOut();
		WebCraweler webCrawl = new WebCraweler();


		web = new Thread(webCrawl);
		save = new Thread(output);

		web.start();
		save.start();
	}



}
