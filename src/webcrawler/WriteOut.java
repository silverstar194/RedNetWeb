package webcrawler;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @author Admin
 *
 */
public class WriteOut implements Runnable{

	public void run(){

		while(true){
			writeOut("urlFile",WebCraweler.listOfTranversedURLs);
			writeOut("bitcoinAddress", WebCraweler.possibleAddresses);
			
			System.out.println("=====Saved All Data to File=====");
			try {
				ThreadManager.save.sleep((long)1000*60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}


	public static void writeOut(String fileOutName, ArrayList<String> arrayListOut){


		String outString=(System.currentTimeMillis()/1000)+"\n";

		for(int i= 0; i<arrayListOut.size(); i++){
			outString+=arrayListOut.get(i)+"\n";
		}

		try {
			FileWriter writer;
			writer = new FileWriter(fileOutName+".txt");
			writer.write(outString, 0, outString.length());
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}


	}


}
