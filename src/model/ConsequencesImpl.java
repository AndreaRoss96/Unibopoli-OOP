package model;

import java.util.Collections;
import java.util.List;

import utilities.enumerations.Consequences;

/**
 *
 * @author Matteo Alesiani
 */

public class ConsequencesImpl {
	private Consequences consequences;
	private String textConsequences;
	private List<String> values;
	
	public ConsequencesImpl(final Consequences consequences, final String textConsequences, final List<String> values) {
		this.consequences = consequences;
		this.textConsequences = textConsequences;
		this.values = values;
	}
	
	public String getTextConsequence() {
		return this.textConsequences;
	}
	
	public void addElement(final String element) {
		this.values.add(element);
	}
	
	public List<String> getValues(){
		return Collections.unmodifiableList(this.values);
	}
	
	public void doConsequences(){
		this.consequences.exec(this.getValues());
	}
	
	public static ConsequencesImpl emptyConsequence() {
		return new ConsequencesImpl(Consequences.NO_CONSEQUENCE, "", Collections.emptyList());
	}
}
