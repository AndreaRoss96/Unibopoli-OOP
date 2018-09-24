package model;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import utilities.CircularListImpl;
import utilities.Parse;
import utilities.ReadFile;
import utilities.enumerations.ClassicType;

public class CardEffectSupplier {

	private static CardEffectSupplier SINGLETONSUPPLIER;
	private CircularListImpl<ConcrateConsequences> unexpected;
	private CircularListImpl<ConcrateConsequences> probability;
	private static boolean done = false;
	
	private CardEffectSupplier(List<String> probability, List<String> unexpected) {
		this.unexpected = new CircularListImpl<>(unexpected.stream().map(Parse.PARSING_CONSEQUENCES::apply).collect(Collectors.toList()));
		this.probability = new CircularListImpl<>(probability.stream().map(Parse.PARSING_CONSEQUENCES::apply).collect(Collectors.toList()));
		
		Collections.shuffle(unexpected);
		Collections.shuffle(probability);
	}

	public static CardEffectSupplier get(){
		if(SINGLETONSUPPLIER == null) {
			try {
				SINGLETONSUPPLIER = new CardEffectSupplier(ReadFile.readFile(ClassicType.Files.GENERALFILEMAP.getProbabilityFile()).collect(Collectors.toList()), 
									 ReadFile.readFile(ClassicType.Files.GENERALFILEMAP.getUnexpectedFile()).collect(Collectors.toList()));
			} catch (IOException e) {
				done = true;
				e.printStackTrace();
			}
		}else if(done) {
			throw new IllegalStateException();
		}
		
		return SINGLETONSUPPLIER;
	}
	
	public ConcrateConsequences getNextProbability(){
		this.probability.shift();
		return this.probability.getHead();
	}
	
	public ConcrateConsequences getNextUnexpected() {
		this.unexpected.shift();
		return this.unexpected.getHead();
	}
}
