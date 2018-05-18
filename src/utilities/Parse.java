package utilities;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import model.BuildableImpl;
import model.Rents;
import utilities.enumerations.Color;

/**
 * @author Matteo Alesiani 
 */

public class Parse {
	private static final String CHAR = "-";
	
	private static final Function<List<Integer>, Rents> PARSING_RENTS = t -> {
							return new Rents(t);
						};
						
	public static final Function<String, BuildableImpl> PARSING_INIT_TILE_BOARD = t -> {
						List<String> record = Arrays.stream(t.split(CHAR))
													.collect(Collectors.toList());
						
						Integer positionTile = new Integer(record.get(0));
						Integer price = new Integer(record.get(1));
						Integer mortgage = new Integer(record.get(2));
						Rents rents = PARSING_RENTS.apply(record.subList(3, 9).stream()
																.map(Integer::new).collect(Collectors.toList()));
						Integer priceBuilding = new Integer(record.get(9));
						
						return new BuildableImpl(positionTile.intValue(), price.intValue(), mortgage.intValue(), rents, 
											 Color.valueOf(Color.class, record.get(10)), priceBuilding.intValue());
					};
}
