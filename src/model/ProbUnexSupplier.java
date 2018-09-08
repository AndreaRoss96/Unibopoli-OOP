package model;

import java.util.List;
import java.util.stream.Collectors;

import utilities.CircularListImpl;
import utilities.Parse;

public class ProbUnexSupplier {

	private static ProbUnexSupplier SINGLETONSUPPLIER;
	private static CircularListImpl<ConsequencesImpl> unexpected;
	private static CircularListImpl<ConsequencesImpl> probability;
	private static boolean done = false;
	
	private ProbUnexSupplier(List<String> probability, List<String> unexpected) {
		ProbUnexSupplier.unexpected = new CircularListImpl<>(unexpected.stream().map(Parse.PARSING_CONSEQUENCES::apply).collect(Collectors.toList()));
		ProbUnexSupplier.probability = new CircularListImpl<>(probability.stream().map(Parse.PARSING_CONSEQUENCES::apply).collect(Collectors.toList()));
	}
	
	private static void check(boolean condition) {
		if(condition) {
			throw new UnsupportedOperationException();
		}
	}
	
	public static ProbUnexSupplier get(){
		check(SINGLETONSUPPLIER == null || !done);
		return SINGLETONSUPPLIER;
	}
	
	public static  ProbUnexSupplier get(List<String> probabilityList, List<String> unexpectedList){
		check(done);
		if(SINGLETONSUPPLIER == null) {
			SINGLETONSUPPLIER = new ProbUnexSupplier(probabilityList, unexpectedList);
			done = true;
		}
		
		return SINGLETONSUPPLIER;
	}
	
	public ConsequencesImpl getNextProbability(){
		probability.shift();
		return probability.getHead();
	}
	
	public ConsequencesImpl getNextUnexpected() {
		unexpected.shift();
		return unexpected.getHead();
	}
}
