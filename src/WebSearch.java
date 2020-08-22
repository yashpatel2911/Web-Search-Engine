import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
 


public class WebSearch<E> {
 
	List<String> stopwords = Arrays.asList("a", "able", "about",
			"across", "after", "all", "almost", "also", "am", "among", "an",
			"and", "any", "are", "as", "at", "be", "because", "been", "but",
			"by", "can", "cannot", "could", "dear", "did", "do", "does",
			"either", "else", "ever", "every", "for", "from", "get", "got",
			"had", "has", "have", "he", "her", "hers", "him", "his", "how",
			"however", "i", "if", "in", "into", "is", "it", "its", "just",
			"least", "let", "like", "likely", "may", "me", "might", "most",
			"must", "my", "neither", "no", "nor", "not", "of", "off", "often",
			"on", "only", "or", "other", "our", "own", "rather", "said", "say",
			"says", "she", "should", "since", "so", "some", "than", "that",
			"the", "their", "them", "then", "there", "these", "they", "this",
			"tis", "to", "too", "twas", "us", "wants", "was", "we", "were",
			"what", "when", "where", "which", "while", "who", "whom", "why",
			"will", "with", "would", "yet", "you", "your");
 
	Map<String, List<Tuple>> index = new HashMap<String, List<Tuple>>();
	List<String> files = new ArrayList<String>();
	
	// function to sort hashmap by values 
    public static HashMap<Integer, Integer> sortByValue(HashMap<Integer, Integer> hm) 
    { 
        // Create a list from elements of HashMap 
        List<Map.Entry<Integer, Integer> > list = 
               new LinkedList<Map.Entry<Integer, Integer> >(hm.entrySet()); 
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer> >() { 
            public int compare(Map.Entry<Integer, Integer> o1,  
                               Map.Entry<Integer, Integer> o2) 
            { 
                return (o1.getValue()).compareTo(o2.getValue()); 
            } 
        }); 
          
        // put data from sorted list to hashmap  
        HashMap<Integer, Integer> temp = new LinkedHashMap<Integer, Integer>(); 
        for (Map.Entry<Integer, Integer> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    } 
 
	public void indexFile(File file) throws IOException {
		int fileno = files.indexOf(file.getPath());
		if (fileno == -1) {
			files.add(file.getPath());
			fileno = files.size() - 1;
		}
 
		int pos = 0;
		BufferedReader reader = new BufferedReader(new FileReader(file));
		for (String line = reader.readLine(); line != null; line = reader
				.readLine()) {
			for (String _word : line.split("\\W+")) {
				String word = _word.toLowerCase();
				pos++;
				if (stopwords.contains(word))
					continue;
				List<Tuple> idx = index.get(word);
				if (idx == null) {	
					idx = new LinkedList<Tuple>();
					index.put(word, idx);
				}
				idx.add(new Tuple(fileno, pos));
			}
		}
//		System.out.println("indexed " + file.getPath() + " " + pos + " words");
	}
 
	public Set search(List<String> words) {
		int count=0;
		Set<String> answer = new HashSet<String>();
		int prev=0;
		int a[]= new int[50];
		int b[] = new int[50];
		for (int j=0;j<a.length;j++)
		{
			a[j]=0;
			b[j]=0;
		}
		for (String _word : words) {
			//Set<String> answer = new HashSet<String>();
			String word = _word.toLowerCase();
			List<Tuple> idx = index.get(word);
			ArrayList l = new ArrayList<E>();
			ArrayList m = new ArrayList<E>();
			HashMap<Integer, Integer> hmap = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> sortedhmap = new HashMap<Integer, Integer>();

			
			for (Tuple t : idx) {
				prev = t.fileno;
				a[t.fileno]++;
				b[t.fileno]++;
				
//				System.out.println(t.fileno);
				
				l.add(t.fileno);
				count++;
				
			}
				//System.out.println(count);
				for (int k = 0;k<a.length;k++)
				{
					if(a[k]!=0)
					{
						
						//System.out.println(k +" -  "  +a[k]);
						hmap.put(k, a[k]);
						//System.out.println(k +" -  "  +hmap.get(k));	
					}
				}
				//Sorting of the hashmap based on values
				//System.out.println("after sorting");
				
				Map<Integer, Integer> hm1 = sortByValue(hmap); 
				  
		        // print the sorted hashmap 
//		        for (Entry<Integer, Integer> en : hm1.entrySet()) { 
//		            System.out.println("Key = " + en.getKey() +  
//		                          ", Value = " + en.getValue()); 
//		        }
//			
			
			
//			for (int k = 0;k<a.length;k++)
//			{
//				l.add(a[k]);
//				m.add(k);
//			}
//			l.sort();
			
			if (idx != null) {
				for (Tuple t : idx) {
					
					count++;
					answer.add(files.get(t.fileno));
					
				}
			}
			
			//System.out.print(word);
//			for (String f : answer) {
//				System.out.print("\n " + f);
//			}
			System.out.println("");
			
		}
		
		return answer;
	}
 
	public static void main(String[] args) {
		try {
			WebSearch<?> idx = new WebSearch();
			File wholeFolder = new File("medium_text");
		    File[] List_Of_Files = wholeFolder.listFiles();
		    Scanner s = new Scanner(System.in);
			BufferedReader br;
			
		    for (int i = 0; i < 50; i++) {
				idx.indexFile(List_Of_Files[i]);

//				br = new BufferedReader(new FileReader("Html_to_text1/"+List_Of_Files[i].getName()+".txt"));
		    }	

//			for (int i = 1; i < args.length; i++) {
//				idx.indexFile(new File(args[i]));
//			}
		    int opt=1;
		    System.out.println("------------------------------------------");
		    System.out.println("Press 1 to continue. 0 to exit");
		    opt = s.nextInt();
//		    System.out.println("\n");
		    while(opt!=0)
		    {
		    System.out.println("Enter the query you want to search:\n");
		    
		    String str = s.nextLine();
		     str = s.nextLine();

		    String[] al = str.split(" ");
			Set<String> answer = new HashSet<String>();
			answer = idx.search(Arrays.asList(al));
			
			int temp=0;
			
			//System.out.println("after main:");
			System.out.println("How many Results you would like:");
			int no = s.nextInt();
			System.out.println("");
			int count = 0;
			if(answer.size()==0)
			{
				System.out.println("Google it");
//				System.exit(0);
			}
			else if(no>answer.size())
			{
				System.out.println("The entered number is greater than total results\n");
//				System.exit(0);
			}
			else
			{
				for (String f : answer) {
				
					if(count<no)
					{
						System.out.print(f.substring(0,f.length()-4)+"\n" );
						count++;
					}
					else
					{
						break;
					}
				
				}
			}
		    System.out.println("------------------------------------------\n");

			System.out.println("Press 1 to continue. 0 to exit");
		    opt = s.nextInt();
		    System.out.println("\n");
		    
			
		}
		}
		catch(Exception e)
		    {
			
			System.out.println("Google it");
			System.out.println("------------------------------------------\n");

			System.out.println("Press 1 to continue. 0 to exit");
			
		    }
			
		} 
 
	private class Tuple {
		private int fileno;
		private int position;
 
		public Tuple(int fileno, int position) {
			this.fileno = fileno;
			this.position = position;
		}
	}
}