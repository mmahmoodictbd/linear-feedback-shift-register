package org.redmind.ai.lfsr;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class LFSR {
	
	private String seed;
	
	private LFSR(){}
	
	public LFSR(String seed) {
		
		if(seed.isEmpty() || seed.length() < 2 || seed.length() > 32 || binToInt(seed)==0){
			throw new IllegalArgumentException();
		}
		
		
		this.seed = seed;
	}
	
	public List<String> generatePseudoRandomSequences(){
		
		List<String> pseudoRandomSequencesList = new ArrayList<String>();
		int seedLength = seed.length();
		int totalSequence = (int)Math.pow(2, seedLength);
		int[] taps = getTaps(seedLength);
		
		StringBuffer tempSequence = new StringBuffer(seed), tempSequence1;
		boolean tapIn1, tapIn2, tapOut;
		
		for(int i=0; i< totalSequence -1; i++){
			
			pseudoRandomSequencesList.add(new String(tempSequence));
			tempSequence1 = leftShift(tempSequence);
			for(int tap : taps){
				tapIn1 = tempSequence.charAt(seedLength - tap + 1)=='1'? true:false;
				tapIn2 = tempSequence.charAt(0)=='1'? true:false;
				tapOut = tapIn1 ^ tapIn2;
				tempSequence1.setCharAt(seedLength - tap, tapOut? '1':'0');
			}
			tempSequence = tempSequence1;
			System.gc();
		}

		return pseudoRandomSequencesList;
		
	}
	
	private StringBuffer leftShift(StringBuffer sequence) {
		StringBuffer sb = new StringBuffer();
		sb.append(sequence.substring(1, sequence.length()));
		sb.append(sequence.charAt(0));
		return sb;
	}
	
	private int[] getTaps(int degree) {
		
		int[] taps = null;
		
		switch (degree) {
		
			case 2:case 3:case 4:case 6:case 7:case 15:case 22:
				taps = new int[]{2};//x^n + x + 1
				break;
			case 5:case 11:case 21:case 29:
				taps = new int[]{3};//x^n + x^2 + 1
				break;
			case 8:case 19:
				taps = new int[]{2, 6, 7};//x^n + x^6 + x^5 + x + 1
				break;
			case 9:
				taps = new int[]{5};
				break;
			case 10:case 17:case 20:case 25:case 28:case 31:
				taps = new int[]{4};
				break;
			case 12:
				taps = new int[]{4, 5, 8}; //x^n + x^7 + x^4 + x^3 + 1
				break;
			case 13:case 24:
				taps = new int[]{2, 4, 5};
				break;
			case 14:
				taps = new int[]{2, 12, 13};//x^n + x^12 + x^11 + x + 1
				break;
			case 16:
				taps = new int[]{3, 4, 6};
				break;
			case 18:
				taps = new int[]{8};
				break;
			case 23:
				taps = new int[]{6};
				break;
			case 26:case 27:
				taps = new int[]{2, 8, 9};
				break;
			case 30:
				taps = new int[]{2, 16, 17};//x^n + x^16 + x^15 + x + 1
				break;
			case 32:
				taps = new int[]{3, 7, 8};//x^n + x^7 + x^6 + x^2 + 1
				break;
		}
		
		return taps;
	}
	
	private int binToInt(String bin) {
		
		int intValue = 0;
		for(int i=0; i<bin.length(); i++){
			intValue += ((int)bin.charAt(i)-48) * Math.pow(2, bin.length()-i-1);
		}
		
		return intValue;
	}

	public void writeLFSROutput(List<String> pseudoRandomSequencesList, String path){
		
		try{
			int counter = 1;
			File file = new File(path + "/output" + seed.length() + ".txt");
			if(!file.exists()){
	    		file.createNewFile();
	    	}
			
			FileWriter fw = new FileWriter(file.getName());
			BufferedWriter bw = new BufferedWriter(fw);
	 	    
			for(String num : pseudoRandomSequencesList){
				bw.write((counter++) + ":" + num + "\n");
			}
			bw.close();
			fw.close();
			
		}catch (IOException e) {
			
		}
		}

}