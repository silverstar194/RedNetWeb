package webcrawler;
/**
 * @author Admin
 *
 */


import java.io.IOException;
import java.util.*;
import java.net.*;

public class WebCraweler implements Runnable{
	static ArrayList<String> listOfPendingURLs =  new ArrayList<>();
	static ArrayList<String> listOfTranversedURLs = new ArrayList<>();
	static ArrayList<String> possibleAddresses = new ArrayList<>();

	public void run(){
		Scanner inputUser = new Scanner(System.in);
		System.out.println("Enter Start URL: ");
		String url = inputUser.nextLine();

		crawler(url);
		inputUser.close();
	}

	private static void crawler(String url){

		listOfPendingURLs.add(url);

		while(!listOfPendingURLs.isEmpty()){
			String urlString = listOfPendingURLs.get(0);

			if(!listOfTranversedURLs.contains(urlString)){
				listOfTranversedURLs.add(urlString);

				System.out.println("Crawling "+listOfTranversedURLs.size()+" of "+listOfPendingURLs.size()+" "+urlString);

				ArrayList<String> urlList = getSubURLs(urlString);

				for(String s: urlList){

					if(!listOfPendingURLs.contains(s) && 
							!s.contains(".jpg") &&
							!s.contains(".png") &&
							!s.contains(".jpeg") &&
							!s.contains(".gifv") &&
							!s.contains(".mp4") &&
							!s.contains(".mp3") &&
							!s.contains(".webm") &&
							!s.contains(".pdf")){

						listOfPendingURLs.add(s);
					}

				}

				getBitCoinAddress(urlString);

				listOfPendingURLs.remove(0);	

			}
		}
	}

	public static ArrayList<String> getSubURLs(String urlString){
		ArrayList<String> list = new ArrayList<>();

		try {
			URL u = new URL(urlString);
			Scanner in = new Scanner(u.openStream());

			int current = 0;
			while(in.hasNext()){
				String line = in.nextLine();
				current = line.indexOf("http:", current);
				while(current>0){
					int endIndex = line.indexOf("\"", current);
					if(endIndex >0){

						if(checkBlackListURL(line.substring(current, endIndex))){
							list.add(line.substring(current, endIndex));
							current = line.indexOf("http:", endIndex);
						}else{
							current = -1;
						}

					}else{
						current = -1;
					}
				}
			}
			in.close();

		} catch(IOException e){
			System.out.println("Unable to Open URL");

		} catch(IllegalArgumentException e){
			System.out.println("Unable to Open URL");

		}

		return list;
	}

	public static void getBitCoinAddress(String url){
		try {
			URL u = new URL(url);
			Scanner in = new Scanner(u.openStream());

			while(in.hasNextLine()){
				String testString = in.nextLine().replace("\'", ""); //take out of quotes single
				testString.replace("\"", ""); //take out of quotes double
				lookLength(testString, 27 ,32, url);
			}

		} catch(IOException e){
			System.out.println("Unable to Open URL");
		} catch(IllegalArgumentException e){
			System.out.println("Unable to Open URL");
		}

	}

	public static ArrayList<String> lookLength(String searchable, int lengthMin, int lengthMax, String site){


		String[] words = searchable.split(" ");

		for (int i=0; i<words.length; i++) {
			if (words[i].length() >= lengthMin && words[i].length() >=lengthMax && 
					checkIllegalChars(words[i])) {

				possibleAddresses.add(words[i]+" | " +site);
				System.out.println(words[i]);
			}

		}

		return possibleAddresses;
	}

	private static boolean 	checkIllegalChars(String checkString){

		String[] charArray = {",", "/" , ">", "<", "\\" , ")", ";", "*", 
				"=", "-", "+", "$", ".", "|", "_", "]", 
				"[", "{", "}", ":", ";", "`", "!", "+", "&", "%", "@", 
				"%", "#", "?", "^", "Ĩ", "裏", "(", "ա", "¥", "⑃", " ܂", "\"",
				"'", "闇"};


		for(int i=0; i<charArray.length; i++){
			if(checkString.contains(charArray[i])){
				return false;
			}

		}
		return true;

	}

	private static boolean checkBlackListURL(String checkAble){

		String[] charArray = {"www.w3.org", "http://schema.org/", 
				"www.pornhub.com", "www.redtube.com", "www.simplemachines.org",
				"www.mysql.com", "php", "porn", "fuck", "sexy", "hardcore", "milf",
				"amzn", "ebay", "http://www.si.com", "https://www.youtube.com", 
				"tube", "xvideos.com", "xxx", "boobs", "sex", "teens", "imgur", "maps"};

		for(int i=0; i<charArray.length; i++){
			if(checkAble.contains(charArray[i])){
				return false;
			}

		}
		return true;

	}

}


