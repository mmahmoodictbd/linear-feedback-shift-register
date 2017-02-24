package org.redmind.ai.lfsr;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		
		
		String sid = args[0];
		
		LFSR lfsr = null;
		List<String> list = null;

		lfsr = new LFSR(sid);
		list = lfsr.generatePseudoRandomSequences();
		lfsr.writeLFSROutput(list, ".");

		
		System.out.println(list);
	
		
	}
	



}